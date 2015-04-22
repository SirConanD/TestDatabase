package ctec.testDatabase.controller;

import java.util.ArrayList;
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
		
	}
	
	
}
