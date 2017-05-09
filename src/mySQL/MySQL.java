/**
 * Drew Vogt
 * BSD 301 VC
 * 
 * MySQL connector, external project
 * 
 * Creates a connection to mySQL server and then returns ResultSet of submitted query
 * Throws SQLException for handling in program side vs here
 */

package mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

	String server;
	String password;
	String userID;
	
	// Instances for access in parent program
	static Connection con;
	static Statement st;
	static ResultSet rs;
	
	// Constructor for creating the connection with supplied server, password, and user ID
	public MySQL(String server, String userID, String password) throws SQLException{
		
		this.server = server;
		this.password = password;
		this.userID = userID;
		
		con = DriverManager.getConnection("jdbc:mysql://sql3.freemysqlhosting.net?user=sql3156246&password=rMvuGyCQxh");
		st = con.createStatement();
	}
	
	// Performs query and returns as ResultSet
	public ResultSet query (String sql) throws SQLException{
		ResultSet rs = st.executeQuery(sql);
		return rs;
	}
	
	// Closes connection
	public void closeConnection() throws SQLException{
		con.close();
	}
	
}
