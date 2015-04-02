package deslimstemens.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import deslimstemens.util.Setting;
/**
 * 
 * @author Tom van Nimwegen
 *
 */
public class SettingTest {

	@Test
	public void testSettingStringT() 
	{
		Setting<String> setting = new Setting<>("TestSetting", "TestValue");
		assertEquals(setting.getName(), "TestSetting");
		assertEquals(setting.getValue(), "TestValue");
	}

	@Test
	public void testSetName() 
	{
		Setting<String> setting = new Setting<>();
		setting.setName("TestSetting");
		assertEquals(setting.getName(), "TestSetting");
		
		setting.setName("");
		assertEquals(setting.getName(), "TestSetting");
	}

	@Test
	public void testSetValue() 
	{
		Setting<String> setting = new Setting<>();
		setting.setValue("TestValue");
		assertEquals(setting.getValue(), "TestValue");
	}

	@Test
	public void testToString() 
	{
		Setting<String> setting = new Setting<>("TestSetting", "TestValue");
		assertEquals(setting.toString(),  "{Name: TestSetting, Value: TestValue}");
	}

}
