package edu.emory.bmi.medicurator.dupdect;

import org.infinispan.Cache;
import edu.emory.bmi.medicurator.general.Metadata;
import java.util.AbstractMap.SimpleEntry
import java.util.function.Function;
import java.util.*;

public class Verify
{
    public static boolean verify(Image a, Image b)
    {
	if (a.getHashCode().equals(b.getHashCode()))
	{
	    return true;
	}
	Metadata metaA = a.getMetadata();
	Metadata metaB = b.getMetadata();
	int count = 0;
	for (String key : metaA.getKeys())
	{
	    if (!metaB.containsKey(key) || !metaB.get(key).equals(metaA.get(key)))
		count++;
	}
	for (String key : metaB.getKeys())
	{
	    if (!metaA.containsKey(key))
		count++;
	}
	if (count < 5) 
	{
	    return true;
	}
	return false;
    }
}

