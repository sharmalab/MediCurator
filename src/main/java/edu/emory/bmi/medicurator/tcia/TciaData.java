package edu.emory.bmi.medicurator.tcia;

import edu.emory.bmi.medicurator.infinispan.ID;
import edu.emory.bmi.medicurator.general.*;
import java.io.InputStream;
import java.util.UUID;

public class TciaData extends Data
{
    private TciaHierarchy type;
    private String path;
    private UUID owner;

    public boolean updated() { return true; }

    public TciaData(TciaHierarchy type, UUID owner)
    {
	super("tcia");
	this.type = type;
	Metadata meta = ID.getDataSet(owner).getMetadata();
	this.path = "/tcia/" + meta.get("Collection") + "/" + meta.get("PatientID") + "/" + meta.get("StudyInstanceUID");
	if (type == TciaHierarchy.SERIES)
	{
	    path += "/" + meta.get("SeriesInstanceUID") + ".zip";
	}
	else
	{
	    path += "/" + meta.get("SeriesInstanceUID") + meta.get("SOPInstanceUID") + ".dcm";
	}
	this.owner = owner;
	setMetaID(ID.getDataSet(owner).getMetaID());
    }

    public String savePath() 
    {
	return path;
    }

    public InputStream fetchFromDataSource() throws Exception
    { 
	Metadata meta = getMetadata();
	if (type == TciaHierarchy.SERIES)
	{
	    return TciaAPI.getImage(meta.get("SeriesInstanceUID"));
	}
	else
	{
	    return TciaAPI.getSingleImage(meta.get("SeriesInstanceUID"), meta.get("SOPInstanceUID"));
	}
    }
}

