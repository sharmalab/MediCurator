package edu.emory.bmi.medicurator.tcia;

import edu.emory.bmi.medicurator.general.*;
import java.util.UUID;

public class TciaDataSource extends DataSource
{
    private UUID rootDataSet;

    public TciaDataSource()
    {
	super("tcia");
	rootDataSet = null;
    }

    public UUID getRootDataSet() 
    {
	if (rootDataSet == null)
	{
	    rootDataSet = (new TciaDataSet(TciaHierarchy.ROOT, null, null)).getID();
	}
	return rootDataSet;
    }
    public UUID retrieveDataSet(Metadata meta) throws Exception
    {
	TciaHierarchy hier = TciaHierarchy.ROOT;
	Metadata[] metas = null;
	if (meta.containsKey("SeriesInstanceUID"))
	{
	    hier = TciaHierarchy.SERIES;
	    metas = TciaAPI.getSeries(null, null, null, meta.get("SeriesInstanceUID"), null, null, null, null);
	}
	else if (meta.containsKey("StudyInstanceUID"))
	{
	    hier = TciaHierarchy.STUDY;
	    metas = TciaAPI.getPatientStudy(null, null, meta.get("StudyInstanceUID"));
	}
	else if (meta.containsKey("PatnentID"))
	{
	    hier = TciaHierarchy.PATIENT;
	    metas = TciaAPI.getPatientStudy(null, null, meta.get("PatientID"));
	}
	else if (meta.containsKey("Collection"))
	{
	    hier = TciaHierarchy.COLLECTION;
	    metas = TciaAPI.getPatient(meta.get("Collection"));
	}
	if (metas == null) return null;

	UUID dataset = getRootDataSet();
	meta = metas[0];
	if (hier == TciaHierarchy.ROOT) return dataset;
	dataset = ((TciaDataSet)ID.getDataSet(dataset)).getSubset(meta.get("Collection")); 
	if (hier == TciaHierarchy.COLLECTION) return dataset;
	dataset = ((TciaDataSet)ID.getDataSet(dataset)).getSubset(meta.get("PatientID")); 
	if (hier == TciaHierarchy.PATIENT) return dataset;
	dataset = ((TciaDataSet)ID.getDataSet(dataset)).getSubset(meta.get("StudyInstanceUID")); 
	if (hier == TciaHierarchy.STUDY) return dataset;
	dataset = ((TciaDataSet)ID.getDataSet(dataset)).getSubset(meta.get("SeriesInstanceUID")); 
	if (hier == TciaHierarchy.SERIES) return dataset;
	return null;
    }
}

