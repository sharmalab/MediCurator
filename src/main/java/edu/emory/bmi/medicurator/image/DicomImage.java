package edu.emory.bmi.medicurator.image;

import edu.emory.bmi.medicurator.storage.HdfsStorage;
import edu.emory.bmi.medicurator.general.*;
import edu.emory.bmi.medicurator.infinispan.ID;

import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.Tag;

import java.io.*;
import java.util.*;

/*
 * Implementation of DICOM image type
 * Use dcm4che library to parse the image data
 */
public class DicomImage extends Image
{
    //parse the Metadata of a DICOM image
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

    //get the raw image data as a byte array
    //return the bytes from DICOM's Pixel Data
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

    //create a new DicomImage with its path
    public DicomImage(String path)
    {
	super(path);
	ID.setImage(getID(), this);
    }

    //read image data from its path
    private InputStream getImage()
    {
	return storage.loadFromPath(path);
    }
}
