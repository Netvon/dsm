package deslimstemens;
import java.util.Locale;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import deslimstemens.data.DataProvider;
import deslimstemens.data.Logger;
import deslimstemens.data.SettingsProvider;
import deslimstemens.exception.ResolveException;
import deslimstemens.exception.ShowViewException;
import deslimstemens.util.Async;
import deslimstemens.util.DefaultSettings;
import deslimstemens.util.Resolver;
import deslimstemens.view.ResourceProvider;
import deslimstemens.view.ViewHelper;

public class App extends Application 
{
	private SettingsProvider settings;
	private ViewHelper vh;
	
	@Override
	public void start(Stage primaryStage) 
	{

		Resolver resolver = new Resolver("factory");
		resolver.load();
		
		try
		{
			settings = resolver.resolve(SettingsProvider.class, "settings");
			settings.loadSettings();			
			
			String rpLocation 	= settings.get("rpLocation", DefaultSettings.RPLOCATION);
			String rpLocale 	= settings.get("rpLocale", DefaultSettings.RPLOCALE);
			String startView	= settings.get("startView", DefaultSettings.STARTVIEW);
			String windowTitle 	= settings.get("windowTitle", DefaultSettings.WINDOWTITLE);
			
			ResourceProvider rp = new ResourceProvider(rpLocation, new Locale(rpLocale));
	
			vh = new ViewHelper(	settings, 
									resolver.resolve(DataProvider.class), 
									resolver.resolve(Logger.class), 
									rp);
			
			primaryStage.getIcons().add(new Image(rp.getResource(settings.get("logoLocation", DefaultSettings.LOGOLOCATION)).toString()));
			primaryStage.setTitle(windowTitle);
			vh.showView(startView, primaryStage);
		}
		catch (ResolveException re)
		{
			ViewHelper.showAlert("Het resolven van " + re.getRequestedClass().getName() + " is mislukt.");
		}
		catch (ShowViewException re)
		{
			ViewHelper.showAlert("Het weergeven van een scherm in de applicatie is mislukt");
		}
		
		
	}
	
	@Override
	public void stop() throws Exception 
	{		
		settings.saveSettings();
		Async.stopAll();
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
