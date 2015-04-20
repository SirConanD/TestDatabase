package ctec.testDatabase.view;

import javax.swing.*;
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
		this.setupLayout(baseLayout);
		this.add(queryButton);
		int spacing = 50;
		
		String [] columns = baseController.getDataController().getDatabaseColumnNames(selectedTable);
		
		for(int spot = 0; spot < columns.length; spot++)
		{
			JLabel columnLabel = new JLabel(columns[spot]);
			JTextField columnField = new JTextField(20);
			
			this.add(columnLabel);
			this.add(columnField);
			
			baseLayout.putConstraint(SpringLayout.WEST, columnLabel, 25, SpringLayout.WEST, this);
			baseLayout.putConstraint(SpringLayout.WEST, columnField, 30, SpringLayout.WEST, columnLabel);
			
			baseLayout.putConstraint();
			
			spacing += 50;
		}
	}
	
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.WEST, queryButton, 161, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, queryButton, -10, SpringLayout.SOUTH, this);
	}
	
}
