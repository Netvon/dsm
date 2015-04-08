package deslimstemens.test;

import static org.junit.Assert.*;

import org.junit.Test;

import deslimstemens.data.DataProvider;
import deslimstemens.data.MockDB;
import deslimstemens.util.resolve.ResolveEntry;
import deslimstemens.util.resolve.ResolveTable;

public class ResolveTableTest {

	
	@Test
	public void testResolveTableResolveEntryArray() 
	{
		
		ResolveTable rt = new ResolveTable(new ResolveEntry(DataProvider.class, MockDB.class));
		assertTrue(rt.contains(DataProvider.class));
	}

	@Test
	public void testGet() 
	{
		ResolveTable rt = new ResolveTable(new ResolveEntry(DataProvider.class, MockDB.class));
		Class<?> clazz = rt.get(DataProvider.class);
		
		assertTrue(clazz.equals(MockDB.class));
	}

	@Test
	public void testAdd() 
	{
		fail("Not yet implemented");
	}

	@Test
	public void testContains() {
		fail("Not yet implemented");
	}

}
