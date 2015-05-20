package ctec.testDatabase.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import ctec.testDatabase.model.QueryInfo;
import ctec.testDatabase.view.testDatabaseFrame;

public class testDatabaseAppController
{
	private testDatabaseFrame appFrame;
	private testDatabaseController dataController;
	private ArrayList<QueryInfo> queryList;
	
	/**
	 * Has the controller and the app frame.
	 */
	public testDatabaseAppController()
	{
		dataController = new testDatabaseController(this);
		queryList = new ArrayList<QueryInfo>();
		appFrame = new testDatabaseFrame(this);
	}
	
	/**
	 * gets the app frame.
	 * @return The app frame. 
	 */
	public testDatabaseFrame getAppFrame()
	{
		return appFrame;
	}
	
	/**
	 * Calls the data controller.
	 * @return The data controller.
	 */
	public testDatabaseController getDataController()
	{
		return dataController;
	}
	
	/**
	 * Gets the info from the user's query.
	 * @return the information from the query.
	 */
	public ArrayList<QueryInfo> getQueryList()
	{
		return queryList;
	}

	/**
	 * starts the program.
	 */
	public void start()
	{
		loadTimingInformation();
	}
	
	/**
	 * Gets the time for the loading time.
	 */
	private void loadTimingInformation()
	{
		try
		{
			File loadFile = new File("asdasda.save");
			if(loadFile.exists())
			{
				queryList.clear();
				Scanner textScanner = new Scanner(loadFile);
				while(textScanner.hasNext())
				{
					String query = textScanner.nextLine();
					long queryTime = Long.parseLong(textScanner.nextLine());
					queryList.add(new QueryInfo(query, queryTime));
				}
				textScanner.close();
				JOptionPane.showMessageDialog(getAppFrame(), queryList.size() + " QueryInfo objects were loaded into the application");
			}
			else
			{
				JOptionPane.showMessageDialog(getAppFrame(), "File not present.  No QueryInfo objects loaded");
			}
		}
		catch(IOException currentError)
		{
			dataController.displayErrors(currentError);
		}
	}
	
	/**
	 * Saves the information from the load timing info method. 
	 */
	public void saveTimingInformation()
	{
		try
		{
			File saveFile = new File("asdasda.save");
			PrintWriter writer = new PrintWriter(saveFile);
			if(saveFile.exists())
			{
				for(QueryInfo current : queryList)
				{
					writer.println(current.getQuery());
					writer.println(current.getQueryTime());
				}
				writer.close();
				JOptionPane.showMessageDialog(getAppFrame(), queryList.size() + " QueryInfo objects were saved");
			}
			else
			{
				JOptionPane.showMessageDialog(getAppFrame(), "File not present. No QueryInfo objects saved");
			}
		}
		catch(IOException currentError)
		{
			dataController.displayErrors(currentError);
		}
	}
}
