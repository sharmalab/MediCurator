package edu.emory.bmi.medicurator.image;

import edu.emory.bmi.medicurator.storage.LocalStorage;
import edu.emory.bmi.medicurator.general.*;
import edu.emory.bmi.medicurator.infinispan.ID;

import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.Tag;

import java.io.InputStream;
import java.util.*;

public class DicomImage extends Image
{
    private UUID imageID = UUID.randomUUID();
    public UUID getID() { return imageID; }

    public Metadata getMetadata()
    {
	try {
	if (metaID == null)
	{
	    DicomInputStream stream = new DicomInputStream(getImage());
	    DicomObject dcm = stream.readDicomObject();
	    Metadata meta = new Metadata();
	    for (Iterator iter = dcm.datasetIterator(); iter.hasNext(); )
	    {
		DicomElement e = (DicomElement)iter.next();
		if (e.tag() == Tag.PixelData) continue;
		meta.put(Integer.toHexString(e.tag()) + ":" + dcm.nameOf(e.tag()), dcm.getString(e.tag()));
	    }
	    ID.setMetadata(meta.getID(), meta);
	    metaID = meta.getID();
	    updateInf();
	}
	else
	{
	    return ID.getMetadata(metaID);
	}
	} catch (Exception e) {}
	return null;
    }

    public byte[] getRawImage()
    {
	try {
	DicomInputStream stream = new DicomInputStream(getImage());
	DicomObject dcm = stream.readDicomObject();
	return dcm.getBytes(Tag.PixelData);
	}
	catch (Exception e) {}
	return null;
    }

    public DicomImage(String path)
    {
	super(path);
	ID.setImage(getID(), this);
    }

    private InputStream getImage()
    {
	try {
	    return (new LocalStorage()).loadFromPath(path);
	}
	catch (Exception e) {}
	return null;
    }
}

