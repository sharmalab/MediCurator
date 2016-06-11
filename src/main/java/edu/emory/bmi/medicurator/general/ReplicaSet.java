package edu.emory.bmi.medicurator.general;

import edu.emory.bmi.medicurator.infinispan.ID;
import java.util.UUID;
import java.util.HashSet;
import java.io.Serializable;

/*
 * A ReplicaSet is a set of DataSets.
 * One can add or remove DataSet in the ReplicaSet, and download the ReplicaSet.
 * When downloads a ReplicaSet, MediCurator will download all DataSets in it.
 */
public class ReplicaSet implements Serializable
{
    //the unique ID used to retrieve a ReplicaSet inside MediCurator
    private UUID replicaSetID = UUID.randomUUID();
    public UUID getID() { return replicaSetID; }

    //store the IDs of DataSets in this ReplicaSet
    private HashSet<UUID> dataSets;

    //create a new empty ReplicaSet
    public ReplicaSet()
    {
	dataSets = new HashSet<UUID>();
	store();
    }

    //make a copy of another ReplicaSet
    public ReplicaSet(ReplicaSet another)
    {
	dataSets = new HashSet<UUID>(another.dataSets);
	store();
    }

    //add a DataSet
    public void putDataSet(UUID datasetID)
    {
	dataSets.add(datasetID);
	store();
    }

    //get the array of IDs of DataSets in the ReplicaSet
    public UUID[] getDataSets()
    {
	return (UUID[])dataSets.toArray();
    }

    //remove a DataSet from this ReplicaSet
    public void removeDataSet(UUID datasetID)
    {
	dataSets.remove(datasetID);
	store();
    }

    //download this ReplicaSet to local
    public void download()
    {
	for (UUID id : dataSets)
	{
	    ID.getDataSet(id).download();
	}
    }

    //store the information of this ReplicaSet to Infinispan
    public void store()
    {
	ID.setReplicaSet(replicaSetID, this);
    }
}

