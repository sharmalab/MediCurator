package edu.emory.bmi.medicurator.general;

import edu.emory.bmi.medicurator.storage.Storage;
import edu.emory.bmi.medicurator.storage.LocalStorage;
import java.io.InputStream;
import java.util.UUID;

public abstract class Data
{
    private UUID dataID = UUID.randomUUID();
    public UUID getID() { return dataID; }

    private UUID metaID;

    protected final String dataType;
    private boolean downloaded;

    private static Storage storage;
    static
    {
	storage = new LocalStorage();
    }

    public abstract InputStream fetchFromDataSource() throws Exception;
    public abstract String savePath();
    public abstract boolean updated();

    public Data(String dataType)
    {
	ID.setData(dataID, this);
	this.dataType = dataType;
	downloaded = false;
	metaID = null;
    }

    public boolean download() throws Exception
    {
	if (downloaded) return true;
	storage.saveToPath(savePath(), fetchFromDataSource());
	downloaded = true;
	return true;
    }

    public UUID getMetaID()
    {
	return metaID;
    }

    public boolean setMetaID(UUID metaID)
    {
	this.metaID = metaID;
	return true;
    }

    public Metadata getMetadata()
    {
	return ID.getMetadata(metaID);
    }

    public boolean setMetadata(Metadata meta)
    {
	return setMetaID(meta.getID());
    }

    public boolean updateInf()
    {
	return ID.setData(getID(), this);
    }
}

