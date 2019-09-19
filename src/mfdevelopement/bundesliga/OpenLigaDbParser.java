package mfdevelopement.bundesliga;
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
	private final Pattern matchTimePattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})");
	private final Pattern leagueNamePattern = Pattern.compile(".*(\\d{4})/(\\d{4})");

	// - Strings
	private final String urlBundesliga1Matches = "https://www.openligadb.de/api/getmatchdata/bl1";
	private final String urlBundesliga1Table2019 = "https://www.openligadb.de/api/getbltable/bl1/";
	private String jsonResponseTable = "";
	private String jsonResponseMatches = "";

	public static final int VALUE_NOT_SET = -999;
	private int bundesligaSeason = 0;


	/**
	 * method to get all current Bundesliga matches from OpenLigaDB
	 * @return List containing objects of type match
	 */
	public List<Match> getCurrentBundesligaMatches() {
		// create new List
		List<Match> matches = new ArrayList<Match>();

		// get matches from OpenLigaDb response
		String jsonResponse = getOpenLigaResponse(urlBundesliga1Matches);
		this.jsonResponseMatches = jsonResponse;
		matches = getMatchesFromJsonResponse(jsonResponse);

		return matches;
	}

	public List<FootballTeam> getBundesligaTable() {
		// create new List
		List<FootballTeam> table = new ArrayList<FootballTeam>();

		if (bundesligaSeason == 0) {
			String jsonResponseCurrentMatchDay = getOpenLigaResponse(urlBundesliga1Matches);
			JSONArray jsonArray = new JSONArray(jsonResponseCurrentMatchDay);
			String leagueName = jsonArray.getJSONObject(0).getString("LeagueName");
			Matcher m = leagueNamePattern.matcher(leagueName); 
			if (m.matches()) {
				String season = m.group(1).trim();
				this.bundesligaSeason = Integer.valueOf(season);
			} else {
				System.out.print("\n** Error when parsing current season of Bundesliga **\n");
				return table;
			}
		}
		// get table from OpenLigaDb response
		String jsonResponse = getOpenLigaResponse(urlBundesliga1Table2019 + this.bundesligaSeason);
		this.jsonResponseTable = jsonResponse;
		table = getTableFromJsonResponse(jsonResponse);			

		return table;		
	}

	/**
	 * get the JSON response of OpenLigaDB as String
	 * @param url: String containing the url for the request
	 * @return: String containing the response in JSON format
	 */
	private String getOpenLigaResponse(String url) {		
		URL urlObject = getJsonResponse(url);		
		String response = readResponse(urlObject);		
		return response;
	}

	/**
	 * get the JSON response 
	 * @param urlString: String containing the url for the request
	 * @return: Object of type URL
	 */
	private URL getJsonResponse(String urlString) {

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
	private String readResponse(URL url) {

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
	public List<Match> getMatchesFromJsonResponse(String jsonResponse) {

		List<Match> m = new ArrayList<Match>();
		// Parse JSON Response		
		JSONArray jsonArray = new JSONArray(jsonResponse);
		for (int i=0; i<jsonArray.length(); i++) {
			// add match to list
			m.add(JsonToMatch(jsonArray.getJSONObject(i)));					
		}
		return m;
	}


	/**
	 * parse jsonObject containing a match to a Object of type Match
	 * @param jsonObject containing part of the JSON response from OpenLigaDB containing a match as JSONObject
	 * @return: Object of type Match
	 */
	private Match JsonToMatch(JSONObject jsonObject) {	

		Match match = new Match();
		String homeTeamName, homeTeamShortName, homeTeamIconUrl;
		String awayTeamName, awayTeamShortName, awayTeamIconUrl;
		int goalsHomeTeamFinal = VALUE_NOT_SET, goalsAwayTeamFinal = VALUE_NOT_SET;
		int goalsHomeTeamHalf = VALUE_NOT_SET, goalsAwayTeamHalf = VALUE_NOT_SET;

		// ---- get entries of the JSONObject
		// - home team
		JSONObject jsonObjectTeam1 = jsonObject.getJSONObject("Team1");
		if (jsonObjectTeam1 != null) {
			homeTeamName = getStringFromJSONObject(jsonObjectTeam1, "TeamName");	
			homeTeamShortName = getStringFromJSONObject(jsonObjectTeam1, "ShortName");				
			homeTeamIconUrl = getStringFromJSONObject(jsonObjectTeam1, "TeamIconUrl");
		} else {
			homeTeamName = "";
			homeTeamShortName = "";
			homeTeamIconUrl = "";
		}
		// home team
		FootballTeam homeTeam = new FootballTeam(homeTeamName);
		homeTeam.setIconUrl(homeTeamIconUrl);		
		homeTeam.setShortName(homeTeamShortName);


		// - away team
		JSONObject jsonObjectTeam2 = jsonObject.getJSONObject("Team2");
		if (jsonObjectTeam2 != null) {
			awayTeamName = getStringFromJSONObject(jsonObjectTeam2, "TeamName");			
			awayTeamShortName = getStringFromJSONObject(jsonObjectTeam2, "ShortName");	
			awayTeamIconUrl = getStringFromJSONObject(jsonObjectTeam2, "TeamIconUrl");
		} else {
			awayTeamName = ""; 
			awayTeamShortName = "";
			awayTeamIconUrl = "";
		}
		// away team
		FootballTeam awayTeam = new FootballTeam(awayTeamName);
		awayTeam.setIconUrl(awayTeamIconUrl);
		awayTeam.setShortName(awayTeamShortName);

		// add teams to match
		match.setHomeTeam(homeTeam);
		match.setAwayTeam(awayTeam);

		// - general 
		String matchDateTime = getStringFromJSONObject(jsonObject, "MatchDateTime");
		boolean matchIsFinished = getBooleanFromJSONObject(jsonObject,"MatchIsFinished");

		// Ergebnisse auslesen, sofern vorhanden
		JSONArray jsonArrayMatchResults = jsonObject.getJSONArray("MatchResults");
		if  (jsonArrayMatchResults != null && jsonArrayMatchResults.length() == 2) {
			// Endergebnis
			JSONObject jsonObjectFinalResult = jsonArrayMatchResults.getJSONObject(0);
			goalsHomeTeamFinal = getIntFromJSONObject(jsonObjectFinalResult,"PointsTeam1"); 
			goalsAwayTeamFinal = getIntFromJSONObject(jsonObjectFinalResult,"PointsTeam2"); 

			// Halbzeit
			JSONObject jsonObjectHalftime = jsonArrayMatchResults.getJSONObject(1);
			goalsHomeTeamHalf = getIntFromJSONObject(jsonObjectHalftime,"PointsTeam1"); 
			goalsAwayTeamHalf = getIntFromJSONObject(jsonObjectHalftime,"PointsTeam2");
		}

		// Tore auslesen, sofern vorhanden
		JSONArray jsonArrayMatchGoals = jsonObject.getJSONArray("Goals");

		// declare variables
		int scoreHomeTeam = 0, scoreAwayTeam, matchMinute;
		String goalGetterName;
		boolean isPenalty, isOwnGoal, isOverTime;

		if (jsonArrayMatchGoals != null) {

			for (int i=0; i<jsonArrayMatchGoals.length(); i++) {
				JSONObject jsonObjectGoal = jsonArrayMatchGoals.getJSONObject(i);
				scoreHomeTeam = getIntFromJSONObject(jsonObjectGoal, "ScoreTeam1");
				scoreAwayTeam = getIntFromJSONObject(jsonObjectGoal, "ScoreTeam2");
				matchMinute = getIntFromJSONObject(jsonObjectGoal, "MatchMinute");
				goalGetterName = getStringFromJSONObject(jsonObjectGoal, "GoalGetterName");
				isPenalty = getBooleanFromJSONObject(jsonObjectGoal, "IsPenalty");
				isOwnGoal = getBooleanFromJSONObject(jsonObjectGoal, "IsOwnGoal");
				isOverTime = getBooleanFromJSONObject(jsonObjectGoal, "IsOvertime");

				match.addMatchGoal(scoreHomeTeam, scoreAwayTeam, goalGetterName,matchMinute, isPenalty, isOwnGoal, isOverTime);
			}
		}

		// add results to match
		// - final
		if (goalsHomeTeamFinal >= 0) 
			match.setGoalsHomeTeamFinal(goalsHomeTeamFinal);
		if (goalsAwayTeamFinal >= 0)
			match.setGoalsAwayTeamFinal(goalsAwayTeamFinal);
		// - half time
		if (goalsHomeTeamHalf >= 0)
			match.setGoalsHomeTeamHalf(goalsHomeTeamHalf);
		if (goalsAwayTeamHalf >= 0)
			match.setGoalsAwayTeamHalf(goalsAwayTeamHalf);		



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


	private String getStringFromJSONObject(JSONObject jsonObject, String key) {

		String value;

		// check if jsonObject is not NULL
		if (jsonObject == null) {
			value = "";
			return value;
		}

		try {
			value = jsonObject.getString(key);
		} catch (Exception e) {
			value = "";
			System.out.println("* Fehler: Der Key " + key + " ist nicht vohanden");
		}

		return value;
	}

	private int getIntFromJSONObject(JSONObject jsonObject, String key) {

		int value;

		// check if jsonObject is not NULL
		if (jsonObject == null) {
			value = VALUE_NOT_SET;
			return value;
		}

		try {
			value = jsonObject.getInt(key);
		} catch (Exception e) {
			value = VALUE_NOT_SET;
			System.out.println("* Fehler: Der Key " + key + " ist nicht vohanden");
		}

		return value;
	}

	private boolean getBooleanFromJSONObject(JSONObject jsonObject, String key) {

		boolean boo = false;

		// check if jsonObject is not NULL
		if (jsonObject == null) {
			boo = false;
			return boo;
		}

		try {
			boo = jsonObject.getBoolean(key);
		} catch (Exception e) {
			boo = false;
			System.out.println("* Fehler: Der Key " + key + " ist nicht vohanden");
		}

		return boo;
	}


	public List<FootballTeam> getTableFromJsonResponse(String jsonResponse) {

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


	private FootballTeam JsonToFootballTeam(JSONObject jsonObject) {

		FootballTeam ft = new FootballTeam();

		// parse information from JSONObject
		// - Strings
		String teamName = getStringFromJSONObject(jsonObject, "TeamName");
		String shortName = getStringFromJSONObject(jsonObject, "ShortName");
		String iconUrl = getStringFromJSONObject(jsonObject, "TeamIconUrl");		
		// - Integers
		int points = getIntFromJSONObject(jsonObject, "Points");
		int goals = getIntFromJSONObject(jsonObject, "Goals");
		int opponentGoals = getIntFromJSONObject(jsonObject, "OpponentGoals");
		int goalDiff = getIntFromJSONObject(jsonObject, "GoalDiff");
		int matches = getIntFromJSONObject(jsonObject, "Matches");
		int matchesWon = getIntFromJSONObject(jsonObject, "Won");
		int matchesLost = getIntFromJSONObject(jsonObject, "Lost");
		int matchesDraw = getIntFromJSONObject(jsonObject, "Draw");

		// add attributes to the object
		ft.setTeamName(teamName);
		ft.setShortName(shortName);
		ft.setIconUrl(iconUrl);
		ft.setPoints(points);
		ft.setGoals(goals);
		ft.setOpponentGoals(opponentGoals);
		ft.setGoalDiff(goalDiff);
		ft.setMatches(matches);
		ft.setMatchesWon(matchesWon);
		ft.setMatchesLost(matchesLost);
		ft.setMatchesDraw(matchesDraw);

		return ft;		
	}

	/**
	 * print all elements of a List containing objects of type Match
	 * @param matchesList: List<Match>
	 */
	public void printMatches(List<Match> matchesList) {

		// loop over all entries and print them to the console
		for (int i=0; i<matchesList.size(); i++) {
			System.out.println(matchesList.get(i).toString());
		}
	}

	/**
	 * print the loaded Bundesliga table to the console
	 * @param table: List of objects FootballTeam
	 */
	public void printTable(List<FootballTeam> table) {

		final int STRING_WIDTH_RANK = 3;

		// print header
		String sep = FootballTeam.STRING_SEPARATOR;
		String rank = String.format("%" + STRING_WIDTH_RANK + "s", " ");
		String team = String.format("%-" + FootballTeam.STRING_WIDTH_TEAMNAME + "s", "Mannschaft");
		String matches = String.format("%-" + FootballTeam.STRING_WIDTH_MATCHES + "s", "Sp");
		String points = String.format("%" + FootballTeam.STRING_WIDTH_POINTS + "s", "Pkt");
		String goals = String.format("%" + FootballTeam.STRING_WIDTH_GOALS + "s", "Tor");
		String matchesWon = String.format("%" + FootballTeam.STRING_WIDTH_MATCHES_WON + "s", "S");
		String matchesDraw = String.format("%" + FootballTeam.STRING_WIDTH_MATCHES_DRAW + "s", "U");
		String matchesLost = String.format("%" + FootballTeam.STRING_WIDTH_MATCHES_LOST + "s", "N");
		String goalsDiff = String.format("%" + FootballTeam.STRING_WIDTH_GOALS_DIFF + "s", "TD");

		// add empty line at top
		System.out.print("\n");

		// print header and separator
		System.out.println(rank + sep + team + sep + matches + sep + points + sep + goalsDiff
				+ sep + matchesWon + sep + matchesDraw + sep + matchesLost + sep + goals);
		System.out.println("-".repeat(STRING_WIDTH_RANK) 
				+ sep + "-".repeat(FootballTeam.STRING_WIDTH_TEAMNAME) 
				+ sep + "-".repeat(FootballTeam.STRING_WIDTH_MATCHES)
				+ sep + "-".repeat(FootballTeam.STRING_WIDTH_POINTS)
				+ sep + "-".repeat(FootballTeam.STRING_WIDTH_GOALS_DIFF)				
				+ sep + "-".repeat(FootballTeam.STRING_WIDTH_MATCHES_WON)
				+ sep + "-".repeat(FootballTeam.STRING_WIDTH_MATCHES_DRAW)
				+ sep + "-".repeat(FootballTeam.STRING_WIDTH_MATCHES_LOST)
				+ sep + "-".repeat(FootballTeam.STRING_WIDTH_GOALS));

		// loop over all entries and print them to the console
		for (int i=0; i<table.size(); i++) {
			System.out.print(String.format("%" + STRING_WIDTH_RANK + "s", i+1));
			System.out.print(sep);
			System.out.print(table.get(i).toString());
			System.out.print("\n");
		}

		// add empty line at bottom
		System.out.print("\n");
	}
	
	/**
	 * get JSON response for Bundesliga table as String
	 * @return String containing the JSON response for the current Bundesliga table
	 */
	public String getJsonResponseTable() {
		
		// if no response is loaded, get it from the internet
		if (this.jsonResponseTable == null || this.jsonResponseTable == "") {
			getBundesligaTable();
		}
		
		return this.jsonResponseTable;
	}
	
	/**
	 * get JSON response for current Bundesliga matches as String
	 * @return String containing the JSON response for the current Bundesliga matches
	 */
	public String getJsonResponseMatches() {
		
		// if no response is loaded, get it from the internet
		if (this.jsonResponseMatches == null || this.jsonResponseMatches == "") {
			getCurrentBundesligaMatches();
		}
		
		return this.jsonResponseMatches;
	}
}
