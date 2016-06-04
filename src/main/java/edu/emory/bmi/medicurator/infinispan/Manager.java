package edu.emory.bmi.medicurator.infinispan;

import org.infinispan.manager.DefaultCacheManager;

public class Manager
{
    private static DefaultCacheManager manager = null;

    public static DefaultCacheManager get() throws Exception
    {   
	if (manager == null)
	{
	    manager = new DefaultCacheManager("infinispan.xml");
	}
	return manager;
    }
}

