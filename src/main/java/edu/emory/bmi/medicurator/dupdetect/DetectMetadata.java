package edu.emory.bmi.medicurator.dupdetect;

import org.infinispan.Cache;
import edu.emory.bmi.medicurator.infinispan.Manager;
import edu.emory.bmi.medicurator.general.Metadata;
import edu.emory.bmi.medicurator.image.Image;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.Function;
import java.util.*;
import java.io.Serializable;
import java.util.stream.Stream;
import org.infinispan.stream.CacheCollectors;
import java.util.stream.Collectors;


class ParseMetadata implements Serializable, Function<Map.Entry<UUID, Metadata>, Map.Entry<String, UUID>[]>
{
    public Map.Entry<String, UUID>[] apply(Map.Entry<UUID, Metadata> e)
    {
	try {
	Cache<String, Integer> order = Manager.get().getCache("metaOrder");
	Metadata meta = e.getValue();
	String[] keys = meta.getKeys();

	ArrayList<Map.Entry<String, UUID>> result = new ArrayList<Map.Entry<String, UUID>>();
	for (int r = 0; r < 5; ++r)
	{
	    int choose = -1;
	    for (int i = 1; i < keys.length; ++i)
	    {
		if (!keys[i].equals("") && (choose == -1 || order.get(keys[i]) > order.get(keys[choose])))
		    choose = i;
	    }
	    result.add(new SimpleEntry<String, UUID>(keys[choose] + meta.get(keys[choose]), e.getKey()));
	    keys[choose] = "";
	}
	return (SimpleEntry<String, UUID>[])result.toArray(new SimpleEntry[0]);

	}
	catch (Exception x) {
	    System.out.println(x);
	}
	return null;
    }
}

public class DetectMetadata
{
    public static DuplicatePair[] detect(Cache<UUID, Image> origin)
    {
	Map<String, List<Map.Entry<String, UUID>>> candidates = 
	    origin.entrySet().parallelStream()
	    .map((Serializable & Function<Map.Entry<UUID, Image>, Map.Entry<UUID, Metadata>>)
		    e -> new SimpleEntry<UUID, Metadata>(e.getKey(), e.getValue().getMetadata()))
	    .map(new ParseMetadata())
	    .flatMap((Serializable & Function<Map.Entry<String, UUID>[], Stream<Map.Entry<String, UUID>>>) Arrays::stream)
	    .collect(CacheCollectors.serializableCollector(() -> Collectors.groupingBy(e -> e.getKey())));

	ArrayList<DuplicatePair> result = new ArrayList<DuplicatePair>();
	for (Map.Entry<String, List<Map.Entry<String, UUID>>> e : candidates.entrySet())
	{
	    Map.Entry<String, UUID>[] dup = (Map.Entry<String, UUID>[])e.getValue().toArray(new Map.Entry[0]);
	    for (int i = 1; i < dup.length; ++i)
	    {
		result.add(new DuplicatePair(dup[0].getValue(), dup[i].getValue()));
	    }
	}
	return (DuplicatePair[])result.toArray(new DuplicatePair[0]);
    }
}

