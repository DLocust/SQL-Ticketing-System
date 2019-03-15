package app.model;

import java.io.FileOutputStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketSystem {
	/**
	 * Create Database and Table if they don't exist.
	 */
	public TicketSystem() {
		try {
			/*Things to DO
			 * CREATE A LOG FILE THAT LOGS ALL TICKETS
			 */
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/?useLegacyDatetimeCode=false&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(url, "root", "");
			Statement stmt = conn.createStatement();
			stmt.execute("CREATE DATABASE IF NOT EXISTS TicketSys");
			stmt.execute("USE TicketSys;");
			stmt.execute("CREATE TABLE IF NOT EXISTS Tickets (ticket_number INT NOT NULL PRIMARY KEY AUTO_INCREMENT, time_posted VARCHAR(100), description VARCHAR(1000))");
		}
		catch(Exception e) {
			System.err.print("Something went wrong: ");
			System.err.println(e.getMessage());
		}
	}
	/**
	 * For setting system time to string variable
	 * @return System Time
	 */
	public String getSysTime() {
		final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return sdf.format(date);
	}
	
	/**
	 * Public method to send to send tickets to the db
	 * @param ticketName ->takes string from gui input
	 */
	public void sendTicket(String ticketName) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/?useLegacyDatetimeCode=false&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(url, "root", "");
			Statement stmt = conn.createStatement();
			stmt.execute("USE TicketSys;");
			String timeposted = getSysTime();
			String ticketDesc = ticketName;
			stmt.execute("INSERT INTO Tickets (time_posted, description) VALUE ('"+timeposted+"', '"+ticketDesc+"');");
			systemLogger(timeposted, ticketDesc);
		}
		catch(Exception e) {
			System.err.print("Something went wrong: ");
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Deletes the next ticket if there is a ticket.
	 */
	public void deleteTicket() {
		String tn = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/?useLegacyDatetimeCode=false&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(url, "root", "");
			Statement stmt = conn.createStatement();
			stmt.execute("USE TicketSys;");
			ResultSet rs = stmt.executeQuery("SELECT ticket_number FROM Tickets ORDER BY ticket_number ASC LIMIT 1");
			int ticket_int = 0;
			while(rs.next()) 
				ticket_int = Integer.parseInt(rs.getString(1));
			stmt.execute("DELETE FROM Tickets WHERE ticket_number = '"+ticket_int+"';");
			if(tn == "") {
				tn = "There are currently no tickets";
			}
		}
		catch(Exception e) {
			System.err.print("Something went wrong: ");
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Public method to retrieve tickets from the db
	 * @return ticket to label and removes ticket from db
	 */
	public String recTicket() {
		String tn = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/?useLegacyDatetimeCode=false&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(url, "root", "");
			Statement stmt = conn.createStatement();
			stmt.execute("USE TicketSys;");
			/*
			 * For putting the time posted into the messageDialog
			 */
			ResultSet rs = stmt.executeQuery("SELECT time_posted FROM Tickets ORDER BY ticket_number ASC LIMIT 1");
			while(rs.next())
				tn += rs.getString(1) + '\n';
			/*
			 * For putting the ticket description in the messageDialog
			 */
			rs = stmt.executeQuery("SELECT description FROM Tickets ORDER BY ticket_number ASC LIMIT 1");
			while(rs.next())
				tn += "Ticket Description: " + rs.getString(1);
			if(tn == "") {
				tn = "There are currently no tickets";
			}
			
		}
		catch(Exception e) {
			System.err.print("Something went wrong: ");
			System.err.println(e.getMessage());
			tn = e.getMessage();
		}
		return tn;
	}
	/**
	 * Clears All Tickets in the Table if there is at least one Ticket
	 * @return string confirming if all tickets where erased or if there were no tickets
	 */
	public String clearAllTickets() {
		String tconf = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/?useLegacyDatetimeCode=false&serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(url, "root", "");
			Statement stmt = conn.createStatement();
			stmt.execute("USE TicketSys;");
			ResultSet rs = stmt.executeQuery("SELECT ticket_number FROM Tickets;");
			while(rs.next())
				tconf += rs.getString(1);
			if(tconf.equals("")) {
				tconf = "There are currently no tickets.";
			}
			else {
				stmt.execute("TRUNCATE TABLE Tickets;");
				tconf = "All Tickets Cleared";
			}
		}
		catch(Exception e) {
			System.err.print("Something went wrong: ");
			System.err.println(e.getMessage());
		}
		return tconf;
	}
	/**
	 * Logs the time posted and ticket description to a syslog text file
	 * @param time
	 * @param description
	 */
	public void systemLogger(String time, String description) {
		String log = time + " Ticket Description: " + description + "\n";
		try {
			FileOutputStream fout = new FileOutputStream("syslog.txt", true);
			fout.write(log.getBytes());
			fout.close();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
}
