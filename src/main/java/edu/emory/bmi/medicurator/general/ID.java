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
    private static Cache<UUID, DataSource> maptoDataSource;

    static
    {
	manager = new DefaultCacheManager();
	maptoUser = manager.getCache("maptoUser");
	maptoReplicaSet = manager.getCache("maptoReplicaSet");
	maptoDataSet = manager.getCache("maptoDataSet");
	maptoMetadata = manager.getCache("maptoMetadata");
	maptoData = manager.getCache("maptoData");
	maptoDataSource = manager.getCache("maptoDataSource");
    }

    public static User getUser(UUID id) { return maptoUser.get(id); }

    public static boolean setUser(UUID id, User user) { maptoUser.put(id, user); return true; }

    public static ReplicaSet getReplicaSet(UUID id) { return maptoReplicaSet.get(id); }

    public static boolean setReplicaSet(UUID id, ReplicaSet replicaset) { maptoReplicaSet.put(id, replicaset); return true; }

    public static DataSet getDataSet(UUID id) { return maptoDataSet.get(id); }

    public static boolean setDataSet(UUID id, DataSet dataset) { maptoDataSet.put(id, dataset); return true; }

    public static Metadata getMetadata(UUID id) { return maptoMetadata.get(id); }

    public static boolean setMetadata(UUID id, Metadata meta) { maptoMetadata.put(id, meta); return true; }

    public static Data getData(UUID id) { return maptoData.get(id); }

    public static boolean setData(UUID id, Data data) { maptoData.put(id, data); return true; }

    public static DataSource getDataSource(UUID id) { return maptoDataSource.get(id); }

    public static boolean setDataSource(UUID id, DataSource dataSource) { maptoDataSource.put(id, dataSource); return true; }
}

