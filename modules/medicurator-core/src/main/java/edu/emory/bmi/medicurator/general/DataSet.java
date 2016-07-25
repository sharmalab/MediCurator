package edu.emory.bmi.medicurator.general;

import edu.emory.bmi.medicurator.image.Image;
import edu.emory.bmi.medicurator.infinispan.ID;
import java.util.UUID;
import java.util.zip.*;
import java.io.*;

/*
 * A DataSet is a node of a Tree.
 * Each DataSet has a parent DataSet and some children DataSets.
 * In a DataSet there might stores several Images.
 * DataSet can be download. When downloads a DataSet, MediCurator will download the Images
 * of the DataSet, then download all its children DataSets recursively.
 * A DataSet has a flag 'downloaded'. One DataSet won't be downloaded twice.
 * Also see DataSource for more information.
 */
public abstract class DataSet implements Serializable
{
    //the unique ID used to retrieve the DataSet inside MediCurator
    private UUID dataSetID = UUID.randomUUID();
    public UUID getID() { return dataSetID; }

    //the ID of the DataSet's Metadata 
    private UUID metaID;

    //the type of the DataSet, similar to DataSource's type
    protected String datasetType;

    //recored whether the DataSet has been downloaded
    public boolean downloaded;
    public  boolean self_downloaded;

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
	self_downloaded = false;
	metaID = null;
    }

    private void update_downloaded()
    {
	if (self_downloaded == false)
	{
	    downloaded = false;
	}
	else
	{
	    downloaded = true;
	    if (getSubsets() != null)
	    {
		for (UUID id : getSubsets())
		{
		    if (!ID.getDataSet(id).downloaded)
		    {
			downloaded = false;
			break;
		    }
		}
	    }
	}
	store();
    }

    //download a DataSet. first download the Images, then download the subsets recursively.
    //check 'downloaded' flag and updated() to avoid downloading the same things twice
    public boolean download()
    {
	if (downloaded && updated())
	{
	    System.out.println("Dataset " + getID() + " already downloaded");
	    return true;
	}
	try {
	    downloaded = true;
	    if (self_download() == false)
	    {
		downloaded = false;
		return false;
	    }
	    if (getSubsets() != null)
	    {
		for (UUID id : getSubsets())
		{
		    if (!ID.getDataSet(id).download())
		    {
			downloaded = false;
			return false;
		    }
		}
	    }
	    store();
	}
	catch (Exception e) {
	    downloaded = false;
	    System.out.println("[ERROR] when downloading dataset " + getID() + " -- " + e);
	    return false;
	}
	System.out.println("Download Dataset " + getID());
	return true;
    }

    public boolean self_download()
    {
	try {
	    if (self_downloaded == true)
		return true;
	    self_downloaded = true;
	    getImages(); 
	    UUID id = getID();
	    while (id != null)
	    {
		DataSet ds = ID.getDataSet(id);
		ds.update_downloaded();
		if (ds.downloaded == false)
		    break;
		id = ds.getParent();
	    }
	    return true;
	}
	catch (Exception e) {
	    self_downloaded = false;
	    System.out.println("[ERROR] when downloading Images of dataset " + getID() + " -- " + e);
	    return false;
	}
    }

    private boolean _delete()
    {
	if (downloaded == true)
	{
	    self_delete();
	    UUID[] subsets = getSubsets();
	    if (subsets != null)
	    {
		for (UUID s : subsets)
		{
		    ID.getDataSet(s)._delete();
		}
	    }
	    store();
	}
	return true;
    }

    public boolean delete()
    {
	if (downloaded == true)
	{
	    _delete();
	    store();
	    UUID par = getParent();
	    while (par != null)
	    {
		DataSet parent = ID.getDataSet(par);
		if (parent.downloaded == false) 
		    break;
		parent.downloaded = false;
		parent.store();
		par = parent.getParent();
	    }
	}
	return true;
    }

    public boolean self_delete()
    {
	if (self_downloaded == true)
	{
	    self_downloaded = false;
	    downloaded = false;
	    store();
	    UUID par = getParent();
	    while (par != null)
	    {
		DataSet parent = ID.getDataSet(par);
		if (parent.downloaded == false) break;
		parent.downloaded = false;
		parent.store();
		par = parent.getParent();
	    }
	}
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

