package mfdevelopement.bundesliga;

import java.util.List;

public class BundesligaDemo {

	public static void main(String[] args) {

		Bundesliga bundesliga = new Bundesliga();

		// print Bundesliga table
		bundesliga.printTable(bundesliga.getTable());
		
		// print current Bundesliga matches
		bundesliga.printMatches(bundesliga.getMatches());
		
		// print current Bundesliga season
		System.out.println(bundesliga.getSeason());

		// print current Bundesliga game day
		System.out.println(bundesliga.getGameDay());

		// print FootballTeam at specific position, e.g. position 18
		FootballTeam lastFootballTeam = bundesliga.getTablePos(18);
		System.out.println(lastFootballTeam.toString());

		// print last update time for current game day
		System.out.println(bundesliga.getLastUpdateTimeCurrentGameDay());

		// print all goal getters of current Bundesliga season
		List<GoalGetter> goalGetters = bundesliga.getGoalGetters(); 
		for (GoalGetter gg: goalGetters) 
			System.out.println(gg.toString());
	}	
}