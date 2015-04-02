package deslimstemens.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import deslimstemens.view.ViewHelper;

public class LogController extends Controller implements Initializable 
{

	public LogController(ViewHelper vh) {
		super(vh);
		// TODO Auto-generated constructor stub
	}

	@FXML
	private ListView<String> lvMessages;
	

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		lvMessages.setItems(logger.getMessages());
	}

	@Override
	public void periodicUpdate() 
	{
		// TODO Auto-generated method stub

	}

}
