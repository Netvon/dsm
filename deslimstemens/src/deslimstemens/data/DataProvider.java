package deslimstemens.data;

import deslimstemens.model.Competition;

public abstract class DataProvider 
{
	public abstract Competition getCompetition(int id);
	public abstract Competition[] getAllCompetitions();
}
