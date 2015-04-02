package deslimstemens.util;

import java.util.ArrayList;
import java.util.List;

public class Async 
{
	private static List<Thread> threadList;

	private static void init()
	{
		if(threadList == null)
		{
			threadList = new ArrayList<Thread>();
		}
	}
	
	public static void Invoke(String name, Runnable target)
	{
		Invoke(name, target, 0, 0, 0);
	}
	
	public static void Invoke(String name, Runnable target, int interval)
	{
		Invoke(name, target, interval, 0, -1);
	}
	
	public static void Invoke(String name, Runnable target, int interval, int delay)
	{
		Invoke(name, target, interval, delay, -1);
	}
	
	public static void Invoke(String name, Runnable target, int interval, int delay, int count)
	{
		init();
		
		Runnable loop = () ->
		{
			int c = count;
			try 
			{
				Thread.sleep(delay * 1000);
				while(c > 0 || c == -1)
				{
					Thread.sleep(interval * 1000);
					target.run();
					if(c > 0)
						c--;
				}
			} 
			catch (InterruptedException e) 
			{				
				System.out.println(Thread.currentThread().getName() + " interrupted");
			}
			
		};
		
		Thread t = new Thread(loop, name);
		threadList.add(t);
		t.start();
	}
	
	public static void stopAll()
	{
		if(threadList == null)
			return;
		
		for (Thread thread : threadList) 
		{
			if(thread == null)
				continue;
			
			thread.interrupt();
		}
	}
}
