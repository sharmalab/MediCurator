package edu.emory.bmi.medicurator.general;

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
	return dataSets.add(dataSetID);
    }

    public UUID[] getDataSets()
    {
	return dataSets.toArray();
    }

    public boolean removeDataSet(UUID datasetID)
    {
	return dataSets.remove(datasetID);
    }

    public boolean download()
    {
	for (UUID id : dataSets)
	{
	    ID.getDataSet(id).download();
	}
	return true;
    }
}

