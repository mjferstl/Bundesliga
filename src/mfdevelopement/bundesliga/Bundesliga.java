package mfdevelopement.bundesliga;

import java.util.ArrayList;
import java.util.List;

public class Bundesliga {

	private List<FootballTeam> table = new ArrayList<FootballTeam>();
	private List<Match> matches = new ArrayList<Match>();

	private OpenLigaDbParser openLigaDbParser = new OpenLigaDbParser();


	public Bundesliga() {
		this.table = getTable();
	}

	public List<FootballTeam> getTable() {
		// get table from OpenLigaDB, if no data is loaded yet
		if (this.table.size() == 0) {
			updateTable();
		}
		return this.table;
	}

	public void updateTable() {
		this.table = openLigaDbParser.getBundesligaTable();
	}

	public List<Match> getMatches() {
		// get data from OpenLigaDB, if nothing is loaded yet
		if (this.matches.size() == 0) {
			updateMatches();
		}

		return this.matches;
	}

	public void updateMatches() {
		this.matches = openLigaDbParser.getCurrentBundesligaMatches();
	}

	public void printTable() {
		openLigaDbParser.printTable(this.table);
	}

	public void printTable(List<FootballTeam> footballTeams) {
		openLigaDbParser.printTable(footballTeams);
	}

	public void printMatches() {
		openLigaDbParser.printMatches(this.matches);
	}

	public void printMatches(List<Match> matchesList) {
		openLigaDbParser.printMatches(matchesList);
	}

	public FootballTeam getTablePos(int position) throws IndexOutOfBoundsException{

		// check if there is a item at position
		if (position < 1 || position > table.size()) {
			throw new IndexOutOfBoundsException("Position " + position + " is out of range. The last team is at position " + table.size());
		}

		// return FootballTeam at position
		return table.get(position-1);
	}

}
