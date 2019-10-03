package mfdevelopement.bundesliga;

public class GoalGetter implements Comparable<GoalGetter>{
	
	private final static int VALUE_NOT_SET = OpenLigaDbParser.VALUE_NOT_SET;
	
	private String name = "";
	private int goals = VALUE_NOT_SET;
	
	public GoalGetter() {}
	
	public GoalGetter(String name, int goals) {
		this.name = name;
		this.goals = goals;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGoals() {
		return goals;
	}
	
	private Integer getGoalsInteger() {
		return Integer.valueOf(this.goals);
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	@Override
	public String toString() {
		return this.name + " scored " + this.goals + " goals";
	}

	@Override
	public int compareTo(GoalGetter gg) {
		if (getGoals() == VALUE_NOT_SET || gg.getGoals() == VALUE_NOT_SET) {
			return 0;
		}
		return getGoalsInteger().compareTo(gg.getGoalsInteger());
	}
}
