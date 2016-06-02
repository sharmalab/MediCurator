package edu.emory.bmi.medicurator.dupdect;

import org.infinispan.Cache;
import edu.emory.bmi.medicurator.general.Metadata;
import java.util.AbstractMap.SimpleEntry
import java.util.function.Function;
import java.util.*;

class ParseMetadata implements Serializable, Function<Map.Entry<UUID, Metadata>, Map.Entry<String, UUID>[]>
{
    private static DefaultCacheManager manager = new DefaultCacheManager("infinispan.xml"); 
    private static Cache<String, Integer> order = manager.getCache("metaOrder");

    public Map.Entry<String, UUID>[] apply(Map.Entry<UUID, Metadata> e)
    {
	Metadata meta = e.getValue();
	String[] keys = meta.getKeys();

	ArayList<Map.Entry<UUID, String>> result = new ArrayList<Map.Entry<UUID, String>>();
	for (int r = 0; r < 5; ++r)
	{
	    int choose = 0;
	    for (int i = 1; i < keys.length; ++i)
	    {
		if (!keys[i].equals("") && order.get(keys[i]) < order.get(keys[0]))
		    choose = i;
	    }
	    result.add(new SimpleEntry<UUID, String>(meta.get(keys[choose]), e.getKey()));
	    keys[choose] = "";
	}
	return result.toArray();
    }
}

public class DetectMetadata
{
    public static DuplicatePair[] <UUID, UUID> detect(Cache<UUID, Image> origin)
    {
	Map<String, List<UUID>> candidates = 
	    origin.entrySet().parallelStream()
	    .map((Serializable & Function<Map.Entry<UUID, Image>, Map.Entry<UUID, Metadata>>)
		    e -> new SimpleEntry<UUID, Metadata>(e.getValue().getMetadata()))
	    .map(new parseMetadata())
	    .flatMap((Serializable & Function<Map.Entry<String, UUID>[], Stream<Map.Entry<String, UUID>>) Arrays::stream)
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

