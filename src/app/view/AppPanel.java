package app.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import app.controller.AppController;
import app.model.EmptyFieldException;
import app.model.TicketSystem;

public class AppPanel extends JPanel {
	private AppController baseController;
	
	private TicketSystem TS;
	private JLabel ticketLabel;
	private JTextField ticketField;
	private JButton createTicketButton;
	private JButton retrieveTicketButton;
	private JButton clearAllButton;
	private SpringLayout baseLayout;
	
	public AppPanel(AppController baseController) {
		this.baseController = baseController;
		TS = new TicketSystem();
		
		ticketLabel = new JLabel("Enter brief ticket description");
		ticketField = new JTextField(30);
		createTicketButton = new JButton("Create Ticket");
		retrieveTicketButton = new JButton("Receive next Ticket");
		clearAllButton = new JButton("Clear All Tickets");
		
		baseLayout = new SpringLayout();
		
		setupPanel();
		setupLayout();
		setupListener();
	}
	
	private void setupLayout() {
		baseLayout.putConstraint(SpringLayout.NORTH, ticketLabel, 81, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, ticketField, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, ticketField, -6, SpringLayout.NORTH, ticketLabel);
		baseLayout.putConstraint(SpringLayout.NORTH, createTicketButton, 39, SpringLayout.SOUTH, ticketLabel);
		baseLayout.putConstraint(SpringLayout.WEST, ticketLabel, 0, SpringLayout.WEST, createTicketButton);
		baseLayout.putConstraint(SpringLayout.NORTH, retrieveTicketButton, 0, SpringLayout.NORTH, createTicketButton);
		baseLayout.putConstraint(SpringLayout.EAST, retrieveTicketButton, -10, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.WEST, createTicketButton, 10, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, clearAllButton, 19, SpringLayout.SOUTH, createTicketButton);
		baseLayout.putConstraint(SpringLayout.WEST, clearAllButton, 0, SpringLayout.WEST, createTicketButton);
	}
	
	private void setupPanel() {
		this.setLayout(baseLayout);
		this.add(createTicketButton);
		this.add(retrieveTicketButton);
		this.add(clearAllButton);
		this.add(ticketField);
		this.add(ticketLabel);
	}
	
	private void setupListener() {
		createTicketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent click) {
				try {
					if(ticketField.getText().equals("")) {
						throw new EmptyFieldException();
					}
					TS.sendTicket(ticketField.getText());
					JOptionPane.showMessageDialog(null, "Ticket Successfully Created");
				}
				catch(EmptyFieldException efe) {
					JOptionPane.showMessageDialog(null, "Enter a description before sending a ticket.");
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, "An Error Occurred");
				}
			}
		});
		
		retrieveTicketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent click) {
				//JOptionPane.showMessageDialog(null, TS.recTicket());
				if(TS.recTicket() != "There are currently no tickets") {
					String confirmation = TS.recTicket() + "\nWould you like to delete this ticket?";
					int dialogResult = JOptionPane.showConfirmDialog(retrieveTicketButton, confirmation);
					if(dialogResult == JOptionPane.YES_OPTION) {
						TS.deleteTicket();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, TS.recTicket());
				}
			}
		});
		
		clearAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent click) {
				int dialogResult = JOptionPane.showConfirmDialog(clearAllButton, "Are you sure you want to delete all Tickets?");
				if(dialogResult == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, TS.clearAllTickets());
				}
			}
		});
		
		ticketField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent enter) {
				try {
					if(ticketField.getText().equals("")) {
						throw new EmptyFieldException();
					}
					TS.sendTicket(ticketField.getText());
					JOptionPane.showMessageDialog(null, "Ticket Successfully Created");
				}
				catch(EmptyFieldException efe) {
					JOptionPane.showMessageDialog(null, "Enter a description before sending a ticket.");
				}
				catch(Exception e) {
					JOptionPane.showMessageDialog(null, "An Error Occurred");
				}
			}
		});
		
	}
}
