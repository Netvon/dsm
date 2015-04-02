package deslimstemens.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import deslimstemens.util.Setting;

public class SimpleSettings extends SettingsProvider
{
	
	public SimpleSettings(String location) {
		super(location);
	}

	@Override
	public void loadSettings()
	{
		createFolder();
		
		File f = new File(location);
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(f.toPath());
		} 
		catch (NoSuchFileException e) 
		{
			try {
				Files.createFile(f.toPath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		for (String string : lines) 
		{
			String[] kv = string.split("=");
			
			if(kv.length > 1)
			{
				String key = kv[0];
				String value = kv[1];
				
				add(key, value);
			}
		}
	}

	@Override
	public void saveSettings() 
	{
		createFolder();
		
		File f = new File(location);
		BufferedWriter bw;
		try 
		{
			bw = Files.newBufferedWriter(f.toPath());
		
			for (Entry<String, Setting<?>> setting : this._settings.entrySet()) 
			{
				String key = setting.getKey();
				String value = (String) setting.getValue().getValue();
				
				bw.write(key + "=" + value + "\n");
			}
			
			bw.close();
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
