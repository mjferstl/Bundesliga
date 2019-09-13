package mfdevelopement.bundesliga;

public class BundesligaDemo {

	public static void main(String[] args) {

		Bundesliga bundesliga = new Bundesliga();
		bundesliga.printTable(bundesliga.getTable());
		bundesliga.printMatches(bundesliga.getMatches());

		FootballTeam lastFootballTeam = bundesliga.getTablePos(18);
		System.out.println(lastFootballTeam.toString());
	}	
}