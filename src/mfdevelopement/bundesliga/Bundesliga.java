package mfdevelopement.bundesliga;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bundesliga {

	private List<FootballTeam> table = new ArrayList<FootballTeam>();
	private List<Match> matches = new ArrayList<Match>();
	private List<GoalGetter> goalGetters = new ArrayList<GoalGetter>();

	private OpenLigaDbParser openLigaDbParser = new OpenLigaDbParser();
	
	public final static int VALUE_NOT_SET = OpenLigaDbParser.VALUE_NOT_SET;

	private int gameDay = VALUE_NOT_SET;
	private int season = VALUE_NOT_SET;


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
		setSeason(openLigaDbParser.getCurrentSeason());
	}

	public List<Match> getMatches() {
		// get data from OpenLigaDB, if nothing is loaded yet
		if (this.matches.size() == 0) {
			updateMatches();
		}

		return this.matches;
	}

	public List<GoalGetter> getGoalGetters() {
		// get data from OpenLigaDB, if nothing is loaded yet
		if (this.goalGetters.size() == 0) {
			updateGoalGetters();
		}
		return this.goalGetters;
	}
	
	public int getGameDay() {
		this.gameDay = openLigaDbParser.getBundesligaGameDay();
		return this.gameDay;
	}
	
	public Date getLastUpdateTimeCurrentGameDay() {
		return openLigaDbParser.getUpdateTimeCurrentGameDay();
	}

	public void updateMatches() {
		this.matches = openLigaDbParser.getCurrentBundesligaMatches();
	}

	public void updateGoalGetters() {
		this.goalGetters = openLigaDbParser.getBundesligaGoalGetters();
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

	/**
	 * get the FootballTeam at a specific position of the Bundesliga table
	 * @param position: position of the desired FootballTeam; starts at 1
	 * @return Object of type FootballTeam
	 * @throws IndexOutOfBoundsException: if the desired position is out of range of the table
	 */
	public FootballTeam getTablePos(int position) throws IndexOutOfBoundsException{

		// check if there is a item at position
		if (position < 1 || position > table.size()) {
			throw new IndexOutOfBoundsException("Position " + position + " is out of range. The last team is at position " + table.size());
		}

		// return FootballTeam at position
		return table.get(position-1);
	}

	/**
	 * get the number of items in the loaded Bundesliga table
	 * @return number of items in the Bundesliga table
	 */
	public int getTableSize() {
		return table.size();
	}

	/**
	 * get the OpenLigaDbParser object for using it's functions trough the Bundesliga object
	 * @return OpenLigaDbParser object
	 */
	public OpenLigaDbParser getOpenLigaDbParser() {
		return this.openLigaDbParser;
	}

	private void setSeason(int season) {
		this.season = season;
	}

	public int getSeason() {
		if (this.season == VALUE_NOT_SET) {
			updateTable();
		}
		return this.season;
	}
	
	
}