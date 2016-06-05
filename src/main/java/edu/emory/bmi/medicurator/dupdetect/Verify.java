package edu.emory.bmi.medicurator.dupdetect;

import edu.emory.bmi.medicurator.general.Metadata;
import edu.emory.bmi.medicurator.image.Image;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.Function;
import java.util.*;

public class Verify
{
    public static boolean verify(Image a, Image b)
    {
	try {
	    if (a.getID().equals(b.getID())) return false;
	    if (a.getHashCode().equals(b.getHashCode()))
	    {
		return true;
	    }
	    Metadata metaA = a.getMetadata();
	    Metadata metaB = b.getMetadata();
	    int count = 0;
	    for (String key : metaA.getKeys())
	    {
		if (!metaB.contains(key) || !metaB.get(key).equals(metaA.get(key)))
		    count++;
	    }
	    for (String key : metaB.getKeys())
	    {
		if (!metaA.contains(key))
		    count++;
	    }
	    if (count < 5) 
	    {
		return true;
	    }
	}
	catch (Exception e) {}
	return false;
    }
}

