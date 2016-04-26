package com.artrec.dbutil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import com.artrec.json.ConvertToJson;
import com.artrec.model.Article;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.xml.sax.InputSource;


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
			
			qry = conn
					.prepareStatement("SELECT * FROM artrecdb.subjects ORDER BY title");		//Prepare the query searching for distinct subject names
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
	
	  public boolean usernameExists(String user) throws SQLException {
			
			PreparedStatement qry = null;									//Create a statement that can later be used as an SQL call
			Connection conn = null;											//Create a connection to that can be used to connect to the database
			ResultSet rs = null;											//Define a variable for the resulting data
		
	        int counter = 1;
	        boolean exists = true;
	        
	        try {
	        	conn = connectToDatabase();									//Connect to the database
	        	qry = conn.prepareStatement("SELECT COUNT(`username`) FROM users WHERE `username` = ?");
	        	qry.setString(1, user);
	            rs = qry.executeQuery();
	            if (rs.next()) {
	                counter = rs.getInt(1);
	            }
	            if(counter > 0) {
	            	exists = true;
	            } else {
	            	exists = false;
	            }
	            
	        } catch (SQLException ex) {
	            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
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
	        return exists;
	    }
	
	
	public JSONArray qryAuthenticateUser(String username, String password) throws Exception{
		
		PreparedStatement qry = null;									//Create a statement that can later be used as an SQL call
		Connection conn = null;											//Create a connection to that can be used to connect to the database
		ResultSet rs = null;											//Define a variable for the resulting data
		ConvertToJson jsonConverter = new ConvertToJson();				//Create a variable to convert the result set to JSON
		JSONArray jsonArray = new JSONArray();							//Create a JSON array that will later be used as the output value
		
		try {
			conn = connectToDatabase();									//Connect to the database
			
			qry = conn.prepareStatement("SELECT id FROM artrecdb.users WHERE username = ? AND password = ?");		//Prepare the query searching for distinct subject names
			qry.setString(1, username);
			qry.setString(2, password);
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
	
public int qryInsertUser(String username, String password) throws Exception{
		
		PreparedStatement qry = null;									//Create a statement that can later be used as an SQL call
		Connection conn = null;											//Create a connection to that can be used to connect to the database
		int result = 0;											//Define a variable for the resulting data
		ConvertToJson jsonConverter = new ConvertToJson();				//Create a variable to convert the result set to JSON
		JSONArray jsonArray = new JSONArray();							//Create a JSON array that will later be used as the output value
		
		try {
			conn = connectToDatabase();									//Connect to the database
			
			qry = conn.prepareStatement("INSERT INTO artrecdb.users (username, password) VALUES (?, ?)");		//Prepare the query searching for distinct subject names
			qry.setString(1, username);
			qry.setString(2, password);
			result = qry.executeUpdate();																		//Execute the prepared query			
										//Convert the result set to JSON

		} catch (SQLException e) {																			//Catch any Exceptions and print them to the console
			e.printStackTrace();
		} finally {
			
			if (qry != null) {											//If the query is empty close it.
				qry.close();
			}
			if (conn != null) {											//If the connection is empty, close it.
				conn.close();
			}
		}

		return result;												//Return the JSON array
			
	}

	public JSONArray qryGetJournals() throws Exception {
		PreparedStatement qry = null;									//Create a statement that can later be used as an SQL call
		Connection conn = null;											//Create a connection to that can be used to connect to the database
		ResultSet rs = null;											//Define a variable for the resulting data
		ConvertToJson jsonConverter = new ConvertToJson();				//Create a variable to convert the result set to JSON
		JSONArray jsonArray = new JSONArray();							//Create a JSON array that will later be used as the output value
		
		try {
			conn = connectToDatabase();									//Connect to the database

			qry = conn.prepareStatement("SELECT * FROM artrecdb.journals ORDER BY title");		//Prepare the query searching for distinct subject names
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
	
	public JSONArray qryUpdateKeywords() throws Exception {
		PreparedStatement qry = null;									//Create a statement that can later be used as an SQL call
		Connection conn = null;											//Create a connection to that can be used to connect to the database
		ResultSet rs = null;											//Define a variable for the resulting data
		ConvertToJson jsonConverter = new ConvertToJson();				//Create a variable to convert the result set to JSON
		JSONArray jsonArray = new JSONArray();							//Create a JSON array that will later be used as the output value
		
		ArrayList<String> keywordLines = new ArrayList<>();
		
		try {
			conn = connectToDatabase();									//Connect to the database

			qry = conn.prepareStatement("SELECT title FROM artrecdb.articles ORDER BY title");		//Prepare the query searching for distinct subject names
			rs = qry.executeQuery();																		//Execute the prepared query
			while(rs.next()){
				keywordLines.add(rs.getString("title").toLowerCase().replaceAll("[^a-zA-Z- ]", ""));
			}
			

			
			qry.close();
				
			ArrayList<String> keywords = new ArrayList<>();
			
			for(String line : keywordLines) {
				for(String s : line.trim().split(" ")) {
					keywords.add(s);
				}
			}
			
			// add elements to al, including duplicates
//			Set<String> hs = new HashSet<>();
//			hs.addAll(keywords);
//			keywords.clear();
//			keywords.addAll(hs);
			
			System.out.println("Numbers of keywords: " + keywords.size());
			
			qry = conn.prepareStatement("INSERT IGNORE INTO keywordpop (keyword) VALUES (?),(?),(?),(?),(?)");	
			
			if(keywords.size()%5 == 0) {
				int i = 1;
				for(String s : keywords) {
					if(i <= 5) {
						qry.setString(i, s);
					} else {
						qry.addBatch();
						i=1;
						qry.setString(i, s);				
					}
					i++;
				}
				
				qry.executeBatch();
			} else {
				ArrayList<String> extraStrings = new ArrayList<>();
				while(keywords.size()%5 != 0) {
					extraStrings.add(keywords.remove(0));
				}
		
				int i = 1;
				for(String s : keywords) {
					if(i <= 5) {
						qry.setString(i, s);
					} else {
						qry.addBatch();
						i=1;
						qry.setString(i, s);				
					}
					i++;
				}		
				qry.executeBatch();
				qry = conn.prepareStatement("INSERT INTO keywords (keyword) VALUES (?)");	
				for(String s : extraStrings) {
					qry.setString(1, s);
					qry.addBatch();
				}
				qry.executeBatch();
				
			}
			
			//INSERT 500 top hits into keywords
//			INSERT INTO keywords (keyword) SELECT keywordpop.keyword
//			FROM keywordpop
//			GROUP BY keywordpop.keyword 
//			ORDER BY count(*) DESC
//			LIMIT 500

			
			
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
	
	public void updateLatestArticles() throws SQLException {
		
		PreparedStatement qry = null;									//Create a statement that can later be used as an SQL call
		Connection conn = null;											//Create a connection to that can be used to connect to the database
		ResultSet rs = null;											//Define a variable for the resulting data

		try {
			conn = connectToDatabase();									//Connect to the database
			
			qry = conn.prepareStatement("SELECT issn FROM artrecdb.journals WHERE issn IS NOT NULL");		//Prepare the query searching for distinct subject names
			rs = qry.executeQuery();																		//Execute the prepared query
			
			ArrayList<String> issns = new ArrayList<>();
			
			while(rs.next()){
				issns.add(rs.getString(1));
			}
			
			rs.close();
			qry.close();
			
		
			for(String s : issns) {
				System.out.println("Journal issn: " + s);
				qry = conn.prepareStatement("INSERT INTO artrecdb.articles(title, link, date, journalIssn, description)  SELECT ? , ? , ? , ? , ? FROM dual WHERE NOT EXISTS (SELECT * FROM artrecdb.articles WHERE artrecdb.articles.title = ? AND artrecdb.articles.journalIssn= ?)");		
			
				ArrayList<Article> articles = qryGetObjectArticlesForJournal(s);
				
				for(Article a : articles) {
					qry.setString(1, a.getTitle());
					qry.setURL(2, a.getLink());
					qry.setDate(3, a.getPublicationDate());
					qry.setString(4, a.getJournalIssn());
					qry.setString(5, a.getDescription().replace("SyndContentImpl.mode=null\nSyndContentImpl.type=text/plain\nSyndContentImpl.interface=interface com.sun.syndication.feed.synd.SyndContent\nSyndContentImpl.value=", ""));
					qry.setString(6, a.getTitle());
					qry.setString(7, a.getJournalIssn());
					qry.addBatch();
				}
				
				qry.executeBatch();
				qry.close();
			}
			
			System.out.println("Journal count: " + issns.size());
			
			
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

		
	}
	
	public JSONArray qryGetArticlesForJournal(String journalISSN) {
		JSONArray response = new JSONArray();
		try {
			SyndFeed feed = getSyndFeedForUrl("http://www.journaltocs.ac.uk/api/journals/"+journalISSN+"?output=articles&user=vilde2.birkehaug@live.uwe.ac.uk");
			List entries = feed.getEntries();
			Iterator<SyndEntry> itEntries = entries.iterator();
			JSONObject json;
			
			while (itEntries.hasNext()) {
				json = new JSONObject();
				SyndEntry entry = itEntries.next();
				json.append("title", entry.getTitle());
				json.append("link", entry.getLink());
				json.append("publicationDate", entry.getPublishedDate());
				json.append("description", entry.getDescription());
				response.put(json);			
			}
		  } catch (Exception e) {

			e.printStackTrace();

		  }

		return response;
	}
	
	
	public JSONArray qryGetArticlesForProject(int projectId) throws Exception {
		PreparedStatement qry = null;									//Create a statement that can later be used as an SQL call
		Connection conn = null;											//Create a connection to that can be used to connect to the database
		ResultSet rs = null;											//Define a variable for the resulting data
		ConvertToJson jsonConverter = new ConvertToJson();				//Create a variable to convert the result set to JSON
		JSONArray jsonArray = new JSONArray();							//Create a JSON array that will later be used as the output value
		
		try {
			conn = connectToDatabase();									//Connect to the database

			qry = conn.prepareStatement("SELECT articles.idArticle, articles.title, articles.date "
					+ "FROM artrecdb.articles "
					+ "JOIN (SELECT keywords.keyword "
					+ "FROM artrecdb.keywords "
					+ "JOIN artrecdb.project_keyword ON keywords.idKeyword = project_keyword.idKeyword "
					+ "JOIN artrecdb.projects ON project_keyword.idProject = projects.idProject "
					+ "WHERE projects.idProject = ?) "
					+ "AS selected_project_keywords "
					+ "ON articles.title LIKE CONCAT('%', selected_project_keywords.keyword,'%') "
					+ "ORDER BY articles.title "
					+ "LIMIT 20;");		//Prepare the query searching for distinct subject names
				
			qry.setInt(1, projectId);
			rs = qry.executeQuery();																		//Execute the prepared query
			
			while(rs.next()) {
				System.out.println(rs.toString());
			}
			rs.beforeFirst();
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
	

	public ArrayList<Article> qryGetObjectArticlesForJournal(String journalISSN) {
		ArrayList<Article> response = new ArrayList<Article>();
		try {
			SyndFeed feed = getSyndFeedForUrl("http://www.journaltocs.ac.uk/api/journals/"+journalISSN+"?output=articles&user=vilde2.birkehaug@live.uwe.ac.uk");
			List entries = feed.getEntries();
			Iterator<SyndEntry> itEntries = entries.iterator();
			Article article;	
			
			while (itEntries.hasNext()) {
			
				SyndEntry entry = itEntries.next();
				//if(entry.getTitle().length() < 500 && entry.getDescription().getValue().toString().length() < 5000) {
				try {
					article = new Article(entry.getTitle(), new URL(entry.getLink()), convertRssDateToSqlDate(entry.getPublishedDate()), journalISSN, entry.getDescription().toString());
					response.add(article);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				//}
			}
		  } catch (Exception e) {

			e.printStackTrace();

		  }

		return response;
	}
	
	private java.sql.Date convertRssDateToSqlDate(java.util.Date rssDate) {
		if(rssDate != null) {
			java.sql.Date dt = new java.sql.Date(rssDate.getTime());
	    	return dt;
		} else {
			return null;
		}
		
	}
	
	private String replaceChars(String string) {
		return string.replace("[", "").replace("]", "").replace("\"", "").replace("\\", "");
	}
	
	public SyndFeed getSyndFeedForUrl(String url) throws MalformedURLException, IOException, IllegalArgumentException, FeedException {
		 
		SyndFeed feed = null;		
		InputStream is = null;
	 
		try {
	 
			URLConnection openConnection = new URL(url).openConnection();			
			is = new URL(url).openConnection().getInputStream();
			if("gzip".equals(openConnection.getContentEncoding())){
				is = new GZIPInputStream(is);
			}			
			InputSource source = new InputSource(is);			
			SyndFeedInput input = new SyndFeedInput();
			feed = input.build(source);
	 
		} catch (Exception e){
			System.out.println("Exception occured when building the feed object out of the url");				
		} finally {
			if( is != null)	is.close();
		}
	 
		return feed; 
	}


	public JSONArray qryGetJournalsForSubjects(String ids) throws Exception {
		PreparedStatement qry = null;									//Create a statement that can later be used as an SQL call
		Connection conn = null;											//Create a connection to that can be used to connect to the database
		ResultSet rs = null;											//Define a variable for the resulting data
		ConvertToJson jsonConverter = new ConvertToJson();				//Create a variable to convert the result set to JSON
		JSONArray jsonArray = new JSONArray();							//Create a JSON array that will later be used as the output value
		
		String[] splitIds = ids.split(",");
		
		int[] intIds = new int[splitIds.length];
		String inString = "";
		
		for(int i = 0; i < intIds.length; i++) {
			intIds[i] = Integer.valueOf(splitIds[i]);
			inString += "?,";
		}
		
		inString = inString.substring(0, inString.length()-1);

		
		try {
			conn = connectToDatabase();									//Connect to the database

			qry = conn.prepareStatement("SELECT journals.idJournal, journals.title, journals.issn,journals.eissn "
					+ "FROM journals "
					+ "JOIN subject_journal "
					+ "ON journals.idJournal = subject_journal.idJournal "
					+ "JOIN subjects "
					+ "ON subject_journal.idSubject = subjects.idSubject "
					+ "WHERE subjects.idSubject IN ("+inString+") "
							+ "ORDER BY journals.title");		//Prepare the query searching for distinct subject names
			
			int o = 1;
			for(int i : intIds) {
				qry.setInt(o, i);
				o++;
			}		
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


	public int[] qryInsertUserSubject(String userid, String ids) throws SQLException {
		PreparedStatement qry = null;									//Create a statement that can later be used as an SQL call
		Connection conn = null;											//Create a connection to that can be used to connect to the database
		int[] result = null;											//Define a variable for the resulting data

		String[] splitIds = ids.split(",");
		
		int[] intIds = new int[splitIds.length];
		
		for(int i = 0; i < intIds.length; i++) {
			intIds[i] = Integer.valueOf(splitIds[i]);
		}
		
		try {
			conn = connectToDatabase();									//Connect to the database
		
			qry = conn.prepareStatement("INSERT INTO artrecdb.user_subject (idUser, idSubject) VALUES (?, ?)");		//Prepare the query searching for distinct subject names
			System.out.println(userid);
			for(int subject : intIds) {
				qry.setInt(1, Integer.valueOf(userid));
				qry.setInt(2, subject);
				qry.addBatch();
			}
			result = qry.executeBatch();																		//Execute the prepared query			
										//Convert the result set to JSON

		} catch (SQLException e) {																			//Catch any Exceptions and print them to the console
			e.printStackTrace();
		} finally {
			
			if (qry != null) {											//If the query is empty close it.
				qry.close();
			}
			if (conn != null) {											//If the connection is empty, close it.
				conn.close();
			}
		}

		return result;												//Return the JSON array
	}
	
	public int[] qryInsertUserJournal(String userid, String ids) throws SQLException {
		PreparedStatement qry = null;									//Create a statement that can later be used as an SQL call
		Connection conn = null;											//Create a connection to that can be used to connect to the database
		int[] result = null;											//Define a variable for the resulting data

		String[] splitIds = ids.split(",");
		
		int[] intIds = new int[splitIds.length];
		
		for(int i = 0; i < intIds.length; i++) {
			intIds[i] = Integer.valueOf(splitIds[i]);
		}
		
		try {
			conn = connectToDatabase();									//Connect to the database
		
			qry = conn.prepareStatement("INSERT INTO artrecdb.user_journal (idUser, idJournal) VALUES (?, ?)");		//Prepare the query searching for distinct subject names
			System.out.println(userid);
			for(int journal : intIds) {
				qry.setInt(1, Integer.valueOf(userid));
				qry.setInt(2, journal);
				qry.addBatch();
			}
			result = qry.executeBatch();																		//Execute the prepared query			
										//Convert the result set to JSON

		} catch (SQLException e) {																			//Catch any Exceptions and print them to the console
			e.printStackTrace();
		} finally {
			
			if (qry != null) {											//If the query is empty close it.
				qry.close();
			}
			if (conn != null) {											//If the connection is empty, close it.
				conn.close();
			}
		}

		return result;												//Return the JSON array
	}


	public JSONArray qryGetJournals(String userid) throws Exception {
		PreparedStatement qry = null;									//Create a statement that can later be used as an SQL call
		Connection conn = null;											//Create a connection to that can be used to connect to the database
		ResultSet rs = null;											//Define a variable for the resulting data
		ConvertToJson jsonConverter = new ConvertToJson();				//Create a variable to convert the result set to JSON
		JSONArray jsonArray = new JSONArray();							//Create a JSON array that will later be used as the output value
		
		try {
			conn = connectToDatabase();									//Connect to the database

			qry = conn.prepareStatement("SELECT * FROM artrecdb.journals JOIN artrecdb.user_journal ON journals.idJournal = user_journal.idJournal WHERE idUser = ?");		//Prepare the query searching for distinct subject names
			qry.setInt(1, Integer.valueOf(userid));
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
	
	public JSONArray qryGetProjectsForUser(String userid) throws Exception {
		PreparedStatement qry = null;									//Create a statement that can later be used as an SQL call
		Connection conn = null;											//Create a connection to that can be used to connect to the database
		ResultSet rs = null;											//Define a variable for the resulting data
		ConvertToJson jsonConverter = new ConvertToJson();				//Create a variable to convert the result set to JSON
		JSONArray jsonArray = new JSONArray();							//Create a JSON array that will later be used as the output value
		
		try {
			conn = connectToDatabase();									//Connect to the database

			qry = conn.prepareStatement("SELECT projects.idProject, projects.title, keywords.keyword, projects.idUser, keywords.idKeyword FROM projects JOIN project_keyword ON projects.idProject = project_keyword.idProject JOIN keywords ON project_keyword.idKeyword = keywords.idKeyword WHERE idUser = ?");		//Prepare the query searching for distinct subject names
			qry.setInt(1, Integer.valueOf(userid));
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


	public JSONArray qryGetArticlesForJournalBySearch(String journalISSN,
			String[] searchTerms) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
