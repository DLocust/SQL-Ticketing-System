package app.model;

public class EmptyFieldException extends Exception{
	public EmptyFieldException() {
		
	}
	
	public EmptyFieldException(String message) {
		super(message);
	}
}