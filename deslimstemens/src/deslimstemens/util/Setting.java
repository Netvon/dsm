package deslimstemens.util;

public class Setting<T> 
{
	private String _name;
	private T _value;
	
	public Setting(){}
	
	public Setting(String name)
	{
		this._name = name;
	}
	
	public Setting(String name, T value)
	{
		this(name);
		setValue(value);
	}
	
	public void setName(String value)
	{
		if(value.trim().isEmpty())
			return;
		
		this._name = value;
	}

	public T getValue() 
	{
		return _value;
	}

	public void setValue(T value) 
	{
		this._value = value;
	}

	public String getName() 
	{
		return _name;
	}
	
	@Override
	public String toString() 
	{
		return "{Name: " + getName() + ", Value: " + getValue().toString() + "}";
	}
	
}
