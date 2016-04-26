package com.artrec.methods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.artrec.dbutil.Schema;

@Path("/v1") 
public class ApiMethods {

	// Default empty constructor so it can be called from the servlet
	public ApiMethods() {
		//update();

	}
	

	public int update() {
		Schema dao = new Schema();
		try {
			dao.qryUpdateKeywords();
			//dao.updateLatestArticles();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}	
	
	
	/**
	 * 
	 * @return JSON array of all subjects
	 * 
	 *         The Get-All-Subjects Method retrieves a list of all of the
	 *         subjects stored in the database. There are no parameters as all
	 *         subjects are retrieved.
	 * 
	 */
	@Path("/getAllSubjects")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getAllSubjects() {
		JSONArray jsonArray = new JSONArray(); // Creates a JSON array for all
												// the data retrieved from the
												// query,
												// This will be returned to the
												// application
		Schema dao = new Schema(); // Gives the call access to the Schema
									// allowing the SQL queries to be made

		try { // Attempt a call to the database
			jsonArray = dao.qryGetSubjects();
		} catch (Exception e) { // Print out to the console if the call is not
								// completed
			e.printStackTrace();
		}

		return jsonArray; // Returns the output from the SQL query
	}

	/**
	 * 
	 * @return JSON array of all journals
	 * 
	 *         The Get-All-Journals Method retrieves a list of all of the
	 *         journals stored in the database. There are no parameters as all
	 *         journals are retrieved.
	 * 
	 */
	@Path("/getAllJournals")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getAllJournals() {
		JSONArray jsonArray = new JSONArray(); // Creates a JSON array for all
												// the data retrieved from the
												// query,
												// This will be returned to the
												// application
		Schema dao = new Schema(); // Gives the call access to the Schema
									// allowing the SQL queries to be made

		try { // Attempt a call to the database
			jsonArray = dao.qryGetJournals();
		} catch (Exception e) { // Print out to the console if the call is not
								// completed
			e.printStackTrace();
		}

		return jsonArray; // Returns the output from the SQL query
	}
	
	
	@Path("/getJournals")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getAllJournals(@HeaderParam("userid") String userid) {
		
		System.out.println(userid);
		
		JSONArray jsonArray = new JSONArray(); // Creates a JSON array for all
												// the data retrieved from the
												// query,
												// This will be returned to the
												// application
		Schema dao = new Schema(); // Gives the call access to the Schema
									// allowing the SQL queries to be made

		try { // Attempt a call to the database
			jsonArray = dao.qryGetJournals(userid);
		} catch (Exception e) { // Print out to the console if the call is not
								// completed
			e.printStackTrace();
		}

		return jsonArray; // Returns the output from the SQL query
	}

	
	@Path("/getProjectsForUser")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getProjectsForUser(@HeaderParam("userid") String userid) {
		
		System.out.println(userid);
		
		JSONArray jsonArray = new JSONArray(); // Creates a JSON array for all
												// the data retrieved from the
												// query,
												// This will be returned to the
												// application
		Schema dao = new Schema(); // Gives the call access to the Schema
									// allowing the SQL queries to be made

		try { // Attempt a call to the database
			jsonArray = dao.qryGetProjectsForUser(userid);
		} catch (Exception e) { // Print out to the console if the call is not
								// completed
			e.printStackTrace();
		}

		return jsonArray; // Returns the output from the SQL query
	}
	
	/**
	 * 
	 * @return JSON array of user id matching username and password
	 * 
	 *         The Get-User Method is used to authenticate the user
	 * 
	 */
	@Path("/user")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getUser(@HeaderParam("username") String username, @HeaderParam("password") String password) {

		try {
			if (username != null && password != null) {
				username = java.net.URLDecoder.decode(username, "UTF-8");
				password = java.net.URLDecoder.decode(password, "UTF-8");
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(username);
		System.out.println(password);

		JSONArray jsonArray = new JSONArray(); // Creates a JSON array for all
												// the data retrieved from the
												// query,
												// This will be returned to the
												// application
		Schema dao = new Schema(); // Gives the call access to the Schema
									// allowing the SQL queries to be made

		try { // Attempt a call to the database
			jsonArray = dao.qryAuthenticateUser(username.trim(), password.trim());
			System.out.println(jsonArray.length() + "");
		} catch (Exception e) { // Print out to the console if the call is not
								// completed
			e.printStackTrace();
		}

		return jsonArray; // Returns the output from the SQL query
	}
	
	
	/**
	 * 
	 * @return JSON array of user id matching username and password
	 * 
	 *         The Get-User Method is used to authenticate the user
	 * 
	 */
	
	@Path("/userexists")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject getUser(@HeaderParam("username") String username) {

		try {
			if (username != null) {
				username = java.net.URLDecoder.decode(username, "UTF-8");
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(username);

		boolean exists = true;
		Schema dao = new Schema(); // Gives the call access to the Schema
									// allowing the SQL queries to be made

		try { // Attempt a call to the database
			exists = dao.usernameExists(username.trim());
			System.out.println(exists+ "");
		} catch (Exception e) { // Print out to the console if the call is not
								// completed
			e.printStackTrace();
		}

		JSONObject response = new JSONObject();
		try {
			response.put("exists", exists);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response; // Returns the output from the SQL query
	}
	
	/**
	 * 
	 *         The Get-User Method is used to authenticate the user
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	@Path("/getJournalsForSubjects")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getJournalsForSubjects(@HeaderParam("ids") String ids) throws UnsupportedEncodingException {

		ids = java.net.URLDecoder.decode(ids, "UTF-8");
		System.out.println(ids);
		
//		String[] singleIds = ids.split(",");
//		
//		Integer[] newArray = new Integer[singleIds.length];
//		int i = 0;
//		for (String value : singleIds) {
//		    newArray[i++] = Integer.valueOf(value);
//		    System.out.print(value);
//		}

		JSONArray jsonArray = new JSONArray(); // Creates a JSON array for all
												// the data retrieved from the
												// query,
												// This will be returned to the
												// application
		Schema dao = new Schema(); // Gives the call access to the Schema
									// allowing the SQL queries to be made

		try { // Attempt a call to the database
			jsonArray = dao.qryGetJournalsForSubjects(ids);
			System.out.println(jsonArray.length() + "");
		} catch (Exception e) { // Print out to the console if the call is not
								// completed
			e.printStackTrace();
		}

		return jsonArray; // Returns the output from the SQL query
	}
	
	@Path("/getArticlesForProject")
	@GET
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getArticlesForProject(@HeaderParam("userid") String idProject) throws UnsupportedEncodingException {
		//IS REALLY PROJECT ID EVENTHOUGH IT IT CALLED USERID FOR METHOD REUSE
		
		
		idProject = java.net.URLDecoder.decode(idProject, "UTF-8");
		System.out.println(idProject);
		

		JSONArray jsonArray = new JSONArray(); 
		Schema dao = new Schema(); 

		try { 
			jsonArray = dao.qryGetArticlesForProject(Integer.parseInt(idProject));
			System.out.println(jsonArray.length() + "");
			for(int i = 0; i < jsonArray.length(); i++) {
				System.out.println(jsonArray.get(i).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonArray; // Returns the output from the SQL query
	}
	
	
	/**
	 * 
	 * @return JSON array of user id matching username and password
	 * 
	 *         The Get-User Method is used to authenticate the user
	 * 
	 */
	@Path("/user")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray InsertUser(@FormParam("username") String username, @FormParam("password") String password) {

		try {
			if (username != null && password != null) {
				username = java.net.URLDecoder.decode(username, "UTF-8");
				password = java.net.URLDecoder.decode(password, "UTF-8");
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(username);
		System.out.println(password);
		System.out.println("inserting...");

		JSONArray jsonArray = new JSONArray(); // Creates a JSON array for all
												// the data retrieved from the
												// query,
												// This will be returned to the
												// application
		Schema dao = new Schema(); // Gives the call access to the Schema
									// allowing the SQL queries to be made

		try { // Attempt a call to the database
			long result = dao.qryInsertUser(username.trim(), password.trim());
			System.out.println(result + " newuserid");
			jsonArray = dao.qryAuthenticateUser(username.trim(), password.trim());
			System.out.println(jsonArray.length() + "");
		} catch (Exception e) { // Print out to the console if the call is not
								// completed
			e.printStackTrace();
		}

		return jsonArray; // Returns the output from the SQL query
	}
	
	/**
	 * 
	 * @return JSON array of user id matching username and password
	 * 
	 *         The Get-User Method is used to authenticate the user
	 * 
	 */
	@Path("/userSubject")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void InsertUserSubject(@FormParam("userid") String userid, @FormParam("ids") String ids) {

		try {
			if (ids != null) {
				ids = java.net.URLDecoder.decode(ids, "UTF-8").trim();
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(userid);
		System.out.println(ids);
		System.out.println("inserting...");

		JSONArray jsonArray = new JSONArray(); // Creates a JSON array for all
												// the data retrieved from the
												// query,
												// This will be returned to the
												// application
		Schema dao = new Schema(); // Gives the call access to the Schema
									// allowing the SQL queries to be made

		try { // Attempt a call to the database
			int[] result = dao.qryInsertUserSubject(userid, ids);
			System.out.println(result.length + "");
		} catch (Exception e) { // Print out to the console if the call is not
								// completed
			e.printStackTrace();
		}

	}
	
	
	@Path("/userJournal")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public synchronized void InsertUserJournal(@FormParam("userid") String userid, @FormParam("ids") String ids) {

		try {
			if (ids != null) {
				ids = java.net.URLDecoder.decode(ids, "UTF-8").trim();
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(userid);
		System.out.println(ids);
		System.out.println("inserting...");

		JSONArray jsonArray = new JSONArray(); // Creates a JSON array for all
												// the data retrieved from the
												// query,
												// This will be returned to the
												// application
		Schema dao = new Schema(); // Gives the call access to the Schema
									// allowing the SQL queries to be made

		try { // Attempt a call to the database
			int[] result = dao.qryInsertUserJournal(userid, ids);
			System.out.println(result.length + "");
		} catch (Exception e) { // Print out to the console if the call is not
								// completed
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @return JSON array of all journals
	 * 
	 *         The Get-All-Journals Method retrieves a list of all of the
	 *         journals stored in the database. There are no parameters as all
	 *         journals are retrieved.
	 * 
	 */
	@Path("/getAllArticlesForJournal")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getAllArticlesForJournal(@Context UriInfo info) {
		String journalISSN = info.getQueryParameters().getFirst("issn");

		JSONArray jsonArray = new JSONArray(); // Creates a JSON array for all
												// the data retrieved from the
												// query,
												// This will be returned to the
												// application
		Schema dao = new Schema(); // Gives the call access to the Schema
									// allowing the SQL queries to be made

		try { // Attempt a call to the database
			jsonArray = dao.qryGetArticlesForJournal(journalISSN);
		} catch (Exception e) { // Print out to the console if the call is not
								// completed
			e.printStackTrace();
		}

		return jsonArray; // Returns the output from the SQL query
	}
	
//	@Path("/getArticlesForJournalBySearch")
//	@POST
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public JSONArray getArticlesForJournalBySearch(@FormParam("issn") String journalISSN, @FormParam("searchterms") String[] searchTerms) {
//	
//		JSONArray jsonArray = new JSONArray(); // Creates a JSON array for all
//												// the data retrieved from the
//												// query,
//												// This will be returned to the
//												// application
//		Schema dao = new Schema(); // Gives the call access to the Schema
//									// allowing the SQL queries to be made
//
//		try { // Attempt a call to the database
//			jsonArray = dao.qryGetArticlesForJournalBySearch(journalISSN, searchTerms);
//		} catch (Exception e) { // Print out to the console if the call is not
//								// completed
//			e.printStackTrace();
//		}
//
//		return jsonArray; // Returns the output from the SQL query
//	}


	/**
	 * Reads in the character stream data and converts it to string
	 * 
	 * @param rd
	 *            The character stream of type Reader
	 * @return The converted character stream as a string
	 * @throws IOException
	 *             Throws this if it cannot read the data
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
	 * Method to buffer in the data from the URL as a string, then converts it
	 * to a JSON array
	 * 
	 * @param url
	 *            The URL that the data is being read from
	 * @return JSON encoded data of the string data
	 * @throws IOException
	 *             Throws this if the data cannot be read
	 * @throws JSONException
	 *             Throws this if there is a problem with the JSON
	 */
	public static JSONArray readJsonFromUrl(String url) throws IOException,
			JSONException {
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
