package deslimstemens.data;

import java.util.Random;

import deslimstemens.model.Competition;
import deslimstemens.model.ScoreUser;

public class MockDB extends DataProvider 
{
	@Override
	public Competition getCompetition(int id) 
	{
		Competition c = new Competition(id);
		
		Random ran = new Random(id);
		int complength = ran.nextInt(5) + 2;
		
		for (int i = 0; i < complength; i++) 
		{
			int namelength = ran.nextInt(10) + 5;
			
			char[] name = new char[namelength];
			
			for (int y = 0; y < name.length; y++) 
			{
				name[y] = (char)(ran.nextInt(25) + 65);
			}			
			
			c.addUser(new ScoreUser(String.valueOf(name), false));
		}
		
		
		return c;
	}

	@Override
	public Competition[] getAllCompetitions() 
	{
		Competition[] carr = new Competition[5];
		
		for (int i = 0; i < carr.length; i++) 
		{
			carr[i] = getCompetition(i);
		}
		
		return carr;
	}
	
}
