package deslimstemens.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import deslimstemens.exception.ShowViewException;
import deslimstemens.model.Competition;
import deslimstemens.util.DefaultSettings;
import deslimstemens.view.ViewHelper;

public class GameController extends Controller implements Initializable
{
	public GameController(ViewHelper vh) 
	{
		super(vh);
	}

	@FXML Label lblSetting;
	@FXML Pane pnCompetition;
	private CompetitionController compcontroller;
	@FXML ChoiceBox<Competition> cbCompetition;
	@FXML ResourceBundle resources;
	@FXML MenuItem miClose;
	@FXML MenuItem miShowLog;


	private void cbCompetition_onSelectionChange() 
	{
		compcontroller.setCompetition(cbCompetition.getSelectionModel().selectedItemProperty().getValue());
	}

	@Override
	public void periodicUpdate() 
	{
		System.out.println(Thread.currentThread().getName());
		compcontroller.periodicUpdate();
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		cbCompetition.getItems().addAll(dataProvider.getAllCompetitions());
		cbCompetition.getSelectionModel().selectedItemProperty().addListener(ssmodel -> cbCompetition_onSelectionChange());

		try 
		{		
			compcontroller = viewHelper.loadViewTo("CompetitionView", pnCompetition);
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		cbCompetition.getSelectionModel().selectFirst();
	}

	@FXML public void miShowLog_onAction() 
	{
		if(viewHelper.isViewVisible("LogView"))
		{
			viewHelper.hideView("LogView");
		}
		else
		{
			try {
				Stage s = new Stage();
				s.setTitle("Log");
				s.getIcons().add(
								new Image(viewHelper.getResourceProvider()
													.getResource(
															viewHelper.getSettingsProvider()
																	  .get("logoLocation", DefaultSettings.LOGOLOCATION)).toString()));
				
				viewHelper.showView("LogView", s);
			} catch (ShowViewException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

	@FXML public void miClose_onAction() 
	{
		System.exit(0);
	}
}
