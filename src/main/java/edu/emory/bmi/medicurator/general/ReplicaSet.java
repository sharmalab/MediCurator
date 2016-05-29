package edu.emory.bmi.medicurator.general;

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
	ID.setReplicaSet(replicaSetID, this);
	dataSets = new HashSet<UUID>();
    }

    public ReplicaSet(ReplicaSet another)
    {
	ID.setReplicaSet(replicaSetID, this);
	dataSets = new HashSet<UUID>(another.dataSets);
    }

    public boolean putDataSet(UUID datasetID)
    {
	return dataSets.add(datasetID);
    }

    public UUID[] getDataSets()
    {
	return (UUID[])dataSets.toArray();
    }

    public boolean removeDataSet(UUID datasetID)
    {
	return dataSets.remove(datasetID);
    }

    public boolean download() throws Exception
    {
	for (UUID id : dataSets)
	{
	    ID.getDataSet(id).download();
	}
	return true;
    }
}

