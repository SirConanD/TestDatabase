package ctec.testDatabase.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import ctec.testDatabase.controller.testDatabaseAppController;

public class DynamicDataPanel extends JPanel
{
	private testDatabaseAppController baseController;
	private JButton queryButton;
	private SpringLayout baseLayout;
	private String table;
	private ArrayList<JTextField> fieldList;
	
	/**
	 * The setters for the methods.
	 * @param baseController Used to call the controller in other classes.
	 * @param table builds a table to put info into it.
	 */
	public DynamicDataPanel(testDatabaseAppController baseController, String table)
	{
		this.baseController = baseController;
		this.table = table;
		queryButton = new JButton("Submit query");
		baseLayout = new SpringLayout();
		fieldList = new ArrayList<JTextField>();
		
		setupPanel(table);
		setupLayout();
		setupListeners();
	}
	
	/**
	 * Sets up the panel for the dynamic panel.
	 * @param selectedTable
	 */
	private void setupPanel(String selectedTable)
	{
		this.setLayout(baseLayout);
		this.add(queryButton);
		int spacing = 50;
		
		String [] columns = baseController.getDataController().getDatabaseColumnNames(selectedTable);
		
		for(int spot = 0; spot < columns.length; spot++)
		{
			if(!columns[spot].equalsIgnoreCase("id"))
			{
			JLabel columnLabel = new JLabel(columns[spot]);
			JTextField columnField = new JTextField(20);
			
			this.add(columnLabel);
			this.add(columnField);
			
			columnField.setName(columns[spot]);
			fieldList.add(columnField);
			
			baseLayout.putConstraint(SpringLayout.WEST, columnLabel, 25, SpringLayout.WEST, this);
			baseLayout.putConstraint(SpringLayout.WEST, columnField, 30, SpringLayout.WEST, columnLabel);
			
			baseLayout.putConstraint(SpringLayout.NORTH, columnLabel, spacing, SpringLayout.NORTH, this);
			baseLayout.putConstraint(SpringLayout.NORTH, columnField, spacing, SpringLayout.NORTH, this);
			
			spacing += 50;
			}
		}
	}
	
	/**
	 * Sets up the layout for the panel.
	 */
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.WEST, queryButton, 161, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, queryButton, -10, SpringLayout.SOUTH, this);
	}
	
	/**
	 * Gets the fields of the tables.
	 * @return The info with in tables.
	 */
	private String getFields()
	{
		String fields = "(";
		
		for(int spot = 0; spot < fieldList.size(); spot++)
		{
			fields += "`" + fieldList.get(spot).getName() + "`";
			if(spot == fieldList.size()-1)
			{
				fields += ")";
			}
			else
			{
				fields += ", ";
			}
		}
		
		return fields;
	}
	
	/**
	 * Gets the values from the fieldList.
	 * @return
	 */
	private String getValues()
	{
		String values = "(";
		
		for(int spot = 0; spot < fieldList.size(); spot++)
		{
			values += "'" + fieldList.get(spot).getText() + "'";
			if(spot == fieldList.size()-1)
			{
				values += ");";
			}
			else
			{
				values += ", ";
			}
		}
		
		return values;
	}
	
	/**
	 * Sets up the listener for the button. 
	 */
	private void setupListeners()
	{
		queryButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String query = "INSERT INTO " + "`" + table + "` " + getFields() + " VALUES " + getValues();
				baseController.getDataController().submitUpdateQuery(query);
			}
		});
	}
	
}
