package com.artrec.methods;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.artrec.dbutil.Schema;
import com.artrec.model.Subject;
import com.artrec.model.User;


@Path("/v1")
public class ApiMethods {
	
	
	//Default empty constructor so it can be called from the servlet
	public ApiMethods() {}
	
	
	/**
	 * 
	 * @return JSON array of all subjects
	 * 
	 * The Get-All-Subjects Method retrieves a list of all of the subjects 
	 * stored in the database. 
	 * There are no parameters as all subjects are retrieved.
	 * 
	 */
	@Path("/subjects")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getAllSubjects(){
	JSONArray jsonArray = new JSONArray();				//Creates a JSON array for all the data retrieved from the query,
														//This will be returned to the application
	Schema dao = new Schema();							//Gives the call access to the Schema allowing the SQL queries to be made
	
	try {												//Attempt a call to the database
		jsonArray =  dao.qryGetSubjects();
	} catch (Exception e) {								//Print out to the console if the call is not completed
		e.printStackTrace();
	}
	
	return jsonArray;									//Returns the output from the SQL query
	}
	
	

	@Path("/users")
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	public void postNewUser(String jsonRequest){
	
		JSONObject json = null;
		try {
			json = new JSONObject(jsonRequest);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		User user = null;
		try {
			user = new User(json.getString("firstname"), json.getString("lastname"));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Schema dao = new Schema();							//Gives the call access to the Schema allowing the SQL queries to be made

		try {												//Attempt a call to the database
			dao.qryPostUser(user);
		} catch (Exception e) {								//Print out to the console if the call is not completed
			e.printStackTrace();
		}
	}
	
	
	@Path("/subjects")
	@POST
	@Consumes (MediaType.APPLICATION_JSON)
	public void postNewUserSubjects(String jsonRequest){
	
		System.out.println("Got subject post request");
		JSONArray json = null;
		try {
			json = new JSONArray(jsonRequest);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<Subject> subjects = new ArrayList<Subject>();
		
		JSONObject jsonUser;
		User user = null;
		try {
			jsonUser = json.getJSONObject(0);
			user = new User(jsonUser.getString("firstname"), jsonUser.getString("lastname"), jsonUser.getInt("id"));
			System.out.println("Found user");
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			for(int i = 1; i < json.length(); i++) {
				subjects.add(new Subject(json.getJSONObject(i).getString("subject")));
			}
			System.out.println("Found subjects: " + subjects.size());
			
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Schema dao = new Schema();							//Gives the call access to the Schema allowing the SQL queries to be made

		try {												//Attempt a call to the database
			System.out.println("trying to post subjects");
			dao.qryPostUserSubjects(user, subjects);
		} catch (Exception e) {								//Print out to the console if the call is not completed
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Reads in the character stream data and converts it to string
	 * @param rd The character stream of type Reader
	 * @return The converted character stream as a string
	 * @throws IOException Throws this if it cannot read the data
	 */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	// Buffers in the JSON string from the URL
	/**
	 * Method to buffer in the data from the URL as a string, then converts it to a JSON array
	 * @param url The URL that the data is being read from
	 * @return JSON encoded data of the string data
	 * @throws IOException Throws this if the data cannot be read
	 * @throws JSONException Throws this if there is a problem with the JSON
	 */
	public static JSONArray readJsonFromUrl(String url) throws IOException,	JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONArray json = new JSONArray('[' + jsonText + ']');
			return json;
		} finally {
			is.close();
		}
	}
}
