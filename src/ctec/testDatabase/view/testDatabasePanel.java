package ctec.testDatabase.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ctec.testDatabase.controller.testDatabaseAppController;
import ctec.testDatabase.controller.testDatabaseController;
import ctec.testDatabase.view.*;

public class testDatabasePanel extends JPanel
{
	private testDatabaseAppController baseController;
	private SpringLayout baseLayout;
	private JButton queryButton;
	private JScrollPane displayPane;
	private JTextArea displayArea;
	private TableCellWrapRenderer cellRenderer;
	private JTable resultsTable;
	private JPasswordField samplePassword;
	
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
		samplePassword = new JPasswordField(null, 20);
		cellRenderer = new TableCellWrapRenderer();
		
		setupTable();
		setupDisplayPane();
		setupPanel();
		setupLayout();
		setupListeners();
	}
	
	/**
	 * Sets up the pane where the results are shown.
	 */
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
		resultsTable = new JTable(new DefaultTableModel(baseController.getDataController().testResults(), baseController.getDataController().getDatabaseColumnNames("cities")));
		
		displayPane = new JScrollPane(resultsTable);
		for(int spot = 0; spot < resultsTable.getColumnCount(); spot++)
		{
			resultsTable.getColumnModel().getColumn(spot).setCellRenderer(cellRenderer);
		}
	}
	
	/**
	 * Sets up the pane, button, size, and color.
	 */
	private void setupPanel()
	{
		this.setBackground(Color.GREEN);
		this.setSize(1000,1000);
		this.setLayout(baseLayout);
		this.add(displayPane);
		this.add(queryButton);
		this.add(samplePassword);
		samplePassword.setEchoChar('@');
		samplePassword.setFont(new Font("Serif", Font.BOLD, 32));
		samplePassword.setForeground(Color.MAGENTA);
	}
	
	/**
	 * Creates the layout for the pane and button.
	 */
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
				JFrame popFrame = new JFrame();
				popFrame.setSize(250, 250);
				popFrame.setContentPane(new DynamicDataPanel(baseController, "books"));
				popFrame.setVisible(true);
			}
		});
	}
}
