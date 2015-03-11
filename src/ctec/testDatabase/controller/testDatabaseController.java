package ctec.testDatabase.controller;

import java.sql.*;
import javax.swing.JOptionPane;
import ctec.testDatabase.view.testDatabaseFrame;

public class testDatabaseController
{
	private String connectionString;
	private Connection databaseConnection;
	private testDatabaseAppController baseController;
	
	/**
	 * calls the controller and the connection string.  It also sets up the driver checker and the connection.
	 * @param baseController
	 */
	public testDatabaseController(testDatabaseAppController baseController)
	{
		this.baseController = baseController;
		this.connectionString = "jdbc:mysql://localhost/heros_and_villains?user=root";
		checkDriver();
		setupConnection();
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
	private void setupConnection()
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
	 * Used to get column names.
	 * @return The name of the column.
	 */
	public String [] getMetaDataTitles()
	{
		String [] columns = null;
		String query = "SHOW TABLES";
		
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
		}
		catch(SQLException currentException)
		{
			columns = new String [] {"empty"};
			displayErrors(currentException);
		}
		
		return columns;
	}
	
	/**
	 * Shows the results of the test.
	 * @return The results of the test.
	 */
	public String [][] testResults()
	{
		String [][] results;
		String query = "SHOW TABLES";
		
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
		}
		catch(SQLException currentException)
		{
			//Makes sure that if the results are empty it shows the error.
			results = new String [][] {{"empty"}};
			displayErrors(currentException);
		}
		
		return results;
	}
	
	/**
	 * A method that shows the tables from a database.
	 * @return The names of the tables in a database.
	 */
	public String displayTables()
	{
		String tableNames = "";
		String query = "SHOW TABLES";
		
		try
		{
			Statement firstStatement = databaseConnection.createStatement();
			ResultSet answers = firstStatement.executeQuery(query);
			
			while(answers.next())
			{
				tableNames += answers.getString(1) + "\n";
			}
			answers.close();
			firstStatement.close();
		}
		catch(SQLException currentError)
		{
			displayErrors(currentError);
		}
		
		return tableNames;
	}
	
	public int insertSample()
	{
		int rowsAffected = -1;
		String query = "INSERET INTO `heros_and_villains`.`anti_heroes` (`id`, `name`, `power`, `affiliation`) VALUES (`1`, `Deadpool`, `healing factor`, `highest bidder`);";
		
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
