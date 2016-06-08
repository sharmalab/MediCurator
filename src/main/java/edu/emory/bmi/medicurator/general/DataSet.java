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

    public boolean download()
    {
	if (downloaded) 
	{
	    System.out.println("*********** Dataset " + getID() + " already downloaded");
	    return true;
	}
	try {
	    getImages();
	    if (getSubsets() != null)
	    {
		for (UUID id : getSubsets())
		{
		    if (!ID.getDataSet(id).download())
		    {
			return false;
		    }
		}
	    }
	    downloaded = true;
	    store();
	}
	catch (Exception e) {
	    System.out.println("[ERROR] when downloading dataset " + getID() + " -- " + e);
	    return false;
	}
	System.out.println("Downloaded Dataset " + getID());
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

