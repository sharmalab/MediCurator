package edu.emory.bmi.medicurator.dupdect;

import org.infinispan.Cache;
import edu.emory.bmi.medicurator.general.Metadata;
import java.util.AbstractMap.SimpleEntry
import java.util.UUID;
import java.util.Map;
import java.util.List;

public class DetectImage
{
    public static DuplicatePair[] <UUID, UUID> detect(Cache<UUID, Image> origin)
    {
	Map<String, List<UUID>> candidates = 
	    origin.entrySet().parallelStream()
	    .map((Serializable & Function<Map.Entry<UUID, Image>, Map.Entry<UUID, String>>) 
		    e -> new SimpleEntry<String, UUID>(e.getValue().getHashCode(), e.getKey()))
	    .collect(CacheCollectors.serializableCollector(() -> 
			Collectors.groupingBy((Serializable & Function<Map.Entry<String, UUID>, String>) e -> e.getKey())));
	ArrayList<DuplicatePair> result new ArrayList<DuplicatePair>();
	for (Map.Entry<String, List<UUID>> e : candidates.entrySet())
	{
	    UUID[] dup = e.getValue().toArray();
	    for (int i = 1; i < dup.length; ++i)
	    {
		result.add(new DuplicatePair(dup[0], dup[i]));
	    }
	}
	return result.toArray();
    }
}

