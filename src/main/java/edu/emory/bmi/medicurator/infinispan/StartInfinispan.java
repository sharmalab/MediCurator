package edu.emory.bmi.medicurator.infinispan;

import org.infinispan.manager.DefaultCacheManager;

public class StartInfinispan
{
    public static void main(String args[])
    {
	DefaultCacheManager manager = Manager.get();
    }
}

