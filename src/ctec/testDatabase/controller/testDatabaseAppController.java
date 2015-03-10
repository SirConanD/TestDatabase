package ctec.testDatabase.controller;

import ctec.testDatabase.view.testDatabaseFrame;

public class testDatabaseAppController
{
	private testDatabaseFrame appFrame;
	private testDatabaseController dataController;
	
	public testDatabaseAppController()
	{
		dataController = new testDatabaseController(this);
		appFrame = new testDatabaseFrame(this);
	}
	
	public testDatabaseFrame getAppFrame()
	{
		return appFrame;
	}
	
	public testDatabaseController getDataController()
	{
		return dataController;
	}

	public void start()
	{
		
	}
}
