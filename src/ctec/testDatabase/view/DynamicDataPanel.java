package ctec.testDatabase.view;

import javax.swing.JButton;
import javax.swing.SpringLayout;

import ctec.testDatabase.controller.testDatabaseAppController;

public class DynamicDataPanel
{
	private testDatabaseAppController baseController;
	private JButton queryButton;
	private SpringLayout baseLayout;
	
	public DynamicDataPanel(testDatabaseAppController baseController, String table)
	{
		this.baseController = baseController;
		queryButton = new JButton("Submit query");
		baseLayout = new SpringLayout();
		setupPanel(table);
	}
	
	private void setupPanel(String selectedTable)
	{
		this.setLayout(baseLayout);
		this.add(queryButton);
		
		
	}
	
	private void setupLayout()
	{
		
	}
	
}
