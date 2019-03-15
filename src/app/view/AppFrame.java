package app.view;

import javax.swing.JFrame;

import app.controller.AppController;
import app.model.TicketSystem;

/**
 * AppFrame object that extends JFrame for use in an SQL GUI
 * @author locust
 * 
 */
public class AppFrame extends JFrame {
	/**
	 * Reference to the application AppPanel
	 */
	private AppPanel basePanel;
	
	/**
	 * Create frame object passing a reference to the AppController for use in the AppFrame object
	 * @param baseController
	 */
	public AppFrame(AppController baseController) {
		basePanel = new AppPanel(baseController);
		setupFrame();
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}
	
	/**
	 * Set the content pane, size and makes the frame visible
	 */
	private void setupFrame() {
		this.setContentPane(basePanel);
		this.setSize(500, 250);
		this.setVisible(true);
	}
}
