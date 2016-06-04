package edu.emory.bmi.medicurator.tcia;

import edu.emory.bmi.medicurator.infinispan.ID;
import edu.emory.bmi.medicurator.general.*;
import java.util.UUID;

public class TciaDataSet extends DataSet
{
    private TciaHierarchy hierarchy;
    private String keyword;
    private UUID parent;
    private UUID[] subsets;
    private UUID[] data;

    public boolean updated() { return true; }

    public TciaDataSet(TciaHierarchy hierarchy, UUID parent, Metadata meta)
    {
	super("tcia");
	ID.setMetadata(meta.getID(), meta);
	setMetaID(meta.getID());
	this.hierarchy = hierarchy;
	this.parent = parent;
	subsets = null;
	data = null;
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
	    case IMAGE:
		keyword = meta.get("SOPInstanceUID"); 
		break;
	}
	updateInf();
    }

    public UUID getParent()
    {
	return parent;
    }

    private void makeSubsets(Metadata[] metas, TciaHierarchy subHierarchy)
    {
	subsets = new UUID[metas.length];
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
		    case SERIES:
			metas = TciaAPI.getSOPInstanceUIDs(meta.get("SeriesInstanceUID"));
			subsets = new UUID[metas.length];
			for (int i = 0; i < metas.length; ++i)
			{
			    Metadata submeta = new Metadata(meta);	    
			    submeta.put("SOPInstanceUID", metas[i].get("sop_instance_uid"));
			    DataSet subset = new TciaDataSet(TciaHierarchy.IMAGE, getID(), submeta);
			    subsets[i] = subset.getID();
			}
			break;
		}
		updateInf();
	    }
	} catch (Exception e){}


	return subsets;
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

    public UUID[] getData()
    {
	if (data == null && (hierarchy == TciaHierarchy.SERIES || hierarchy == TciaHierarchy.IMAGE))
	{
	    data = new UUID[1];
	    data[0] = (new TciaData(hierarchy, getID())).getID();
	    updateInf();
	}
	return data;
    }

    public String getKeyword()
    {
	return keyword;
    }
}

