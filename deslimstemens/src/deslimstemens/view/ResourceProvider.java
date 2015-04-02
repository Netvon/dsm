package deslimstemens.view;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import deslimstemens.controller.Controller;
import deslimstemens.data.DataProvider;
import deslimstemens.data.Logger;
import deslimstemens.data.SettingsProvider;

public class ResourceProvider 
{
	private static final String FXML_FILE_SUFFIX = ".fxml";
	private static final String CSS_FILE_SUFFIX = ".css";
	private static final String FXML_PACKAGE_PREFIX = "fxml/";
	private static final String CSS_PACKAGE_PREFIX = "css/";
	
	private ResourceBundle resourceBundle;
	
	public ResourceProvider(String resourceBundleLocation, Locale locale) 
	{
		setResourceBundle(resourceBundleLocation, locale);
	}
	
	public ResourceProvider(String resourceBundleLocation) 
	{
		this(resourceBundleLocation, Locale.getDefault());
	}
	
	public void setResourceBundle(String location, Locale locale)
	{
		resourceBundle = ResourceBundle.getBundle(location, locale);
	}
	
	public void setResourceBundle(String location)
	{
		setResourceBundle(location, Locale.getDefault());
	}
	
	public URL getResource(String name)
	{
		return this.getClass().getResource(name);
	}
	
	@SuppressWarnings("unchecked")
	public FXMLLoader getLoader(String viewName, SettingsProvider settingsProvider, DataProvider dataProvider, Logger logger) throws IOException
	{
		if(!viewName.endsWith(FXML_FILE_SUFFIX))
			viewName += FXML_FILE_SUFFIX;
		
		FXMLLoader loader;		
		
		if(resourceBundle == null)
			loader = new FXMLLoader(getResource(FXML_PACKAGE_PREFIX + viewName));
		else
			loader = new FXMLLoader(getResource(FXML_PACKAGE_PREFIX + viewName), resourceBundle);
		
		loader.setControllerFactory(x -> 
		{
			Class<? extends Controller> clazz = (Class<? extends Controller>) x;
			
			Constructor<?>[] ctors = clazz.getDeclaredConstructors();
			
			ArrayList<Object> args = new ArrayList<Object>();
			args.add(new ViewHelper(settingsProvider, dataProvider, logger, this));
//			args.add(dataProvider);
//			args.add(logger);
//			args.add(this);
			
			try {
				return (Controller) ctors[0].newInstance(args.toArray());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		});
		
		return loader;
	}
	
	public <T> T getFXML(String viewName , SettingsProvider settingsProvider, DataProvider dataProvider, Logger logger) throws IOException
	{
		FXMLLoader loader = getLoader(viewName, settingsProvider, dataProvider, logger);
		return loader.load();
	}
	
	public String getCSS(String viewName) throws IOException
	{
		if(!viewName.endsWith(CSS_FILE_SUFFIX))
			viewName += CSS_FILE_SUFFIX;
		
		return getResource(CSS_PACKAGE_PREFIX + viewName).toExternalForm();
	}
}
