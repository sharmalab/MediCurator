package edu.emory.bmi.medicurator.image;

import edu.emory.bmi.medicurator.storage.LocalStorage;
import edu.emory.bmi.medicurator.general.*;
import edu.emory.bmi.medicurator.infinispan.ID;

// employ the third party libiary
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.Tag;

import java.io.InputStream;
import java.util.*;

public class DicomImage extends Image
{
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
		store();
		return meta;
	    }
	    else
	    {
		return ID.getMetadata(metaID);
	    }
	} catch (Exception e) {System.out.println(e);}
	return null;
    }

    //employ the third party library to get the rawimage
    public byte[] getRawImage()
    {
	try {
	    DicomInputStream stream = new DicomInputStream(getImage());
	    DicomObject dcm = stream.readDicomObject();
	    return dcm.getBytes(Tag.PixelData);
	}
	catch (Exception e) {System.out.println(e);}
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
	    return storage.loadFromPath(path);
	}
	catch (Exception e) {System.out.println(e);}
	return null;
    }
}
