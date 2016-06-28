package edu.emory.bmi.medicurator.general;

import edu.emory.bmi.medicurator.image.Image;
import edu.emory.bmi.medicurator.infinispan.ID;
import java.util.UUID;
import java.util.zip.*;

/*
 * A DataSet is a node of a Tree.
 * Each DataSet has a parent DataSet and some children DataSets.
 * In a DataSet there might stores several Images.
 * DataSet can be download. When downloads a DataSet, MediCurator will download the Images
 * of the DataSet, then download all its children DataSets recursively.
 * A DataSet has a flag 'downloaded'. One DataSet won't be downloaded twice.
 * Also see DataSource for more information.
 */
public abstract class DataSet
{
    //the unique ID used to retrieve the DataSet inside MediCurator
    private UUID dataSetID = UUID.randomUUID();
    public UUID getID() { return dataSetID; }

    //the ID of the DataSet's Metadata 
    private UUID metaID;

    //the type of the DataSet, similar to DataSource's type
    protected final String datasetType;

    //recored whether the DataSet has been downloaded
    private boolean downloaded;

    //get the ID of parent DataSet
    public abstract UUID getParent();

    //get the array of children DataSets' IDs 
    public abstract UUID[] getSubsets();

    //get the array of IDs of Imags in the DataSet
    public abstract UUID[] getImages();

    //check if the downloaded DataSet is out of date.
    public abstract boolean updated();

    //get a String to describe this DataSet
    public abstract String getKeyword();

    //create a new DataSet
    public DataSet(String datasetType)
    {
	this.datasetType = datasetType;
	downloaded = false;
	metaID = null;
    }

    //download a DataSet. first download the Images, then download the subsets recursively.
    //check 'downloaded' flag and updated() to avoid downloading the same things twice
    public boolean download()
    {
	if (downloaded && updated())
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
	System.out.println("Download Dataset " + getID());
	return true;
    }

    //get the ID of this DataSet's Metadata
    public UUID getMetaID()
    {
	return metaID;
    }

    //get Metadata of this DataSet
    public Metadata getMetadata()
    {
	return ID.getMetadata(metaID);
    }

    //set the DataSet's Metadata ID
    public void setMetaID(UUID id)
    {
	metaID = id;
	store();
    }

    //store the DataSet to Infinispan
    public void store()
    {
	ID.setDataSet(getID(), this);
    }
}

