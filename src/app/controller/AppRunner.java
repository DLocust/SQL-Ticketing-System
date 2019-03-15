package app.controller;
/**
 * Runner object for the Java SQL Application
 * @author locust
 * @version Decemeber 18, 2018
 */
public class AppRunner {
	/**
	 * Entry point for java application
	 * @param args No args for GUI application
	 */
	public static void main(String[] args) {
		AppController baseApp = new AppController();
		baseApp.start();
	}

}
