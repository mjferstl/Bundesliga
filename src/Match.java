import java.util.Calendar;

public class Match {
	
	private FootballTeam homeTeam, awayTeam;
	//private String homeTeam, awayTeam;
	private int matchId;
	private String leagueName;
	private boolean isFinished;
	private Calendar matchTime;
	
	public Match() {
	}
	
	@Override
	public String toString() {
		return "Spiel: " + matchTimeToString() + " " + this.homeTeam.toString() + " vs. " + this.awayTeam.toString();
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

	public void setMatchTime(Calendar matchTime) {
		this.matchTime = matchTime;
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
