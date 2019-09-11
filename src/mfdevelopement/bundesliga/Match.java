package mfdevelopement.bundesliga;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Match {
	
	private FootballTeam homeTeam, awayTeam;
	//private String homeTeam, awayTeam;
	private int matchId;
	private int goalsHomeTeamFinal = OpenLigaDbParser.VALUE_NOT_SET, goalsAwayTeamFinal = OpenLigaDbParser.VALUE_NOT_SET;
	private int goalsHomeTeamHalf = OpenLigaDbParser.VALUE_NOT_SET, goalsAwayTeamHalf = OpenLigaDbParser.VALUE_NOT_SET;
	private List<MatchGoal> matchGoals = new ArrayList<MatchGoal>();
	private String leagueName;
	private boolean isFinished;
	private Calendar matchTime;
	
	public Match() {
		// nothing defined yet
	}
	
	@Override
	public String toString() {
		String encounterString = String.format("%-40s", this.homeTeam.toString() + " vs. " + this.awayTeam.toString());
		
		String resultString;
		if ((this.goalsHomeTeamFinal != OpenLigaDbParser.VALUE_NOT_SET) && (this.goalsAwayTeamFinal != OpenLigaDbParser.VALUE_NOT_SET))
			resultString = String.format("%5s", String.format("%2s", this.goalsHomeTeamFinal) + "-" + this.goalsAwayTeamFinal);
		else {
			resultString = "";
		}
		String goalsString = "";
		for (int i=0; i<matchGoals.size(); i++) {
			MatchGoal matchGoal = matchGoals.get(i);
			goalsString += matchGoal.getScoreHomeTeam() + "-" + matchGoal.getScoreAwayTeam() + " " + matchGoal.getGoalGetterName();
			
			if (matchGoal.isPenalty())
				goalsString += "(EM)";
			
			if (matchGoal.isOwnGoal())
				goalsString += "(ET)";
			
			goalsString += " " + matchGoal.getMatchMinute() + ".";
			
			if (i<matchGoals.size()-1)
				goalsString += ", ";
 		}
		
		return "Spiel: " + matchTimeToString() + " " + encounterString + "\t" + resultString + "\t" + goalsString;
	}

	public FootballTeam getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(FootballTeam homeTeam) {
		this.homeTeam = homeTeam;
	}

	public FootballTeam getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(FootballTeam awayTeam) {
		this.awayTeam = awayTeam;
	}

	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public Calendar getMatchTime() {
		return matchTime;
	}

	public int getGoalsHomeTeamFinal() {
		return goalsHomeTeamFinal;
	}

	public void setGoalsHomeTeamFinal(int goalsHomeTeam) {
		if (goalsHomeTeam >= 0) 
			this.goalsHomeTeamFinal = goalsHomeTeam;
	}

	public int getGoalsAwayTeamFinal() {
		return goalsAwayTeamFinal;
	}

	public void setGoalsAwayTeamFinal(int goalsAwayTeam) {
		if (goalsAwayTeam >= 0) 
			this.goalsAwayTeamFinal = goalsAwayTeam;
	}

	public int getGoalsHomeTeamHalf() {
		return goalsHomeTeamHalf;
	}

	public void setGoalsHomeTeamHalf(int goalsHomeTeamHalf) {
		if (goalsHomeTeamHalf >= 0)
			this.goalsHomeTeamHalf = goalsHomeTeamHalf;
	}

	public int getGoalsAwayTeamHalf() {
		return goalsAwayTeamHalf;
	}

	public void setGoalsAwayTeamHalf(int goalsAwayTeamHalf) {
		if (goalsAwayTeamHalf >= 0)
			this.goalsAwayTeamHalf = goalsAwayTeamHalf;
	}

	public void setMatchTime(Calendar matchTime) {
		this.matchTime = matchTime;
	}
	
	public void addMatchGoal(int goalsHomeTeam, int goalsAwayTeam, String goalGetterName, int matchMinute) {
		matchGoals.add(new MatchGoal(goalsHomeTeam, goalsAwayTeam, goalGetterName, matchMinute, false, false, false));
	}
	
	public void addMatchGoal(int goalsHomeTeam, int goalsAwayTeam, String goalGetterName, int matchMinute, boolean isPenalty, boolean isOwnGoal, boolean isOverTime) {
		matchGoals.add(new MatchGoal(goalsHomeTeam, goalsAwayTeam, goalGetterName, matchMinute, isPenalty, isOwnGoal, isOverTime));
	}
	
	/**
	 * takes the matchTime of the current object and returns date and time formatted
	 * @return String containing the formatted date and time
	 */
	private String matchTimeToString() {
		
		// get year, month, day of month, hour and minute from the calendar object
		int year = this.matchTime.get(Calendar.YEAR);
		int month = this.matchTime.get(Calendar.MONTH); 
		int day = this.matchTime.get(Calendar.DAY_OF_MONTH);
		int hour = this.matchTime.get(Calendar.HOUR_OF_DAY);
		int minute = this.matchTime.get(Calendar.MINUTE);
		
		// formatting
		String yearFormat = String.format("%04d", year);
		String monthFormat = String.format("%02d", month);
		String dayFormat = String.format("%02d", day);
		String hourFormat = String.format("%02d", hour);
		String minuteFormat = String.format("%02d", minute);
		
		return yearFormat + "-" + monthFormat + "-" + dayFormat + " " + hourFormat + ":" + minuteFormat;
	}
}
