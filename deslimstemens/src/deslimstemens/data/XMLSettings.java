package deslimstemens.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import deslimstemens.util.Setting;

@XmlRootElement(name = "settings")
public class XMLSettings extends SettingsProvider
{
	
	public XMLSettings(String location) 
	{
		super(location);
		_settings = new HashMap<String, Setting<?>>();
	}

	@XmlElement(name = "setting")
	public Map<String, Setting<?>> getSettings()
	{		
		return _settings;
	}
	
	public void saveSettings()
	{
		File file = new File(location + ".xml");
		try {
			JAXBContext context = JAXBContext.newInstance(XMLSettings.class);
			
			Marshaller m = context.createMarshaller();
	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        
	        m.marshal(this, file);
		} catch (JAXBException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void loadSettings()
	{
		File file = new File(location + ".xml");
		
		if(!file.exists())
		{
			try {
				Files.createFile(file.toPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(XMLSettings.class);
			Unmarshaller m = context.createUnmarshaller();			
			XMLSettings s  = (XMLSettings)m.unmarshal(file);
			
			for (Entry<String, Setting<?>> setting : s.getSettings().entrySet()) 
			{
				setting.getValue().setName(setting.getKey());
				add(setting.getKey(), setting.getValue().getValue());
			}
		} catch (JAXBException e) 
		{
			e.printStackTrace();
		}		
		
	}
}
