package mfdevelopement.bundesliga;
import java.util.ArrayList;
import java.util.List;

public class Bundesliga {

	// Variables
	// - Lists
	private static List<Match> matches = new ArrayList<>();
	private static List<FootballTeam> bundesligaTable = new ArrayList<FootballTeam>();


	public static void main(String[] args) {
		
		OpenLigaDbParser openLigaDbParser = new OpenLigaDbParser();
		
		// get current Bundesliga matches
		matches = openLigaDbParser.getCurrentBundesligaMatches();		
		openLigaDbParser.printMatches(matches);
		
		// get table of Bundesliga
		bundesligaTable = openLigaDbParser.getBundesligaTable();
		openLigaDbParser.printTable(bundesligaTable);
	}	
}