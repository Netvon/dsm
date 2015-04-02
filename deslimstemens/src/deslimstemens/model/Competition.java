package deslimstemens.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Competition 
{
	private IntegerProperty id;
	private ObservableList<ScoreUser> userList;
	private ObservableList<MatchRequest> requestList;
//	private ObservableMap<User, IntegerProperty> userMap;
	
	public Competition(int id)
	{
		this.id = new SimpleIntegerProperty(id);
		this.userList = FXCollections.observableArrayList();
		this.requestList = FXCollections.observableArrayList();
	}
	
	public IntegerProperty getID()
	{
		return id;
	}
	
	public void addUser(ScoreUser u)
	{
		this.userList.add(u);
	}
	
	public void addAllUsers(ScoreUser...users)
	{
		for (ScoreUser scoreUser : users)
		{
			addUser(scoreUser);
		}
	}
	
	public ObservableList<ScoreUser> getAllUsers()
	{
		return this.userList;
	}
	
	public void makeMatchRequest(ScoreUser sender, ScoreUser receiver)
	{
		requestList.add(new MatchRequest(sender, receiver));
	}
	

	@Override
	public String toString() {
		return "Competition #" + id.getValue().toString();
	}
}
