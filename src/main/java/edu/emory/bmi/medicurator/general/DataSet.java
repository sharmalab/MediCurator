package edu.emory.bmi.medicurator.general;

import edu.emory.bmi.medicurator.image.Image;
import edu.emory.bmi.medicurator.infinispan.ID;
import java.util.UUID;
import java.util.zip.*;

public abstract class DataSet
{
    private UUID dataSetID = UUID.randomUUID();
    public UUID getID() { return dataSetID; }

    private UUID metaID;
    protected final String datasetType;
    private boolean downloaded;

    public abstract UUID getParent();
    public abstract UUID[] getSubsets();
    public abstract UUID[] getImages();
    public abstract boolean updated();

    public DataSet(String datasetType)
    {
	this.datasetType = datasetType;
	downloaded = false;
	metaID = null;
    }

    public boolean download() throws Exception
    {
	if (downloaded) return true;
	getImages();
	if (getSubsets() != null)
	{
	    for (UUID id : getSubsets())
	    {
		ID.getDataSet(id).download();
	    }
	}
	downloaded = true;
	store();
	return true;
    }

    public UUID getMetaID()
    {
	return metaID;
    }

    public Metadata getMetadata()
    {
	return ID.getMetadata(metaID);
    }

    public void setMetaID(UUID id)
    {
	metaID = id;
	store();
    }

    public void store()
    {
	ID.setDataSet(getID(), this);
    }
}

