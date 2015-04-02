package deslimstemens.controller;

import deslimstemens.data.DataProvider;
import deslimstemens.data.SettingsProvider;
import deslimstemens.data.Logger;
import deslimstemens.view.ResourceProvider;
import deslimstemens.view.ViewHelper;

public abstract class Controller
{
	protected final SettingsProvider settingsProvider;
	protected final DataProvider dataProvider;
	protected final Logger logger;
	protected final ResourceProvider resourceProvider;
	
	protected final ViewHelper viewHelper;
	
	protected Controller parent;
	
	public Controller(ViewHelper vh) 
	{
		this.viewHelper = vh;
		this.settingsProvider = vh.getSettingsProvider();
		this.dataProvider = vh.getDataProvider();
		this.logger = vh.getLogger();
		this.resourceProvider = vh.getResourceProvider();
	}
	
	/**
	 * A method to get the parent controller of this controller.
	 * @return The parent of this controller. Can be <code>null</code> of no
	 * parent was set
	 */
	@SuppressWarnings("unchecked")
	public <T extends Controller> T getParent()
	{
		return (T) parent;
	}
	
	public abstract void periodicUpdate();
}
