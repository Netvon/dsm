package deslimstemens.util.resolve;

public class ResolveEntry
{
	public Class<?> resolveFrom;
	public Class<?> resolveTo;
	
	public ResolveEntry(Class<?> resolveFrom, Class<?> resolveTo) 
	{
		this.resolveFrom = resolveFrom;
		this.resolveTo = resolveTo;
	}
	
	public Class<?> resolvesTo()
	{
		return resolveTo;
	}
	
	public Class<?> resolvesFrom()
	{
		return resolveFrom;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof ResolveEntry)
		{
			ResolveEntry r = (ResolveEntry) obj;
			return this.resolveFrom.equals(r);
		}
		
		return false;
	}
}
