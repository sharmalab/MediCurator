package edu.emory.bmi.medicurator.dupdetect;

import org.infinispan.Cache;
import edu.emory.bmi.medicurator.general.Metadata;
import edu.emory.bmi.medicurator.image.Image;
import java.util.*;
import java.util.function.Function;
import java.util.AbstractMap.SimpleEntry;
import java.io.Serializable;
import org.infinispan.stream.CacheCollectors;
import java.util.stream.Collectors;


public class DetectImage
{
    public static DuplicatePair[] detect(Cache<UUID, Image> origin) throws Exception
    {
	Map<String, List<Map.Entry<String, UUID>>> candidates = 
	    origin.entrySet().parallelStream()
	    .map((Serializable & Function<Map.Entry<UUID, Image>, Map.Entry<String, UUID>>) 
		    e -> new SimpleEntry<String, UUID>(e.getValue().getHashCode(), e.getKey()))
	    .collect(CacheCollectors.serializableCollector(() -> Collectors.groupingBy(e -> e.getKey())));

	ArrayList<DuplicatePair> result = new ArrayList<DuplicatePair>();
	for (Map.Entry<String, List<Map.Entry<String, UUID>>> e : candidates.entrySet())
	{
	    Map.Entry<String, UUID>[] dup = (Map.Entry<String, UUID>[])e.getValue().toArray();
	    for (int i = 1; i < dup.length; ++i)
	    {
		result.add(new DuplicatePair(dup[0].getValue(), dup[i].getValue()));
	    }
	}
	return (DuplicatePair[])result.toArray();
    }
}

