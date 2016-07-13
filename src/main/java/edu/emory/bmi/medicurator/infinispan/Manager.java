package edu.emory.bmi.medicurator.infinispan;

import org.infinispan.manager.DefaultCacheManager;
import java.io.IOException;

/*
 * the global shared DefaulCacheManager
 */
public class Manager
{
    private static DefaultCacheManager manager = null;

    public static DefaultCacheManager get()
    {   
	while (manager == null)
	{
	    try {
		manager = new DefaultCacheManager("infinispan.xml");
	    }
	    catch (IOException e) {
		System.out.println("[ERROR] get DefaultCacheManager error -- " + e);
	    }
	}
	return manager;
    }
}

