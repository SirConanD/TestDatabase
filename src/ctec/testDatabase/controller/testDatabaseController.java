
package ctec.testDatabase.controller;

import java.sql.*;

import javax.swing.JOptionPane;

import ctec.testDatabase.model.QueryInfo;
import ctec.testDatabase.view.testDatabaseFrame;

public class testDatabaseController
{
	private String connectionString;
	private Connection databaseConnection;
	private testDatabaseAppController baseController;
	private String query;
	private String currentQuery;
	private long queryTime;
	
	/**
	 * calls the controller and the connection string.  It also sets up the driver checker and the connection.
	 * @param baseController
	 */
	public testDatabaseController(testDatabaseAppController baseController)
	{
		this.baseController = baseController;
		this.connectionString = "jdbc:mysql://localhost/gasoline_travel?user=root";
		queryTime = 0;
		checkDriver();
		setupConnection();
	}
	
	/**
	 * Sets the query to be called for.
	 * @return The query
	 */
	public String getQuery()
	{
		return query;
	}
	
	/**
	 * Calls the method query.
	 * @param query The method above.
	 */
	public void setQuery(String query)
	{
		this.query = query;
	}
	
	/**
	 * Checks the current query for any violations of the data in the database.
	 * @return if it is true or false.
	 */
	private boolean checkForDataViolation()
	{
		if(currentQuery.toUpperCase().contains(" DROP ") || currentQuery.toUpperCase().contains(" TRUNCATE ") || currentQuery.toUpperCase().contains(" SET ") || currentQuery.toUpperCase().contains(" ALTER "))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	/**
	 * checks if there is a violation of the database structure.
	 * @return if it is true or false.
	 */
	private boolean checkForStructureViolation()
	{
		if(currentQuery.toUpperCase().contains("DATABASE"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * builds a connection so it can connect to any database.
	 * @param pathToDBServer The path to the database.
	 * @param databaseName The databases name.
	 * @param userName the name of the user.
	 * @param password the users password.
	 */
	public void connectionStringBuilder(String pathToDBServer, String databaseName, String userName, String password)
	{
		connectionString = "jdbc:mysql://";
		connectionString += pathToDBServer;
		connectionString += "/" + databaseName;
		connectionString += "?user=" + userName;
		connectionString += "&password=" + password;
	}
	
	/**
	 * checks the drivers for the database.
	 */
	private void checkDriver()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception currentException)
		{
			displayErrors(currentException);
			System.exit(1);
		}
	}

	/**
	 * closes the connection to the external database data.
	 */
	public void closeConnection()
	{
		try
		{
			databaseConnection.close();
		}
		catch(SQLException currentException)
		{
			displayErrors(currentException);
		}
	}
	
	/**
	 * sets up the connection to the database and starts to get the information.
	 */
	public void setupConnection()
	{
		try
		{
			databaseConnection = DriverManager.getConnection(connectionString);
		}
		catch(SQLException currentException)
		{
			displayErrors(currentException);
		}
	}
	
	/**
	 * Gets info from the table.
	 * @return
	 */
	public String [] getMetaDataTitles()
	{
		String [] columns = null;
		query = "SELECT * FROM `INNODB_SYS_COLUMNS`";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		endTime = 0;
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			//takes the results and turns them into a result.
			ResultSetMetaData answerData = answers.getMetaData();
			
			//gets the answer data and converts it to the number of columns.
			columns = new String[answerData.getColumnCount()];
			
			for(int column = 0; column < answerData.getColumnCount(); column++)
			{
				columns[column] = answerData.getColumnName(column+1);
			}
			
			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		}
		catch(SQLException currentException)
		{
			endTime = System.currentTimeMillis();
			columns = new String [] {"empty"};
			displayErrors(currentException);
		}
		baseController.getQueryList().add(new QueryInfo(currentQuery, endTime-startTime));
		return columns;
	}
	
	
	/**
	 * Used to get column names.
	 * @return The name of the column.
	 */
	public String [] getDatabaseColumnNames(String tableName)
	{
		String [] columns = null;
		query = "SELECT * FROM `"+ tableName +"`";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		endTime = 0;
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			//takes the results and turns them into a result.
			ResultSetMetaData answerData = answers.getMetaData();
			
			
			//gets the answer data and converts it to the number of columns.
			columns = new String[answerData.getColumnCount()];
			
			for(int column = 0; column < answerData.getColumnCount(); column++)
			{
				columns[column] = answerData.getColumnName(column+1);
			}
			
			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		}
		catch(SQLException currentException)
		{
			endTime = System.currentTimeMillis();
			columns = new String [] {"empty"};
			displayErrors(currentException);
		}
		baseController.getQueryList().add(new QueryInfo(currentQuery, endTime-startTime));
		return columns;
	}
	
	
	/**
	 * Selects the results of the query.
	 * @param query
	 * @return
	 */
	public String [][] selectQueryResults(String query)
	{
		String [][] results;
		this.query = query;
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		endTime = 0;
		try
		{
			if(checkForDataViolation())
			{
				throw new SQLException("There was an attempt ata a data violation", 
						               "you dont get to mess with data in here ", 
						               Integer.MIN_VALUE);
			}
			
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			int columnCount = answers.getMetaData().getColumnCount();
			
			answers.last();
			//Gets the number of rows that will get the answer.
			int numberOfRows = answers.getRow();
			answers.beforeFirst();
			
			//Will show the results of the number of rows.
			results = new String [numberOfRows][columnCount];
			
			while(answers.next())
			{
				for(int col = 0; col < columnCount; col++)
				{
					//The method that compiles the answer to get the results.
					results[answers.getRow()-1][col] = answers.getString(col + 1);
				}
				
			}
			
			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		}
		catch(SQLException currentException)
		{
			endTime = System.currentTimeMillis();
			//Makes sure that if the results are empty it shows the error.
			results = new String [][] {{"The query did not work."},
										{"You may want to try another query."},
										{currentException.getMessage()}
									  };
			displayErrors(currentException);
		}
		baseController.getQueryList().add(new QueryInfo(currentQuery, endTime-startTime));
		return results;		
	}
	
	/**
	 * Returns the results of the rows and columns.
	 * @return
	 */
	public String [][] realResults()
	{
		String [][] results;
		query = "SELECT * FROM `INNIDB_SYS_COLUMNS`";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		endTime = 0;
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			int columnCount = answers.getMetaData().getColumnCount();
			
			answers.last();
			//Gets the number of rows that will get the answer.
			int numberOfRows = answers.getRow();
			answers.beforeFirst();
			
			//Will show the results of the number of rows.
			results = new String [numberOfRows][columnCount];
			
			while(answers.next())
			{
				for(int col = 0; col < columnCount; col++)
				{
					//The method that compiles the answer to get the results.
					results[answers.getRow()-1][col] = answers.getString(col + 1);
				}
				
			}
			
			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		}
		catch(SQLException currentException)
		{
			endTime = System.currentTimeMillis();
			//Makes sure that if the results are empty it shows the error.
			results = new String [][] {{"empty"}};
			displayErrors(currentException);
		}
		baseController.getQueryList().add(new QueryInfo(currentQuery, endTime-startTime));
		return results;		
	}
	
	/**
	 * Shows the results of the test.
	 * @return The results of the test.
	 */
	public String [][] testResults()
	{
		String [][] results;
		query = "SHOW TABLES";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		endTime = 0;
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			
			answers.last();
			//Gets the number of rows that will get the answer.
			int numberOfRows = answers.getRow();
			answers.beforeFirst();
			
			//Will show the results of the number of rows.
			results = new String [numberOfRows][1];
			
			while(answers.next())
			{
				//The method that compiles the answer to get the results.
				results[answers.getRow()-1][0] = answers.getString(1);
			}
			
			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		}
		catch(SQLException currentException)
		{
			endTime = System.currentTimeMillis();
			//Makes sure that if the results are empty it shows the error.
			results = new String [][] {{"empty"}};
			displayErrors(currentException);
		}
		baseController.getQueryList().add(new QueryInfo(currentQuery, endTime-startTime));
		return results;
	}
	
	/**
	 * A method that shows the tables from a database.
	 * @return The names of the tables in a database.
	 */
	public String displayTables()
	{
		String tableNames = "";
		query = "SHOW TABLES";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		endTime = 0;
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			
			while(answers.next())
			{
				//the name of the tables is equal to or greater than the string.
				tableNames += answers.getString(1) + "\n";
			}
			answers.close();
			firstStatement.close();
			endTime = System.currentTimeMillis();
		}
		catch(SQLException currentError)
		{
			endTime = System.currentTimeMillis();
			displayErrors(currentError);
		}
		baseController.getQueryList().add(new QueryInfo(currentQuery, endTime-startTime));
		return tableNames;
	}
	
	/**
	 * submits the users input into the database.
	 * @param query
	 */
	public void submitUpdateQuery(String query)
	{
		this.query = query;
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		try
		{
			Statement updateStatment = databaseConnection.createStatement();
			updateStatment.executeUpdate(query);
			endTime = System.currentTimeMillis();
		}
		catch(SQLException currentError)
		{
			endTime = System.currentTimeMillis();
			displayErrors(currentError);
		}
		baseController.getQueryList().add(new QueryInfo(query, endTime - startTime));
	}
	
	/**
	 * A method that inserts data in to the database.
	 * @return Shows where the new data affected the table.
	 */
	public int insertSample()
	{
		int rowsAffected = -1;
		query = "INSERET INTO `heros_and_villains`.`anti_heroes` (`id`, `name`, `power`, `affiliation`) VALUES (`1`, `Deadpool`, `healing factor`, `highest bidder`);";
		
		try
		{
			Statement insertStatement = databaseConnection.createStatement();
			rowsAffected = insertStatement.executeUpdate(query);
			insertStatement.close();
		}
		catch(SQLException currentError)
		{
			displayErrors(currentError);
		}
		
		return rowsAffected;
	}
	
	/**
	 * creates a statement that lets a person drop data from a database.
	 * @param query
	 */
	public void dropStatement(String query)
	{
		currentQuery = query;
		String results;
		try
		{
			if(checkForStructureViolation())
			{
				throw new SQLException("you are not allowed to drop the database","Try another query", Integer.MIN_VALUE);
			}
			
			if(currentQuery.toUpperCase().contains(" INDEX "))
			{
				results = "The index was ";
			}
			else
			{
				results = "The table was ";
			}
			
			Statement dropStatement = databaseConnection.createStatement();
			int affected = dropStatement.executeUpdate(currentQuery);
			
			dropStatement.close();
			
			if(affected == 0)
			{
				results += "dropped";
			}
			JOptionPane.showMessageDialog(baseController.getAppFrame(), results);
		}
		catch(SQLException dropError)
		{
			displayErrors(dropError);
		}
	}
	
	/**
	 * The method that checks for SQL errors and then displays them.
	 * @param currentException Checks the exception that is currently happening.
	 */
	public void displayErrors(Exception currentException)
	{
		JOptionPane.showMessageDialog(baseController.getAppFrame(), "Exception: " + currentException.getMessage());
		if(currentException instanceof SQLException)
		{
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL State: " + ((SQLException) currentException).getSQLState());
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL Error Code: " + ((SQLException) currentException).getErrorCode());
		}
	}
}
