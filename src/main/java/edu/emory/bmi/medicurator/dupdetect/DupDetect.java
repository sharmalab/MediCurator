package edu.emory.bmi.medicurator.dupdetect;

import edu.emory.bmi.medicurator.general.*;
import edu.emory.bmi.medicurator.image.Image;
import edu.emory.bmi.medicurator.infinispan.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Predicate;
import java.util.function.Function;
import org.infinispan.Cache;
import org.infinispan.stream.CacheCollectors;
import java.io.Serializable;

public class DupDetect
{
    public static DuplicatePair[] detect(UUID[] imgIDs) throws Exception
    {
	Cache<UUID, Image> idCache = Manager.get().getCache(UUID.randomUUID().toString());
	for (UUID id : imgIDs)
	{
	    idCache.put(id, ID.getImage(id));
	}

	Map<String, Integer> order = idCache.entrySet().parallelStream()
	    .map((Serializable & Function<Map.Entry<UUID, Image>, Map.Entry<String, String>[]>) e ->
		    {
			Metadata meta = e.getValue().getMetadata();
			String[] keys = meta.getKeys();
			SimpleEntry[] result = new SimpleEntry[keys.length];
			for (int i = 0; i < keys.length; ++i)
			{
			    result[i] = new SimpleEntry(keys[i], meta.get(keys[i]));
			}
			return result;
		    })
	    .flatMap((Serializable & Function<Map.Entry<String, String>[], Stream<Map.Entry<String, String>>>) Arrays::stream)
	    .collect(CacheCollectors.serializableCollector(() -> Collectors.groupingBy(e -> e.getKey())))
	    .entrySet().parallelStream()
	    .map((Serializable & Function<Map.Entry<String, List<Map.Entry<String, String>>>, Map.Entry<String, Integer>>) e ->
		    {
			HashSet<String> S = new HashSet<String>();
			for (Map.Entry<String, String> s : e.getValue()) S.add(s.getValue());
			return new SimpleEntry(e.getKey(), S.size());
		    })
	    .collect(Collectors.toMap(Map.Entry<String, Integer>::getKey, Map.Entry<String, Integer>::getValue));

	Cache<String, Integer> metaOrder = Manager.get().getCache("metaOrder");
	for (Map.Entry<String, Integer> e : order.entrySet())
	{
	    metaOrder.put(e.getKey(), e.getValue());
	}

	DuplicatePair[] candidateMeta = DetectMetadata.detect(idCache);
	DuplicatePair[] candidateImg = DetectImage.detect(idCache);

	Cache<DuplicatePair, Integer> candidate = Manager.get().getCache(UUID.randomUUID().toString());
	for (DuplicatePair dp : candidateMeta)
	{
	    candidate.put(dp, 0);
	}
	for (DuplicatePair dp : candidateImg)
	{
	    candidate.put(dp, 0);
	}
	List<DuplicatePair> result = candidate.keySet().parallelStream()
	    .filter(dp -> Verify.verify(ID.getImage(dp.first), ID.getImage(dp.second)))
	    .collect(CacheCollectors.serializableCollector(() -> Collectors.toList()));

	candidate.stop();
	idCache.stop();
	return (DuplicatePair[])result.toArray(new DuplicatePair[0]);
    }
}

