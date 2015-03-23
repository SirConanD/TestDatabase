package ctec.testDatabase.controller;

import ctec.testDatabase.view.testDatabaseFrame;

public class testDatabaseAppController
{
	private testDatabaseFrame appFrame;
	private testDatabaseController dataController;
	
	/**
	 * Has the controller and the app frame.
	 */
	public testDatabaseAppController()
	{
		dataController = new testDatabaseController(this);
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
	 * starts the program.
	 */
	public void start()
	{
		
	}
}
