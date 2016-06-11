package edu.emory.bmi.medicurator.infinispan;

import edu.emory.bmi.medicurator.general.*;
import edu.emory.bmi.medicurator.image.Image;

import org.infinispan.Cache;
import java.util.UUID;

/*
 * ID keeps a map of global unique id to the data instance in Infinispan
 * ID supports User, ReplicaSet, DataSource, DataSet, Metadata and Image
 */
public class ID
{
    //Infinispan Caches
    private static Cache<UUID, User> maptoUser;
    private static Cache<UUID, DataSource> maptoDataSource;
    private static Cache<UUID, ReplicaSet> maptoReplicaSet;
    private static Cache<UUID, DataSet> maptoDataSet;
    private static Cache<UUID, Metadata> maptoMetadata;
    private static Cache<UUID, Image> maptoImage;

    //initialize the Infinispan Caches
    static
    {
	boolean successful = false;
	while (!successful)
	{
	    try {
		maptoUser = Manager.get().getCache("maptoUser");
		maptoDataSource = Manager.get().getCache("maptoDataSource");
		maptoReplicaSet = Manager.get().getCache("maptoReplicaSet");
		maptoDataSet = Manager.get().getCache("maptoDataSet");
		maptoMetadata = Manager.get().getCache("maptoMetadata");
		maptoImage = Manager.get().getCache("maptoImage");
		successful = true;
	    }
	    catch (Exception e) {
		System.out.println("[ERROR] get Infinispan Cache error -- " + e);
	    }
	}
    }


    //get or store data instance with id to Infinispan

    public static User getUser(UUID id) { return maptoUser.get(id); }

    public static void setUser(UUID id, User user) { maptoUser.put(id, user); }

    public static DataSource getDataSource(UUID id) { return maptoDataSource.get(id); }

    public static void setDataSource(UUID id, DataSource dataSource) { maptoDataSource.put(id, dataSource); }

    public static ReplicaSet getReplicaSet(UUID id) { return maptoReplicaSet.get(id); }

    public static void setReplicaSet(UUID id, ReplicaSet replicaset) { maptoReplicaSet.put(id, replicaset); }

    public static DataSet getDataSet(UUID id) { return maptoDataSet.get(id); }

    public static void setDataSet(UUID id, DataSet dataset) { maptoDataSet.put(id, dataset); }

    public static Metadata getMetadata(UUID id) { return maptoMetadata.get(id); }

    public static void setMetadata(UUID id, Metadata meta) { maptoMetadata.put(id, meta); }

    public static Image getImage(UUID id) { return maptoImage.get(id); }

    public static void setImage(UUID id, Image image) { maptoImage.put(id, image); }
}

