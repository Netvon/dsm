package deslimstemens.data;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;

public abstract class Logger 
{
	private ObservableList<String> messages;
	
	public Logger()
	{
		this.messages = FXCollections.observableArrayList();
		this.messages.addListener(new ListChangeListener<String>()
		{
			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends String> c) 
			{
				messages_onChanged(c);
			}
		});
	}
	
	protected abstract void messages_onChanged(Change<? extends String> c);
	
	public void putMessage(String message)
	{
		messages.add(message);
	}

	public ObservableList<String> getMessages() 
	{
		return messages;
	}
}
