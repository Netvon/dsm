package deslimstemens.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import deslimstemens.controller.Controller;
import deslimstemens.data.DataProvider;
import deslimstemens.data.Logger;
import deslimstemens.data.SettingsProvider;
import deslimstemens.exception.ShowViewException;

/**
 * The {@code ViewHelper} Class helps you with displaying different views.
 * It also contains ways to load custom parts of a View and
 * ways to display alert windows.
 * </p>
 * With this class you can;
 * <ul>
 * 	<li>{@link #showView(String) Show a View} on a new {@link Stage}
 *  <li>{@link #showView(String, Stage) Show a View} on a {@link Stage}
 *  <li>{@link #hideView(String) Hide a View}
 * 	<li>{@link #loadViewTo(String, Pane) Load a View} to a {@link Pane}
 * </ul>
 * 
 * {@code ViewHelper} also wraps some useful variables, like;
 * <ul>
 * 	<li>{@link #getDataProvider() A DataProvider}
 * 	<li>{@link #getSettingsProvider() A SettingsProvider}
 *  <li>{@link #getResourceProvider() A ResourceProvider}
 *  <li>{@link #getLogger() A Logger}
 * </ul>
 * 
 * You can display a new View by calling the following in a {@link Controller}
 * by using {@link #showView(String)}.
 * <pre>
 * {@code
 * viewHelper.showView("ViewName");
 * }
 * </pre>
 * If you want to call a method form the View's {@link Controller} you can
 * get a reference to the controller by doing the following:
 * <pre>
 * {@code
 * ViewController viewController = viewHelper.showView("ViewName");
 * viewController.myMethod();
 * }
 * </pre>
 * The type of the variable should always be the Class of the {@link Controller}
 * defined in the View's FXML. 
 *  
 * @author Tom van Nimwegen
 * @see SettingsProvider
 * @see Logger
 * @see DataProvider
 */
public class ViewHelper 
{
	private final SettingsProvider settingsProvider;
	private final DataProvider dataProvider;
	private final Logger logger;
	private final ResourceProvider resourceProvider;
	
	private Map<String, StageControllerPair<? extends Controller>> stageList;
	
	/**
	 * Returns an instance of a {@code SettingsProvider}.
	 * This instance can be used to get Settings for this application: 
	 * <blockquote><pre>
	 * SettingsProvider s = viewHelper.getSettingsProvider();
	 * String setting = s.get("test", "default value");
	 * </pre></blockquote>
	 * 
	 * @return Returns an instance of a {@code SettingsProvider}
	 * @see SettingsProvider
	 */
	public SettingsProvider getSettingsProvider() 
	{
		return settingsProvider;
	}

	public DataProvider getDataProvider() 
	{
		return dataProvider;
	}

	public Logger getLogger() 
	{
		return logger;
	}

	public ResourceProvider getResourceProvider() 
	{
		return resourceProvider;
	}
	
	public ViewHelper(	SettingsProvider settingsProvider, 
						DataProvider dataProvider,
						Logger logger,
						ResourceProvider resourceProvider) 
	{
		this.settingsProvider = settingsProvider;
		this.dataProvider = dataProvider;
		this.logger = logger;
		this.resourceProvider = resourceProvider;
		
		this.stageList = new HashMap<String, ViewHelper.StageControllerPair<? extends Controller>>();
	}
	
	public <T extends Controller> T showView(String name) throws ShowViewException
	{
		return showView(name, new Stage());
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Controller> T showView(String name, Stage s) throws ShowViewException
	{		
		try 
		{
			if(stageList.containsKey(name))
			{
				Stage storedStage = stageList.get(name).stage;
				storedStage.show();
				return (T) stageList.get(name).controller;
			}			
			
			FXMLLoader loader = resourceProvider.getLoader(name, settingsProvider, dataProvider, logger);
					
			Parent root = loader.load();
			Controller c = loader.getController();
			
			Scene scene = new Scene(root,400,400);
			s.setScene(scene);
			s.show();
			
			StageControllerPair<? extends Controller> scp = new StageControllerPair<>(c, s);
			
			stageList.put(name, scp);
			
			return (T) c;
		} 
		catch (IOException e) 
		{
			throw new ShowViewException();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Controller> T loadViewTo(String name, Pane container) throws IOException
	{
		FXMLLoader loader = resourceProvider.getLoader(name, settingsProvider, dataProvider, logger);		
		container.getChildren().add(loader.load());
		Controller c = loader.getController();
		
		return (T) c;
	}
	
	public void hideView(String name)
	{
		if(stageList.containsKey(name))
		{
			Stage storedStage = stageList.get(name).stage;
			storedStage.hide();
		}
	}
	
	public static Optional<ButtonType> showAlert(String header)
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Er is een fout opgetreden");
		alert.setContentText("Probeer het later nog een keer. Blijft het probleem zich aanhouden neem dat contact op met groep SO3b.");
		alert.setHeaderText(header);
		
		return alert.showAndWait();
	}
	
	public static Optional<ButtonType> showInfo(String content)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Informatie");
		alert.setHeaderText(null);
		alert.setContentText(content);

		return alert.showAndWait();
	}
	
	public boolean isViewVisible(String name)
	{
		if(stageList.containsKey(name))
		{
			Stage storedStage = stageList.get(name).stage;
			return storedStage.isShowing();
		}
		
		return false;
	}
	
	private class StageControllerPair<T extends Controller>
	{
		public T controller;
		public Stage stage;
		
		public StageControllerPair(T c, Stage s)
		{
			this.controller = c;
			this.stage = s;
		}
	}
}
