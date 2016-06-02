package edu.emory.bmi.medicurator.dupdect;

import edu.emory.bmi.medicurator.general.*;
import java.util.UUID;

public class DupDetect
{
    private static DefaultCacheManager manager;
    private static Cache<String, Integer> order;
    static 
    {
	try 
	{
	    manager = new DefaultCacheManager("infinispan.xml");
	    Cache<String, Integer> order = manager.getCache("metaOrder");
	}
	catch (Exception e) { System.out.println(e); }
    }

    public static DuplicatePair[] detect(UUID[] imgIDs)
    {
	Cache<UUID, UUID> 
	













    }
}

