package com.artrec.json;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import java.sql.ResultSet;
import org.owasp.esapi.ESAPI;
/**
 * Class to convert result set data to a JSON array
 * @author Vilde
 * Original author 308tube
 * https://github.com/308tube/com.youtube.rest/blob/master/src/com/youtube/util/ToJSON.java
 * Code adapted to suit this project 
 */
public class ConvertToJson {

	/**
	 * Method to loop through the result set data, convert it to the JSON type 
	 * and insert it into a JSON object
	 * @param rs The result set data to be converted into a JSON array
	 * @return JSON array of the converted ResultSet data
	 * @throws Exception Throws an exception if something went wrong
	 */
	public JSONArray convertToJsonArray(ResultSet rs) throws Exception {

		JSONArray jsonArray = new JSONArray();
		String temp = null;

		try {
			// Get the column names
			java.sql.ResultSetMetaData rsMetaData = rs.getMetaData();

			while (rs.next()) {
				// how many columns?
				int cols = rsMetaData.getColumnCount();
				// each row will be a JSON object
				JSONObject jsonObj = new JSONObject();

				// Loop through the columns, put them in the JSON object
				for (int i = 1; i < cols + 1; i++) {
					String colName = rsMetaData.getColumnName(i);

					// Now to sort out the data types
					if (rsMetaData.getColumnType(i) == java.sql.Types.ARRAY) {
						jsonObj.put(colName, rs.getArray(colName));
						System.out.println("ToJson: ARRAY");
					}

					if (rsMetaData.getColumnType(i) == java.sql.Types.BIGINT) {
						jsonObj.put(colName, rs.getInt(colName));
						System.out.println("ToJson: BIGINT");
					}

					if (rsMetaData.getColumnType(i) == java.sql.Types.BLOB) {
						jsonObj.put(colName, rs.getBlob(colName));
						System.out.println("ToJson: BLOB");
					}

					if (rsMetaData.getColumnType(i) == java.sql.Types.BOOLEAN) {
						jsonObj.put(colName, rs.getBoolean(colName));
						System.out.println("ToJson: BOOLEAN");
					}

					
					if (rsMetaData.getColumnType(i) == java.sql.Types.CHAR) {
						jsonObj.put(colName, rs.getString(colName));
						System.out.println("ToJson: CHAR");
					}

					if (rsMetaData.getColumnType(i) == java.sql.Types.DATE) {
						jsonObj.put(colName, rs.getDate(colName));
						System.out.println("ToJson: DATE");
					}
					
					if (rsMetaData.getColumnType(i) == java.sql.Types.TIMESTAMP) {
						jsonObj.put(colName, rs.getTimestamp(colName));
						System.out.println("ToJson: TIMESTAMP");
					}
					
					if (rsMetaData.getColumnType(i) == java.sql.Types.DECIMAL) {
						jsonObj.put(colName, Float.parseFloat(rs.getString(colName)));
						System.out.println("ToJson: DECIMAL");
					}

					if (rsMetaData.getColumnType(i) == java.sql.Types.DOUBLE) {
						jsonObj.put(colName, rs.getDouble(colName));
						System.out.println("ToJson: DOUBLE");
					}
					
					if (rsMetaData.getColumnType(i) == java.sql.Types.FLOAT) {
						jsonObj.put(colName, rs.getFloat(colName));
						System.out.println("ToJson: FLOAT");
					}

					if (rsMetaData.getColumnType(i) == java.sql.Types.INTEGER) {
						jsonObj.put(colName, rs.getInt(colName));
						System.out.println("ToJson: INTEGER");
					}
					
					//Extra stuff needs to be done here to make it browser safe and safe from SQL injection
					if (rsMetaData.getColumnType(i) == java.sql.Types.VARCHAR) {
						temp = rs.getString(colName);
						temp = ESAPI.encoder().canonicalize(temp); //Un-encode it
						//temp = ESAPI.encoder().encodeForHTML(temp); //Make it browser safe (remove HTML tagging)
						jsonObj.put(colName, temp);
						System.out.println("ToJson: VARCHAR");
					}
					
					if (rsMetaData.getColumnType(i) == java.sql.Types.TINYINT) {
						jsonObj.put(colName, rs.getInt(colName));
						System.out.println("ToJson: TINYINT");
					}
				}

				// Put the new object into the array
				jsonArray.put(jsonObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonArray;

	}

}
