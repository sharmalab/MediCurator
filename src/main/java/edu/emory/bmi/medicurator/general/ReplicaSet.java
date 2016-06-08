package edu.emory.bmi.medicurator.general;

import edu.emory.bmi.medicurator.infinispan.ID;
import java.util.UUID;
import java.util.HashSet;
import java.io.Serializable;

public class ReplicaSet implements Serializable
{
    private UUID replicaSetID = UUID.randomUUID();
    public UUID getID() { return replicaSetID; }

    private HashSet<UUID> dataSets;

    public ReplicaSet()
    {
	dataSets = new HashSet<UUID>();
	store();
    }

    public ReplicaSet(ReplicaSet another)
    {
	dataSets = new HashSet<UUID>(another.dataSets);
	store();
    }

    public void putDataSet(UUID datasetID)
    {
	dataSets.add(datasetID);
	store();
    }

    public UUID[] getDataSets()
    {
	return (UUID[])dataSets.toArray();
    }

    public void removeDataSet(UUID datasetID)
    {
	dataSets.remove(datasetID);
	store();
    }

    public void download() throws Exception
    {
	for (UUID id : dataSets)
	{
	    ID.getDataSet(id).download();
	}
    }

    public void store()
    {
	ID.setReplicaSet(replicaSetID, this);
    }
}

