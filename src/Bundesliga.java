import java.util.ArrayList;
import java.util.List;

public class Bundesliga {

	// Variables
	// - Lists
	private static List<Match> matches = new ArrayList<>();
	private static List<FootballTeam> bundesligaTable = new ArrayList<FootballTeam>();


	public static void main(String[] args) {
		
		// get current Bundesliga matches
		matches = OpenLigaDbParser.getCurrentBundesligaMatches();		
		OpenLigaDbParser.printMatches(matches);
		
		// get table of Bundesliga
		bundesligaTable = OpenLigaDbParser.getBundesligaTable();
		OpenLigaDbParser.printTable(bundesligaTable);
	}	
}