package edu.emory.bmi.medicurator.dupdect;

import edu.emory.bmi.medicurator.general.*;
import edu.emory.bmi.medicurator.infinispan.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.UUID;

public class DupDetect
{
    public static DuplicatePair[] detect(UUID[] imgIDs)
    {
	Cache<UUID, Image> idCache = Manager.get().getCache(UUID.randomUUID().toString());
	for (UUID id : imgIDs)
	{
	    idCache.put(id, ID.getImage(id));
	}

	Map<String, Integer> order = idCache.entrySet().parallelStream()
	    .map((Serializalbe & Function<Map.Entry<UUID, Integer>, Map.Entry<String, String>[]>) e ->
		    {
			Metadata meta = e.getValue().getMetadata();
			String[] keys = meta.getKeys();
			Map.Entry<String, String>[] result = new SimpleEntry<String, String>[keys.length];
			for (int i = 0; i < keys.length; ++i)
			{
			    result[i] = new SimpleEntry(keys[i], meta.get(keys[i]));
			}
			return result;
		    })
	    .flatMap((Serializable & Function<Map.Entry<String, String>[], Stream<Map.Entry<String, String>>>) Arrays::stream)
	    .collect(CacheCollectors.serializableCollector(() -> Collectors.groupingBy(Map.Entry<String, String>::geyKey)))
	    .entrySet().parallelStream()
	    .map((Serializable & Function<Map.Entry<String, String[]>, Map.Entry<String, Integer>>) e ->
		    {
			String[] values = e.getValue();
			HashSet<String> S = new HashSet<String>(values);
			return new SimpleEntry(e.getKey(), S.size());
		    }
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
	List<DuplicatePair> result = candidate.keySet().parallemStream()
	    .filter((Serializable & Predicate & Function<DuplicatePair, Boolean>) dp -> Verify.verify(ID.getImage(dp.first), ID.getImage(dp.second)))
	    .collect(CacheCollectors.serializableCollector(() -> Collectors.toList()));


	candidate.stop();
	idCache.stop();
	return result.toArray();
    }
}

