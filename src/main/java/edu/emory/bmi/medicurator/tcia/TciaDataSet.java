package edu.emory.bmi.medicurator.tcia;

import edu.emory.bmi.medicurator.infinispan.ID;
import edu.emory.bmi.medicurator.storage.*;
import edu.emory.bmi.medicurator.general.*;
import edu.emory.bmi.medicurator.image.*;
import java.util.UUID;
import java.util.ArrayList;
import java.util.zip.*;

/*
 * Implementation of TCIA DataSet
 * Each hierarchy has a keyword, such as PatientID of patient or StudyInstanceUID of study
 * Series contains Images, other hierarchies don't have Image
 */
public class TciaDataSet extends DataSet
{
    private static Storage storage = LocalStorage.getInstance();
    private TciaHierarchy hierarchy;
    private String keyword;
    private UUID parent;
    private UUID[] subsets;
    private UUID[] images;

    //TODO: check if the downloaded DataSet out of date
    public boolean updated() { return true; }

    //create a TciaDataSet with specified Metadata
    public TciaDataSet(TciaHierarchy hierarchy, UUID parent, Metadata meta)
    {
	super("tcia");
	meta.store();
	setMetaID(meta.getID());
	this.hierarchy = hierarchy;
	this.parent = parent;
	subsets = null;
	images = null;
	switch (hierarchy)   // get keyword
	{
	    case ROOT:
		keyword = "";
		break;
	    case COLLECTION:
		keyword = meta.get("Collection");
		break;
	    case PATIENT:
		keyword = meta.get("PatientID");
		break;
	    case STUDY:
		keyword = meta.get("StudyInstanceUID");
		break;
	    case SERIES:
		keyword = meta.get("SeriesInstanceUID");
		break;
	}
	store();
    }

    public UUID getParent()
    {
	return parent;
    }

    // create this DataSet's subsets
    private void makeSubsets(Metadata[] metas, TciaHierarchy subHierarchy)
    {
	if (metas.length == 0) return;
	subsets = new UUID[metas.length];
	for (int i = 0; i < metas.length; ++i)
	{
	    DataSet subset = new TciaDataSet(subHierarchy, getID(), metas[i]);
	    subsets[i] = subset.getID();
	}
    }

    // get ID array of subsets
    public UUID[] getSubsets()
    {
	if (subsets == null && hierarchy != TciaHierarchy.SERIES)
	{
	    Metadata meta = getMetadata();
	    Metadata[] metas;
	    switch (hierarchy)
	    {
		case ROOT:
		    metas = TciaAPI.getCollectionValues();
		    makeSubsets(metas, TciaHierarchy.COLLECTION);
		    break;
		case COLLECTION:
		    metas = TciaAPI.getPatient(meta.get("Collection"));
		    makeSubsets(metas, TciaHierarchy.PATIENT);
		    break;
		case PATIENT:
		    metas = TciaAPI.getPatientStudy(meta.get("Collection"), meta.get("PatientID"), null);
		    makeSubsets(metas, TciaHierarchy.STUDY);
		    break;
		case STUDY:
		    metas = TciaAPI.getSeries(meta.get("Collection"), meta.get("PatientID"), meta.get("StudyInstanceUID"), null, null, null, null, null);
		    makeSubsets(metas, TciaHierarchy.SERIES);
		    break;
	    }
	    store();
	}
	return subsets;
    }

    //if this is a Series DataSet, download the images of the Series
    //the downloaded images are compressed with zip, unzip them and store
    public UUID[] getImages()
    {
	if (images == null)	
	{
	    if (hierarchy != TciaHierarchy.SERIES)
	    {
		images = new UUID[0];
	    }
	    else
	    {
		Metadata meta = getMetadata();
		ArrayList<UUID> imgs = new ArrayList<UUID>();
		try {
		    String root = "/tcia/" + meta.get("Collection") + "/" + ID.getDataSet(getParent()).getMetadata().get("PatientID")
				    + "/" + meta.get("StudyInstanceUID") + "/" + meta.get("SeriesInstanceUID") + "/";
		    ZipInputStream zip = new ZipInputStream(TciaAPI.getImage(meta.get("SeriesInstanceUID")));
		    ZipEntry ze;
		    while ((ze = zip.getNextEntry()) != null)
		    {
			if (ze.isDirectory()) continue;
			String path = root + ze.getName();
			storage.saveToPath(path, zip);
			System.out.println(ze.getName());
			Image img = new DicomImage(path);
			imgs.add(img.getID());
		    }
		    images = (UUID[])imgs.toArray(new UUID[0]);
		}
		catch (Exception e) {
		    System.out.println("[ERROR] when TCIA dataset getImages " + getID() + " -- " + e);
		    images = new UUID[0];
		}
	    }
	    store();
	}
	return images;
    }

    public String getKeyword()
    {
	return keyword;
    }

    //get a subset with specified keyword
    public UUID getSubset(String keyword)
    {
	for (UUID u : getSubsets())
	{
	    if (((TciaDataSet)ID.getDataSet(u)).getKeyword().equals(keyword))
	    {
		return u;
	    }
	}
	return null;
    }
}

