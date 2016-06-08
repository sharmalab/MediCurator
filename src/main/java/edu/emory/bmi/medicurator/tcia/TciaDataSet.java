package edu.emory.bmi.medicurator.tcia;

import edu.emory.bmi.medicurator.infinispan.ID;
import edu.emory.bmi.medicurator.storage.*;
import edu.emory.bmi.medicurator.general.*;
import edu.emory.bmi.medicurator.image.*;
import java.util.UUID;
import java.util.ArrayList;
import java.util.zip.*;

public class TciaDataSet extends DataSet
{
    private static Storage storage = LocalStorage.getInstance();
    private TciaHierarchy hierarchy;
    private String keyword;
    private UUID parent;
    private UUID[] subsets;
    private UUID[] images;

    public boolean updated() { return true; }

    public TciaDataSet(TciaHierarchy hierarchy, UUID parent, Metadata meta)
    {
	super("tcia");
	meta.store();
	setMetaID(meta.getID());
	this.hierarchy = hierarchy;
	this.parent = parent;
	subsets = null;
	images = null;
	switch (hierarchy)
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

    private void makeSubsets(Metadata[] metas, TciaHierarchy subHierarchy)
    {
	subsets = new UUID[metas.length];
	System.out.println(metas.length);
	for (int i = 0; i < metas.length; ++i)
	{
	    DataSet subset = new TciaDataSet(subHierarchy, getID(), metas[i]);
	    subsets[i] = subset.getID();
	}
    }

    public UUID[] getSubsets()
    {
	try {
	    if (subsets == null && !hierarchy.equals("image"))
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
	} 
	catch (Exception e){System.out.println(e);}

	return subsets;
    }

    public UUID[] getImages()
    {
	if (images == null && hierarchy == TciaHierarchy.SERIES)
	{
	    try {
		Metadata meta = getMetadata();
		ArrayList<UUID> imgs = new ArrayList<UUID>();
		String root = "/tcia/" + meta.get("Collection") + "/" + ID.getDataSet(getParent()).getMetadata().get("PatientID") + "/" + 
		    meta.get("StudyInstanceUID") + "/" + meta.get("SeriesInstanceUID") + "/";
		ZipInputStream zip = new ZipInputStream(TciaAPI.getImage(meta.get("SeriesInstanceUID")));
		ZipEntry ze;
		while ((ze = zip.getNextEntry()) != null)
		{
		    if (ze.isDirectory()) continue;
		    String path = root + ze.getName();
		    System.out.println(path);
		    storage.saveToPath(path, zip);
		    Image img = new DicomImage(path);
		    imgs.add(img.getID());
		}
		images = (UUID[])imgs.toArray(new UUID[0]);
		store();
	    }
	    catch (Exception e) {}
	}
	return images;
    }

    public String getKeyword()
    {
	return keyword;
    }

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

