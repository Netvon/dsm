package deslimstemens.util.resolve;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import deslimstemens.data.SettingsProvider;
import deslimstemens.data.SimpleSettings;
import deslimstemens.exception.ResolveException;

/**
 * A class used to resolve classes at runtime.
 * This class uses a {@link SimpleSettings} object to save the resolve table to the disk.
 * 
 * <p>
 * You can use the {@link #resolve(Class, Object...) resolve} method to resolve a class.
 * In this example the Resolve Table is located in the resolvetable file
 * <pre>
 * {@code
 * Resolver resolver = new Resolver("resolvetable");
 * DataProvider data = resolver.Resolve(DataProvider.class);
 * }
 * </pre>
 * Resolving a Class can result in a {@link ResovleException} if the Class is not
 * in the resolve table.
 * 
 * @author Tom van Nimwegen
 * @see SimpleSettings
 * @see ResolveException
 */
public class Resolver 
{
	private SettingsProvider settings;
	
	/**
	 * Initializes a new {@code Resolver} object with a resolve table
	 * at the {@code filelocation}.
	 * 
	 * @param filelocation The location of the resolve table on disk
	 */
	public Resolver(String filelocation) 
	{
		settings = new SimpleSettings(filelocation);
	}
	
	/**
	 * Loads the resolve table from file
	 * 
	 * @see SimpleSettings
	 */
	public void load()
	{
		settings.loadSettings();
	}
	
	/**
	 * Resolves a Class using a resolve table.
	 * 
	 * <p>
	 * You can resolve a Class by specifying what the superclass is.
	 * So if you want the default {@code DataProvider} you can do the following:
	 * <blockquote><pre>
	 * Resolver resolver = new Resolver("filelocation");
	 * DataProvider data = resolver.Resolve(DataProvider.class);
	 * </pre></blockquote>
	 * 
	 * The above example would only work if the Class you want to resolver
	 * had a constructor without any parameters. If you have a Class you want to 
	 * resolve with parameters you can add them after the specified class:
	 * 
	 * <blockquote><pre>
	 * Resolver resolver = new Resolver("filelocation");
	 * SettingsProvider settings = resolver.Resolve(SettingsProvider.class, "filelocation");
	 * </pre></blockquote>
	 *
	 * Keep in mind that the number of arguments needs to be the same as the 
	 * constructor you want to call. If the Class you want to resolve has two or more
	 * constructors with the same amount of arguments the first one will be used. 
	 * 
	 * @param clazz The Class you want the resolve
	 * @param ctorParams The optional arguments if you want to call a different constructor
	 * @return A new instance of the resolved Class
	 * @throws ResolveException
	 */
	@SuppressWarnings("unchecked")
	public <V,T extends V> T resolve(Class<V> clazz, Object...ctorParams) throws ResolveException
	{
		try 
		{
			//get the name of the class mapped to clazz.
			String clazzName = settings.get(clazz.getName(), "Object");
			Class<T> returnclass = (Class<T>) Class.forName(clazzName);
			
			
			if(ctorParams.length > 0)
			{
				Constructor<?>[] ctors = returnclass.getDeclaredConstructors();
				
				for (Constructor<?> constructor : ctors) 
				{
					int paramcount = constructor.getParameterTypes().length;
					if(paramcount == ctorParams.length)
					{
						return (T) constructor.newInstance(ctorParams);
					}
				}
			}
			
			return returnclass.newInstance();
		} 
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			ResolveException re = new ResolveException(clazz);
			re.addSuppressed(e);
			throw re;
		}
	}
}
