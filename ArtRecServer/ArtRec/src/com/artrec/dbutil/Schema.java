package com.artrec.dbutil;

import java.sql.*;

import com.artrec.json.ConvertToJson;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;


/**
 * Schema class that provides the SQL methods to insert and delete data from the database tables
 * @author Vilde
 *
 */
public class Schema extends DbUtil {
	
	/**
	 * 
	 * @return JSON string of all Subjects in the database
	 * @throws Exception if the SQL query can not be called
	 * 
	 * This method will retrieve all the subjects stored in the database. 
	 * There are no input parameters as all data will be retrieved and no
	 * filtering needs to be done.
	 * 
	 */
	public JSONArray qryGetSubjects() throws Exception{
			
		PreparedStatement qry = null;									//Create a statement that can later be used as an SQL call
		Connection conn = null;											//Create a connection to that can be used to connect to the database
		ResultSet rs = null;											//Define a variable for the resulting data
		ConvertToJson jsonConverter = new ConvertToJson();				//Create a variable to convert the result set to JSON
		JSONArray jsonArray = new JSONArray();							//Create a JSON array that will later be used as the output value
		
		try {
			conn = connectToDatabase();									//Connect to the database
			
																		/*
																		 * For prepared statements, strings, integers etc MUST be set.
																		 * As we're only looking for UK airports with a country_code = 'GB' we have 
																		 * the set parameter 1 as "GB" as a String.
																		 * 
																		 * The only time this doesn't apply is if a set integer is in the query string, so:
																		 * "SELECT * FROM table WHERE id = 1"... but nobody ever uses that, as the ID will always be different...
																		*/
			
			qry = conn
					.prepareStatement("SELECT DISTINCT title FROM artrecdb.subjects ORDER BY title");		//Prepare the query searching for distinct subject names
			rs = qry.executeQuery();																		//Execute the prepared query
			
			jsonArray = jsonConverter.convertToJsonArray(rs);												//Convert the result set to JSON

		} catch (SQLException e) {																			//Catch any Exceptions and print them to the console
			e.printStackTrace();
		} finally {
			if (rs != null) {											//If the result set is empty close it
				rs.close();
			}
			if (qry != null) {											//If the query is empty close it.
				qry.close();
			}
			if (conn != null) {											//If the connection is empty, close it.
				conn.close();
			}
		}

		return jsonArray;												//Return the JSON array
			
	}
}
