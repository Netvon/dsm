package deslimstemens.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ScoreUser extends User 
{
	public ScoreUser(String name, boolean isAdmin) 
	{
		super(name, isAdmin);
		this.score = new SimpleIntegerProperty(0);
	}

	private IntegerProperty score;
	
	public int getScore()
	{
		return score.get();
	}
	
	public void addToScore(int value)
	{
		this.score.set(getScore() + value);
	}
	
	public IntegerProperty scoreProperty()
	{
		return score;
	}
}
