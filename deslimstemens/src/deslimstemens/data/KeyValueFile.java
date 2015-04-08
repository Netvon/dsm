package deslimstemens.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class KeyValueFile<K, V> 
{
	protected File file;
	private List<KeyValue<K,V>> keyValueList;	
	
	private static final String KEYVALUE_SEPARATOR = "=";
	private static final Function<String, KeyValue<String, String>> DEFAULT_PARSE_LINE = input -> 
	{
		String[] kv = input.split(KEYVALUE_SEPARATOR);
		
		if(kv.length > 1)
		{
			String key = kv[0];
			String value = kv[1];
			
			return new KeyValue<String, String>(key, value);
		}
		
		return null;
	};
	
	private static final Function<KeyValue<String, String>, KeyValue<String, String>> DEFAULT_PARSE_KEYVALUE = input -> input;
	
	public KeyValueFile(File file)
	{
		this.file = file;		
		this.keyValueList = new ArrayList<KeyValue<K,V>>();
	}
	
	public void load()
	{
		load(x -> parseLine(x), x -> parseKeyValue(x));
	}
	
	protected void load(Function<String, KeyValue<String, String>> parseLine,
					 Function<KeyValue<String, String>, KeyValue<K, V>> parseKeyValue)
	{
		
		if(!file.exists() || !file.canRead())
			return;
		
		try 
		{
			List<String> lines = Files.readAllLines(file.toPath());
			for (String string : lines) 
			{
				KeyValue<String, String> kv = parseLine.apply(string);
				this.keyValueList.add(parseKeyValue.apply(kv));
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	protected KeyValue<String, String> parseLine(String line)
	{
		return DEFAULT_PARSE_LINE.apply(line);
	}
	
	@SuppressWarnings("unchecked")
	protected KeyValue<K, V> parseKeyValue(KeyValue<String, String> keyValue)
	{
		return (KeyValue<K, V>) DEFAULT_PARSE_KEYVALUE.apply(keyValue);
	}
}
