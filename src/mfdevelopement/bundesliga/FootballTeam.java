package mfdevelopement.bundesliga;

public class FootballTeam {
	
	// Strings
	private String teamName, shortName;
	private String iconUrl;
	
	public final static String STRING_SEPARATOR = " | ";
	
	// Integers
	public final static int VALUE_NOT_SET = OpenLigaDbParser.VALUE_NOT_SET;	
	public final static int STRING_WIDTH_TEAMNAME = 35;
	public final static int STRING_WIDTH_POINTS = 3;
	public final static int STRING_WIDTH_GOALS = 3;
	public final static int STRING_WIDTH_MATCHES = 2;
	public final static int STRING_WIDTH_MATCHES_WON = 2;
	public final static int STRING_WIDTH_MATCHES_DRAW = 2;
	public final static int STRING_WIDTH_MATCHES_LOST = 2;
	public final static int STRING_WIDTH_GOALS_DIFF = 3;
	
	private int points = VALUE_NOT_SET;
	private int goals = VALUE_NOT_SET, opponentGoals = VALUE_NOT_SET, matches = VALUE_NOT_SET, matchesWon = VALUE_NOT_SET;
	private int matchesLost = VALUE_NOT_SET, matchesDraw = VALUE_NOT_SET, goalDiff = VALUE_NOT_SET;
	
	
	public FootballTeam() {}
	
	public FootballTeam(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamName() {
		String name = "";
		if (this.teamName != null) {
			name = this.teamName;
		}
		return name;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getShortName() {
		String name = ""; 
		if (this.shortName != null) {
			name = this.shortName;
		}
		return name;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getIconUrl() {
		String url = "";
		if (this.iconUrl != null) {
			url = this.iconUrl;
		}
		return url;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public int getMatches() {
		return matches;
	}

	public void setMatches(int matches) {
		this.matches = matches;
	}

	public int getMatchesWon() {
		return matchesWon;
	}

	public void setMatchesWon(int won) {
		this.matchesWon = won;
	}

	public int getMatchesLost() {
		return matchesLost;
	}

	public void setMatchesLost(int lost) {
		this.matchesLost = lost;
	}

	public int getMatchesDraw() {
		return matchesDraw;
	}

	public void setMatchesDraw(int draw) {
		this.matchesDraw = draw;
	}

	public int getGoalDiff() {
		return goalDiff;
	}

	public void setGoalDiff(int goalDiff) {
		this.goalDiff = goalDiff;
	}

	public int getOpponentGoals() {
		return opponentGoals;
	}

	public void setOpponentGoals(int opponentGoals) {
		this.opponentGoals = opponentGoals;
	}

	
	public String toTableString() {
		if (this.points == -1) {
			return this.teamName;
		} else {
			String sep = STRING_SEPARATOR;
			String teamName = String.format("%-" + STRING_WIDTH_TEAMNAME + "s", this.teamName);
			String points = String.format("%" + STRING_WIDTH_POINTS + "d", this.points);
			String goals = String.format("%" + STRING_WIDTH_GOALS + "d", this.goals);
			String matches = String.format("%" + STRING_WIDTH_MATCHES + "s", this.matches);
			String matchesWon = String.format("%" + STRING_WIDTH_MATCHES_WON + "s", this.matchesWon);
			String matchesDraw = String.format("%" + STRING_WIDTH_MATCHES_DRAW + "s", this.matchesDraw);
			String matchesLost = String.format("%" + STRING_WIDTH_MATCHES_LOST + "s", this.matchesLost);
			String goalsDiff = String.format("%" + STRING_WIDTH_GOALS_DIFF + "s", this.goalDiff);
			return teamName
					+ sep + matches + sep + points + sep + goalsDiff
					+ sep + matchesWon + sep + matchesDraw + sep + matchesLost
					+ sep + goals; 
		}
	}
	
	@Override
	public String toString() {
		return "[" + this.teamName + "," + this.points + " points, " + 
				this.goals + ":" + this.opponentGoals + " goals," + this.goalDiff + " goals difference, " + 
				this.matches + " matches, " + this.matchesWon + " won, " + this.matchesDraw + " draw, " + this.matchesLost + " lost]";
	}
}
