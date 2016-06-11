package edu.emory.bmi.medicurator.infinispan;

import org.infinispan.manager.DefaultCacheManager;

/*
 * Just start a Infinispan node
 */
public class StartInfinispan
{
    public static void main(String args[])
    {
	DefaultCacheManager manager = Manager.get();
    }
}

