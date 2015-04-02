package deslimstemens.controller;

import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import deslimstemens.model.ScoreUser;

public class RequestCell extends TableCell<ScoreUser, String> 
{
    final Button button;
    final Consumer<String> consumer;
     
    public RequestCell(String displayText, Consumer<String> consumer)
    {
    	button = new Button(displayText);
    	this.consumer = consumer;
    }
    
    @Override
    protected void updateItem(String item, boolean empty) 
    {
    	super.updateItem(item, empty);
    	
    	if(!empty)
    	{
            setGraphic(button);
            button.setOnAction(ae -> buttonClick(ae));
        }
    	else
    	{
    		setGraphic(null);
    	}
    }
    
    private void buttonClick(ActionEvent ae)
    {
    	consumer.accept(this.getItem());
    }
}
