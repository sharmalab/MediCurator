package edu.emory.bmi.medicurator.general;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import java.util.UUID;

public class ID
{
    private static DefaultCacheManager manager;

    private static Cache<UUID, User> maptoUser;
    private static Cache<UUID, ReplicaSet> maptoReplicaSet;
    private static Cache<UUID, DataSet> maptoDataSet;
    private static Cache<UUID, Metadata> maptoMetadata;
    private static Cache<UUID, Data> maptoData;

    static
    {
	manager = new DefauleCacheManager();
	maptoUser = manager.getCache("maptoUser");
	maptoReplicaSet = manager.getCache("maptoReplicaSet");
	maptoDataSet = manager.getCache("maptoDataSet");
	maptoMetadata = manager.getCache("maptoMetadata");
	maptoData = manager.getCache("maptoData");
    }

    public static User getUser(UUID id) { return maptoUser.get(id); }

    public static boolean setUser(UUID id, User user) { maptoUser.put(id, user); }

    public static ReplicaSet getReplicaSet(UUID id) { return maptoReplicaSet.get(id); }

    public static boolean setReplicaSet(UUID id, ReplicaSet replicaset) { maptoReplicaSet.put(id, replicaset); }

    public static DataSet getDataSet(UUID id) { return maptoDataSet.get(id); }

    public static boolean setDataSet(UUID id, DataSet dataset) { maptoDataSet.put(id, dataset); }

    public static Metadata getMetadata(UUID id) { return maptoMetadata.get(id); }

    public static boolean setMetadata(UUID id, Metadata meta) { maptoMetadata.put(id, meta); }

    public static Data getData(UUID id) { return maptoData.get(id); }

    public static boolean setData(UUID id, Data data) { maptoData.put(id, data); }
}

