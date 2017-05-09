/**
 * Process Explorer
 * Drew Vogt
 * MIS 301VC
 * 
 * Final Project
 * 
 * This class is a worker object to store the credentials of the database
 * being queried.
 */

package processExplorer;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DBInfo {

	String server;
	String database;
	String password;
	String table;
	String field;
	
	DBInfo() {
		this.server = "";
		this.database = "";
		this.password = "";
		this.table = "";
		this.field = "";
	}
	
	public void getDBInfoGUI(){
		
		JTextField server = new JTextField();
		JTextField database = new JTextField();
		JTextField password = new JTextField();
		JTextField table = new JTextField();
		JTextField field = new JTextField();
	 
		Object[] message = {
		    "Server:", server,
		    "Database", database,
		    "Password:", password,
		    "Table:", table,
		    "Field:", field
		};

		int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
		    this.server = server.getText();
		    this.database = database.getText();
		    this.password = password.getText();
		    this.table = table.getText();
		    this.field = field.getText();
		} 
	}
	
}
