package deslimstemens.exception;

@SuppressWarnings("serial")
public class ResolveException extends Exception 
{
	private Class<?> requestedClass;
	
	public ResolveException(Class<?> requestedClass)
	{
		this.requestedClass = requestedClass;
	}
	
	public Class<?> getRequestedClass()
	{
		return requestedClass;
	}
}
