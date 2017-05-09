/**
 * Process Explorer
 * Drew Vogt
 * MIS 301 VC
 * 
 * Final Project
 * 
 * This class is an object that stores the details about the data and
 * information for processing the charts.  It is created and shared as needed.
 */

package processExplorer;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DataInfo {

	// Variables for processing the data
	double target;
	int samples;
	int sampleSize;
	double service;
	
	// Default constructor.  Ensures 0 values for default
	DataInfo () {
		this.target = 0;
		this.samples = 0;
		this.sampleSize = 0;
		this.service = 0;
	}
	
	// Launches GUI input for data details
	public void getDataInfoGUI(){

		JTextField target = new JTextField();
		JTextField samples = new JTextField();
		JTextField sampleSize = new JTextField();
		JTextField service = new JTextField();
	 
		Object[] message = {
		    "Target Value:", target,
		    "Samples (2 - 25):", samples,
		    "Sample Size (1 - 25):", sampleSize,
		    "Service Level (0.0 - 1.0):", service,
		};

		int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
		    System.out.println(target.getText() + " " + samples.getText() + " " + sampleSize.getText() + " " + service.getText());
		    this.target = Double.parseDouble(target.getText());
		    this.samples = Integer.parseInt(samples.getText());
		    this.sampleSize = Integer.parseInt(samples.getText());
		    this.service = Double.parseDouble(service.getText());
		} else {
			//Does nothing currently
		}

	}
}
