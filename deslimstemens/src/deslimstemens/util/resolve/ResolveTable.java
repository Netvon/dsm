package deslimstemens.util.resolve;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResolveTable 
{
	private List<ResolveEntry> resolveTable;
	
	public ResolveTable()
	{
		this.resolveTable = new ArrayList<ResolveEntry>();
	}
	
	public ResolveTable(File f) 
	{
		this();
		
		List<String> lines = Collections.emptyList();
		try 
		{
			lines = Files.readAllLines(f.toPath());
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
				
				try 
				{
					add(Class.forName(key), Class.forName(value));
				} 
				catch (ClassNotFoundException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public ResolveTable(ResolveEntry...entries)
	{
		this();
		
		for (ResolveEntry resolveEntry : entries) 
		{
			resolveTable.add(resolveEntry);
		}
	}
	
	public Class<?> get(Class<?> key)
	{
		for (ResolveEntry resolveEntry : resolveTable) 
		{
			if(resolveEntry.resolvesFrom().equals(key))
			{
				return resolveEntry.resolvesTo();
			}
		}
		
		return null;
	}
	
	public void add(Class<?> resolveFrom, Class<?> resolveTo)
	{
		ResolveEntry r = new ResolveEntry(resolveFrom, resolveTo);
		
		if(!contains(r))
			this.resolveTable.add(new ResolveEntry(resolveFrom, resolveTo));
	}
	
	public boolean contains(ResolveEntry entry)
	{
		if(resolveTable == null || resolveTable.size() < 0)
			return false;
		
		for (ResolveEntry resolveEntry : resolveTable) 
		{
			if(resolveEntry.equals(entry))
				return true;
		}
		
		return false;
	}
	
	public boolean contains(Class<?> resolveFrom)
	{
		if(resolveTable == null || resolveTable.size() < 0)
			return false;
		
		for (ResolveEntry resolveEntry : resolveTable) 
		{
			if(resolveEntry.resolveFrom.equals(resolveFrom))
				return true;
		}
		
		return false;
	}

	
}
