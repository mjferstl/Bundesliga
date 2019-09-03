package mfdevelopement.bundesliga;

public class MatchGoal {
	
	private int scoreHomeTeam, scoreAwayTeam, matchMinute;
	private String goalGetterName;
	private boolean isPenalty, isOwnGoal, isOverTime;
	
	public MatchGoal(int goalsHomeTeam, int goalsAwayTeam, String goalGetterName) {
		setScoreHomeTeam(goalsHomeTeam);
		setScoreAwayTeam(goalsAwayTeam);
		setGoalGetterName(goalGetterName);
	}
	
	public MatchGoal(int goalsHomeTeam, int goalsAwayTeam, String goalGetterName, int matchMinute, boolean isPenalty, boolean isOwnGoal, boolean isOverTime) {
		setScoreHomeTeam(goalsHomeTeam);
		setScoreAwayTeam(goalsAwayTeam);
		setGoalGetterName(goalGetterName);
		setMatchMinute(matchMinute);
		setPenalty(isPenalty);
		setOwnGoal(isOwnGoal);
		setOverTime(isOverTime);
	}

	public int getScoreHomeTeam() {
		return scoreHomeTeam;
	}

	public void setScoreHomeTeam(int goalsHomeTeam) {
		if (goalsHomeTeam >= 0)
			this.scoreHomeTeam = goalsHomeTeam;
	}

	public int getScoreAwayTeam() {
		return scoreAwayTeam;
	}

	public void setScoreAwayTeam(int goalsAwayTeam) {
		if (goalsAwayTeam >= 0)
			this.scoreAwayTeam = goalsAwayTeam;
	}

	public String getGoalGetterName() {
		return goalGetterName;
	}

	public void setGoalGetterName(String goalGetterName) {
		this.goalGetterName = goalGetterName;
	}

	public boolean isPenalty() {
		return isPenalty;
	}

	public void setPenalty(boolean isPenalty) {
		this.isPenalty = isPenalty;
	}

	public boolean isOwnGoal() {
		return isOwnGoal;
	}

	public void setOwnGoal(boolean isOwnGoal) {
		this.isOwnGoal = isOwnGoal;
	}

	public boolean isOverTime() {
		return isOverTime;
	}

	public void setOverTime(boolean isOverTime) {
		this.isOverTime = isOverTime;
	}

	public int getMatchMinute() {
		return matchMinute;
	}

	public void setMatchMinute(int matchMinute) {
		this.matchMinute = matchMinute;
	}
}
