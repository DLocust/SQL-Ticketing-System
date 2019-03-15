package app.controller;

import app.view.AppFrame;

public class AppController {
	private AppFrame frame;
	
	public void start() {
		frame = new AppFrame(this);
	}
}
