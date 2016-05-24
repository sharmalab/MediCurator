package edu.emory.bmi.medicurator.general;

import java.util.List;
import java.util.UUID;

public abstract class DataSet
{
    private UUID dataSetID = UUID.randomUUID();
    public UUID getID() { return dataSetID; }

    private Metadata meta;

    public abstract Metadata getMetadata();
    public abstract ImageSet[] getSubset();
    public abstract Data[] getData();
}

