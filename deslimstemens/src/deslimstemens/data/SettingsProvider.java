package deslimstemens.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import deslimstemens.util.Setting;

public abstract class SettingsProvider 
{
	protected final static String DATA_FOLDER = "deslimstemens_data/";
	
	protected Map<String, Setting<?>> _settings;
	
	protected String location;
	
	public SettingsProvider(String location) 
	{
		this.location = DATA_FOLDER + location;
		_settings = new HashMap<String, Setting<?>>();
	}
	
	public <T> void add(String name, T value)
	{
		_settings.put(name, new Setting<>(name, value));
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String name, T defaultValue)
	{
		if(!_settings.containsKey(name))
		{
			add(name, defaultValue);
			return defaultValue;
		}
		
		return (T)_settings.get(name).getValue();
	}
	
	public <T> void set(String name, T value)
	{
		Setting<T> s = new Setting<>(name, value);
		_settings.replace(name, s);
	}
	
	public abstract void loadSettings();
	
	public abstract	void saveSettings();
	
	protected void createFolder()
	{
		File f = new File(DATA_FOLDER);
		if(!Files.exists(f.toPath()))
		{
			try 
			{
				Files.createDirectory(f.toPath());
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
