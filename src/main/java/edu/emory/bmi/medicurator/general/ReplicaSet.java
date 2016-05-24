package edu.emory.bmi.medicurator.general;

import java.io.Serializable;

public class ReplicaSet implements Serializable
{
    private UUID replicaSetID = UUID.randomUUID();
    public UUID getID() { return replicaSetID; }

    private HashSet<UUID> dataSets;

    public ReplicaSet()
    {
    }

    public boolean putDataSet(UUID datasetID)
    {
    }

    public UUID[] getDataSets()
    {
    }

    public boolean removeDataSet(UUID datasetID)
    {
    }

    public boolean download()
    {
    }
}

