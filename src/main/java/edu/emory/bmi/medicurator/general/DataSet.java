package edu.emory.bmi.medicurator.general;

import java.util.UUID;

public abstract class DataSet
{
    private UUID dataSetID = UUID.randomUUID();
    public UUID getID() { return dataSetID; }

    private UUID metaID;

    protected final String datasetType;
    private boolean downloaded;

    public DataSet(String datasetType)
    {
	ID.setDataSet(dataSetID, this);
	this.datasetType = datasetType;
	downloaded = false;
	metaID = 0;
    }

    public abstract UUID getParent();
    public abstract UUID[] getSubsets();
    public abstract UUID[] getData();

    public boolean download()
    {
	if (downloaded) return true;
	for (UUID id in getSubsets())
	{
	    ID.getDataSet(id).download();
	}
	for (UUID id in getData())
	{
	    ID.getData(id).download();
	}
	return true;
    }

    public UUID getMetaID()
    {
	return metaID;
    }

    public boolean setMetaID(UUID metaID)
    {
	this.metaID = metaID;
	return true;
    }

    public Metadata getMetadata()
    {
	return ID.getMetadata(metaID);
    }

    public boolean setMetadata(Metadata meta)
    {
	return setMetaID(meta.getID());
    }
}

