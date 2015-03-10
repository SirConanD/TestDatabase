package ctec.testDatabase.view;

import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import ctec.testDatabase.controller.testDatabaseAppController;
import ctec.testDatabase.controller.testDatabaseController;

public class testDatabaseFrame extends JFrame
{
	private testDatabasePanel basePanel;
	
	public testDatabaseFrame(testDatabaseAppController baseController)
	{
		basePanel = new testDatabasePanel(baseController);
		setupFrame();
	}
	
	private void setupFrame()
	{
		this.setContentPane(basePanel);
		this.setVisible(true);
	}
}
