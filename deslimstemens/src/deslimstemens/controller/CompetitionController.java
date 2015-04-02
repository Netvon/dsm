package deslimstemens.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import deslimstemens.model.Competition;
import deslimstemens.model.ScoreUser;
import deslimstemens.view.ViewHelper;

public class CompetitionController extends Controller implements Initializable
{	

	public CompetitionController(ViewHelper vh) {
		super(vh);
		// TODO Auto-generated constructor stub
	}

	@FXML private TableView<ScoreUser> tblvUsers;
	@FXML private TableColumn<ScoreUser, String> tblvcUsername;
	@FXML private TableColumn<ScoreUser, Integer> tblvcScore;
	@FXML private TableColumn<ScoreUser, String> tblvcRequest;
	@FXML private TextField tfSearch;
	
	private ObservableList<ScoreUser> users;
	private ObservableList<ScoreUser> usersFiltered;
	
	private Competition competition;
	@FXML private Button btnCancelSearch;
	
	@FXML ResourceBundle resources;



	@FXML public void tfSearch_onKeyReleased() 
	{		
		if(!tfSearch.getText().trim().isEmpty())
		{
			btnCancelSearch.setVisible(true);
			usersFiltered = users.filtered(u -> u.getName().toLowerCase().contains(tfSearch.getText().trim().toLowerCase()));
			tblvUsers.setItems(usersFiltered);
		}
		else
		{
			btnCancelSearch.setVisible(false);
			tblvUsers.setItems(users);
		}
	}	
	
	public void requestButton_onAction(String name)
	{
		logger.putMessage("Uitnodiging naar " + name + " verstuurd");
		ViewHelper.showInfo("Uitnodiging naar " + name + " verstuurd");
		
//		Alert alert = new Alert(AlertType.INFORMATION);
//		alert.setTitle("Stuur uitnodiging");
//		alert.setHeaderText(null);
//		alert.setContentText("Uitnodiging naar " + name + " verstuurd");
		
		

//		alert.showAndWait();
	}
	
	public void setCompetition(Competition comp)
	{
		competition = comp;		
		users = competition.getAllUsers();		
		tblvUsers.setItems(users);
	}

	@FXML public void btnCancelSearch_onAction() 
	{
		tfSearch.setText("");
		tfSearch_onKeyReleased();
	}

	@Override
	public void periodicUpdate() 
	{
//		competition.addUser(new ScoreUser("a", false));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		tblvcUsername.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		tblvcScore.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());
		tblvcRequest.setCellFactory(column -> new RequestCell(resources.getString("requestCell_text"), ae -> requestButton_onAction(ae)));
		tblvcRequest.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
				
		tblvUsers.setPlaceholder(new Label("Geen gebruikers gevonden."));
		
	}
	
}
