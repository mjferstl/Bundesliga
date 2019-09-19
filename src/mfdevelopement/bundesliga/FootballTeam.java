package mfdevelopement.bundesliga;

public class FootballTeam {
	
	private String teamName, shortName;
	private String iconUrl;
	private int points = -1;
	private int goals, opponentGoals, matches, matchesWon, matchesLost, matchesDraw, goalDiff;
	
	public final static int STRING_WIDTH_TEAMNAME = 35;
	public final static int STRING_WIDTH_POINTS = 3;
	public final static int STRING_WIDTH_GOALS = 3;
	public final static int STRING_WIDTH_MATCHES = 2;
	public final static int STRING_WIDTH_MATCHES_WON = 2;
	public final static int STRING_WIDTH_MATCHES_DRAW = 2;
	public final static int STRING_WIDTH_MATCHES_LOST = 2;
	public final static int STRING_WIDTH_GOALS_DIFF = 3;
	public final static String STRING_SEPARATOR = " | ";
	
	public FootballTeam() {}
	
	public FootballTeam(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamName() {
		String name = "";
		if (teamName != null) {
			name = this.teamName;
		}
		return name;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getIconUrl() {
		return iconUrl;
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

	@Override
	public String toString() {
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
}
