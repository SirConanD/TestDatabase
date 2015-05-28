package ctec.testDatabase.view;

import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import ctec.testDatabase.controller.testDatabaseAppController;
import ctec.testDatabase.controller.testDatabaseController;

public class testDatabaseFrame extends JFrame
{
	private testDatabasePanel basePanel;
	private testDatabaseAppController baseController;
	
	/**
	 * Creates the frame for the GUI of the database.
	 * @param baseController The method that calls the controller.
	 */
	public testDatabaseFrame(testDatabaseAppController baseController)
	{
		
		this.baseController = baseController;
		basePanel = new testDatabasePanel(baseController);
		setupFrame();
		setupListeners();
	}
	
	/**
	 * sets up the contents of the frame.
	 */
	private void setupFrame()
	{
		this.setSize(500,500);
		this.setContentPane(basePanel);
		this.setVisible(true);
	}
	
	/**
	 * Sets up a listener method for the frame.
	 */
	private void setupListeners()
	{
		addWindowListener(new WindowListener()
		{
			public void windowOpened(WindowEvent e)
			{
			}
			
			public void windowClosing(WindowEvent e)
			{
				baseController.saveTimingInformation();
			}

			@Override
			public void windowActivated(WindowEvent arg0)
			{
			}

			@Override
			public void windowClosed(WindowEvent e)
			{
			}

			@Override
			public void windowDeactivated(WindowEvent e)
			{
			}

			@Override
			public void windowDeiconified(WindowEvent e)
			{
			}

			@Override
			public void windowIconified(WindowEvent e)
			{
			}
		});
	}
}
