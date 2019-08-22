
public class FootballTeam {
	
	private String teamName, shortName;
	private String iconUrl;
	private int points = -1;
	private int goals, opponentGoals, matches, won, lost, draw, goalDiff;
	
	public final static int STRING_WIDTH_TEAMNAME = 35;
	public final static int STRING_WIDTH_POINTS = 3;
	public final static int STRING_WIDTH_GOALS = 3;
	public final static String STRING_SEPARATOR = " | ";
	
	public FootballTeam() {}
	
	public FootballTeam(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamName() {
		return teamName;
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

	public int getWon() {
		return won;
	}

	public void setWon(int won) {
		this.won = won;
	}

	public int getLost() {
		return lost;
	}

	public void setLost(int lost) {
		this.lost = lost;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
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
			String teamName = String.format("%" + STRING_WIDTH_TEAMNAME + "s", this.teamName);
			String points = String.format("%" + STRING_WIDTH_POINTS + "d", this.points);
			String goals = String.format("%" + STRING_WIDTH_GOALS + "d", this.goals);
			return teamName + sep + points + sep + goals;
		}
	}
}
