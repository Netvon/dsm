package deslimstemens.util.resolve;

import java.io.File;
import java.io.IOException;

import deslimstemens.data.KeyValue;
import deslimstemens.data.KeyValueFile;

public class ResolveTable2 extends KeyValueFile<Class<?>, Class<?>>
{
	public ResolveTable2(String pathToFile) throws IOException 
	{
		super(new File(pathToFile));
	}

	@Override
	protected ResolveEntry2 parseKeyValue(KeyValue<String, String> keyValue)
	{
		try {
			return new ResolveEntry2(Class.forName(keyValue.getKey()), Class.forName(keyValue.getValue()));
		} 
		catch (ClassNotFoundException e) 
		{
			
		}
		
		return null;
	}
	
	
}
