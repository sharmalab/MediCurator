package edu.emory.bmi.medicurator.dupdetect;

import edu.emory.bmi.medicurator.storage.Storage;
import edu.emory.bmi.medicurator.storage.LocalStorage;
import java.io.InputStream;
import java.util.UUID;

public abstract class Image
{
    private UUID imageID = UUID.randomUUID();
    public UUID getID() { return imageID; }

    private String path;

    public abstract Metadata getMetadata();
    public abstract byte[] getRawImage();

    public Image(String path)
    {
	this.path = path;
    }

    private boolean updateInf()
    {
	return ID.setImage(getID(), this);
    }
}

