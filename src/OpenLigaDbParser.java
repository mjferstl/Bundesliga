import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

public class OpenLigaDbParser {

	// Variables
	// - Patterns
	private static Pattern matchTimePattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})");

	// - Strings
	private static String urlBundesliga1Matches = "https://www.openligadb.de/api/getmatchdata/bl1";
	private static String urlBundesliga1Table2019 = "https://www.openligadb.de/api/getbltable/bl1/2019";


	/**
	 * method to get all current Bundesliga matches from OpenLigaDB
	 * @return List containing objects of type match
	 */
	public static List<Match> getCurrentBundesligaMatches() {
		// create new List
		List<Match> matches = new ArrayList<Match>();

		// get matches from OpenLigaDb response
		String jsonResponse = getOpenLigaResponse(urlBundesliga1Matches);
		matches = getMatchesFromJsonResponse(jsonResponse);

		return matches;
	}

	public static List<FootballTeam> getBundesligaTable() {
		// create new List
		List<FootballTeam> table = new ArrayList<FootballTeam>();

		// get table from OpenLigaDb response
		String jsonResponse = getOpenLigaResponse(urlBundesliga1Table2019);
		table = getTableFromJsonResponse(jsonResponse);			

		return table;		
	}

	/**
	 * get the JSON response of OpenLigaDB as String
	 * @param url: String containing the url for the request
	 * @return: String containing the response in JSON format
	 */
	private static String getOpenLigaResponse(String url) {		
		URL urlObject = getJsonResponse(url);		
		String response = readResponse(urlObject);		
		return response;
	}

	/**
	 * get the JSON response 
	 * @param urlString: String containing the url for the request
	 * @return: Object of type URL
	 */
	private static URL getJsonResponse(String urlString) {

		// initialize local variables
		URL url = null;
		HttpURLConnection conn = null;
		int responseCode = 0;

		// get JSON response using a String containing the url
		try {
			url = new URL(urlString);
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			responseCode = conn.getResponseCode();					
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// check response code
		if(responseCode != 200) {
			throw new RuntimeException("HttpResponseCode: " + responseCode);
		}

		// return URL object
		return url;
	}


	/**
	 * parse JSON response to a string
	 * @param url: Object of type URL
	 * @return: String containing the JSON response
	 */
	private static String readResponse(URL url) {

		String response = "";

		// Get content of the response
		Scanner sc = null;
		try {
			sc = new Scanner(url.openStream(),"UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// read response and store it as string
		while(sc.hasNext())	{ response+=sc.nextLine(); }
		sc.close();

		return response;
	}

	/**
	 * create a list containing objects of type Match using a JSON reponse
	 * @param jsonResponse
	 * @return List of objects of type "Match"
	 */
	private static List<Match> getMatchesFromJsonResponse(String jsonResponse) {

		List<Match> m = new ArrayList<Match>();
		// Parse JSON Response		
		JSONArray jsonArray = new JSONArray(jsonResponse);
		for (int i=0; i<jsonArray.length(); i++) {
			// add match to list
			m.add(OpenLigaDbParser.JsonToMatch(jsonArray.getJSONObject(i)));					
		}
		return m;
	}


	/**
	 * parse jsonObject containing a match to a Object of type Match
	 * @param jsonObject containing part of the JSON response from OpenLigaDB containing a match as JSONObject
	 * @return: Object of type Match
	 */
	private static Match JsonToMatch(JSONObject jsonObject) {	

		// ---- get entries of the JSONObject
		// - home team
		String homeTeamName = jsonObject.getJSONObject("Team1").get("TeamName").toString();
		String homeTeamShortName = jsonObject.getJSONObject("Team1").get("ShortName").toString();
		String homeTeamIconUrl = jsonObject.getJSONObject("Team1").get("TeamIconUrl").toString();		
		// - away team
		String awayTeamName = jsonObject.getJSONObject("Team2").get("TeamName").toString();
		String awayTeamShortName = jsonObject.getJSONObject("Team2").get("ShortName").toString();
		String awayTeamIconUrl = jsonObject.getJSONObject("Team2").get("TeamIconUrl").toString();

		// - general 
		String matchDateTime = jsonObject.getString("MatchDateTime");
		boolean matchIsFinished = jsonObject.getBoolean("MatchIsFinished");


		// ---- create objects for each team
		// home team
		FootballTeam homeTeam = new FootballTeam(homeTeamName);
		homeTeam.setIconUrl(homeTeamIconUrl);		
		homeTeam.setShortName(homeTeamShortName);
		// away team
		FootballTeam awayTeam = new FootballTeam(awayTeamName);
		awayTeam.setIconUrl(awayTeamIconUrl);
		awayTeam.setShortName(awayTeamShortName);		

		// ---- create object of type Match and add teams and general information
		Match match = new Match();
		// add teams to match
		match.setHomeTeam(homeTeam);
		match.setAwayTeam(awayTeam);

		// set boolean if match is finished
		match.setFinished(matchIsFinished);					

		// optional: add match time 
		Matcher m = matchTimePattern.matcher(matchDateTime);
		if (m.matches()) {
			int year = Integer.valueOf(m.group(1).trim());
			int month = Integer.valueOf(m.group(2).trim());
			int day = Integer.valueOf(m.group(3).trim());
			int hour = Integer.valueOf(m.group(4).trim());
			int minute = Integer.valueOf(m.group(5).trim());

			Calendar cal = Calendar.getInstance();
			cal.set(year, month, day, hour, minute);
			match.setMatchTime(cal);
		}			

		// return Object of type Match
		return match;
	}

	private static List<FootballTeam> getTableFromJsonResponse(String jsonResponse) {

		// create new List
		List<FootballTeam> table = new ArrayList<FootballTeam>();

		// Parse JSON Response		
		JSONArray jsonArray = new JSONArray(jsonResponse);
		for (int i=0; i<jsonArray.length(); i++) {
			// add match to list
			table.add(JsonToFootballTeam(jsonArray.getJSONObject(i)));					
		}
		return table;
	}


	private static FootballTeam JsonToFootballTeam(JSONObject jsonObject) {

		FootballTeam ft = new FootballTeam();

		// parse information from JSONObject
		// - Strings
		String teamName = jsonObject.getString("TeamName");
		String shortName = jsonObject.getString("ShortName");
		String iconUrl = jsonObject.getString("TeamIconUrl");		
		// - Integers
		int points = jsonObject.getInt("Points");
		int goals = jsonObject.getInt("Goals");
		int opponentGoals = jsonObject.getInt("OpponentGoals");
		int goalDiff = jsonObject.getInt("GoalDiff");
		int matches = jsonObject.getInt("Matches");
		int matchesWon = jsonObject.getInt("Won");
		int matchesLost = jsonObject.getInt("Lost");
		int matchesDraw = jsonObject.getInt("Draw");

		// add attributes to the object
		ft.setTeamName(teamName);
		ft.setShortName(shortName);
		ft.setIconUrl(iconUrl);
		ft.setPoints(points);
		ft.setGoals(goals);
		ft.setOpponentGoals(opponentGoals);
		ft.setGoalDiff(goalDiff);
		ft.setMatches(matches);
		ft.setWon(matchesWon);
		ft.setLost(matchesLost);
		ft.setDraw(matchesDraw);

		return ft;		
	}

	/**
	 * print all elements of a List containing objects of type Match
	 * @param matchesList: List<Match>
	 */
	public static void printMatches(List<Match> matchesList) {

		// loop over all entries and print them to the console
		for (int i=0; i<matchesList.size(); i++) {
			System.out.println(matchesList.get(i).toString());
		}
	}

	public static void printTable(List<FootballTeam> table) {

		// print header
		String sep = FootballTeam.STRING_SEPARATOR;
		String team = String.format("%" + FootballTeam.STRING_WIDTH_TEAMNAME + "s", "Teamname");
		String points = String.format("%" + FootballTeam.STRING_WIDTH_POINTS + "s", "Pkt");
		String goals = String.format("%" + FootballTeam.STRING_WIDTH_GOALS + "s", "Tor");
		System.out.println(team + sep + points + sep + goals);
		System.out.println("");
		
		// loop over all entries and print them to the console
		for (int i=0; i<table.size(); i++) {
			System.out.println(table.get(i).toString());
		}
	}
}
