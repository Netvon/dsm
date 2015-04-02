package deslimstemens.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User 
{
	private StringProperty name;
	private BooleanProperty isAdmin;
	
	public User(String name, boolean isAdmin)
	{
		this.name = new SimpleStringProperty();
		this.isAdmin = new SimpleBooleanProperty();
		setName(name);
		setIsAdmin(isAdmin);
	}
	
	public String getName()
	{
		return name.get();
	}
	
	public void setName(String name)
	{
		this.name.set(name);
	}
	
	public boolean getIsAdmin()
	{
		return isAdmin.get();
	}
	
	public void setIsAdmin(boolean isAdmin)
	{
		this.isAdmin.set(isAdmin);
	}
	
	public StringProperty nameProperty()
	{
		return name;
	}
	
	public BooleanProperty isAdminProperty()
	{
		return isAdmin;
	}
}
