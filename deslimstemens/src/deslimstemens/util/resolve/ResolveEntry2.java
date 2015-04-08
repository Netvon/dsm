package deslimstemens.util.resolve;

import deslimstemens.data.KeyValue;

public class ResolveEntry2 extends KeyValue<Class<?>, Class<?>> {

	public ResolveEntry2(Class<?> key, Class<?> value) 
	{
		super(key, value);
	}
	
	public Class<?> resolvesTo()
	{
		return this.getValue();
	}
	
	public Class<?> resolvesFrom()
	{
		return this.getKey();
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof ResolveEntry2)
		{
			ResolveEntry2 r = (ResolveEntry2) obj;
			return this.resolvesFrom().equals(r);
		}
		
		return false;
	}
}
