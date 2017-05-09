package processExplorer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;


import mySQL.MySQL;

public class DBQuery {
	
	// Creates empty ArrayList of type double.  
	private ArrayList<Double> rawData = new ArrayList<>(Arrays.asList());

	DBQuery(DBInfo dbInfo) {

		// Builds a query and sends to the MySQL object and receives the dataset
		try {
			MySQL mySQL = new MySQL(dbInfo.server, dbInfo.database, dbInfo.password);
			String sqlQuery = "SELECT " + dbInfo.field + " FROM " + dbInfo.database+"." + dbInfo.table;
			ResultSet rs = mySQL.query(sqlQuery);
				while(rs.next()) {
					rawData.add((double) rs.getFloat(dbInfo.field));
				}
			mySQL.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Double> returnQuery() {
		return rawData;
	}
}
