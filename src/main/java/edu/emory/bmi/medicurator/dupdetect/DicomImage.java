package edu.emory.bmi.medicurator.dupdetect;

import edu.emory.bmi.medicurator.storage.LocalStorage;
import edu.emory.bmi.medicurator.general.*;

import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.Tag;

import java.io.InputStream;
import java.util.UUID;

public class DicomImage extends Image;
{
    private UUID imageID = UUID.randomUUID();
    public UUID getID() { return imageID; }

    private Metadata meta;

    public  Metadata getMetadata()
    {
	if (meta == null)
	{
	    DicomInputStream stream = new DicomInputStream(getImage());
	    DicomObject dcm = stream.readDicomObject();
	    meta = new Metadata();
	    for (Iterator iter = img.datasetIterator(); iter.hasNext(); )
	    {
		DicomElement e = (DicomElement)iter.next();
		if (e.tag() == Tag.PixelData) continue;
		meta.put(Integer.toHexString() + ":" + dcm.nameOf(e.tag()), dcm.getString(e.tag()));
	    }
	    updateInf();
	}
	else
	{
	    return meta;
	}
    }

    public byte[] getRawImage()
    {
	DicomInputStream stream = new DicomInputStream(getImage());
	DicomObject dcm = stream.readDicomObject();
	return dcm.getBytes(Tag.pixelData);
    }

    public DicomImage(String path)
    {
	super(path);
	meta = null;
	ID.setImage(getID(), this);
    }

    private ImputStream getImage()
    {
	return LocalStorage.loadFromPath(path);
    }
}

