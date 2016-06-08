package edu.emory.bmi.medicurator.image;

import edu.emory.bmi.medicurator.storage.LocalStorage;
import edu.emory.bmi.medicurator.general.*;
import edu.emory.bmi.medicurator.infinispan.ID;

import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.Tag;

import java.io.InputStream;
import java.io.IOException;
import java.util.*;

public class DicomImage extends Image
{
    public Metadata getMetadata()
    {
	if (metaID == null)
	{
	    Metadata meta = new Metadata();
	    try {
		DicomInputStream stream = new DicomInputStream(getImage());
		DicomObject dcm = stream.readDicomObject();
		for (Iterator iter = dcm.datasetIterator(); iter.hasNext(); )
		{
		    DicomElement e = (DicomElement)iter.next();
		    if (e.tag() == Tag.PixelData) continue;
		    meta.put(Integer.toHexString(e.tag()) + ":" + dcm.nameOf(e.tag()), dcm.getString(e.tag()));
		}
	    }
	    catch (IOException e) {
		System.out.println("[ERROR] when get Metadata from dicom " + path + " -- " + e);
	    }
	    ID.setMetadata(meta.getID(), meta);
	    metaID = meta.getID();
	    store();
	    return meta;
	}
	return ID.getMetadata(metaID);
    }

    public byte[] getRawImage()
    {
	InputStream in = getImage();
	if (in != null)
	{
	    try {
		DicomInputStream stream = new DicomInputStream(in);
		DicomObject dcm = stream.readDicomObject();
		return dcm.getBytes(Tag.PixelData);
	    }
	    catch (IOException e) {
		System.out.println("[ERROR] when get RawImage from dicom " + path + " -- " + e);
	    }
	}
	return new byte[0];
    }

    public DicomImage(String path)
    {
	super(path);
	ID.setImage(getID(), this);
    }

    private InputStream getImage()
    {
	return storage.loadFromPath(path);
    }
}
