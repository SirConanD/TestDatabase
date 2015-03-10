package ctec.testDatabase.view;

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ctec.testDatabase.controller.testDatabaseAppController;
import ctec.testDatabase.controller.testDatabaseController;

public class testDatabasePanel extends JPanel
{
	private testDatabaseAppController baseController;
	private SpringLayout baseLayout;
	private JButton queryButton;
	private JScrollPane displayPane;
	private JTextArea displayArea;
	private JTable resultsTable;
	
	/**
	 * sets up the all of the JSwing items and the layout.
	 * @param baseController
	 */
	public testDatabasePanel(testDatabaseAppController baseController)
	{
		this.baseController = baseController;
		baseLayout = new SpringLayout();
		queryButton = new JButton("Click here to test the query");
		displayArea = new JTextArea(10,30);
		displayPane = new JScrollPane(displayArea);
		
		//setupDisplayPane();
		setupPanel();
		setupLayout();
		setupListeners();
	}
	
	private void setupDisplayPane()
	{
		displayArea.setWrapStyleWord(true);
		displayArea.setLineWrap(true);
		displayArea.setEditable(false);
		displayArea.setBackground(Color.PINK);
	}
	
	/**
	 * Shows the test results from the test results and the metaDataTables.
	 */
	private void setupTable()
	{
		DefaultTableModel basicData = new DefaultTableModel(baseController.getDataController().testResults(), baseController.getDataController().getMetaDataTitles());
		resultsTable = new JTable(basicData);
		displayPane = new JScrollPane(resultsTable);
	}
	
	private void setupPanel()
	{
		this.setBackground(Color.GREEN);
		this.setSize(1000,1000);
		this.setLayout(baseLayout);
		this.add(displayPane);
		this.add(queryButton);
	}
	
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.NORTH, queryButton, 50, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, queryButton, 392, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, displayPane, 150, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, displayPane, 50, SpringLayout.WEST, this);
	}
	
	/**
	 * Creates the listener for the button so the code will compile.
	 */
	private void setupListeners()
	{
		queryButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String [] temp =  baseController.getDataController().getMetaDataTitles();
				for(String current : temp)
				{
					displayArea.setText(displayArea.getText() +"Column : " + current +"\n");
				}
			}
		});
	}
}
