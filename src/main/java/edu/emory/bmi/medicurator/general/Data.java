package edu.emory.bmi.medicurator.general;

import edu.emory.bmi.medicurator.storage.Storage;
import java.util.UUID;

public abstract class Data
{
    private UUID dataID = UUID.randomUUID();
    public UUID getID { return dataID; }

    private UUID metaID;

    protected final String dataType;
    private boolean downloaded;

    private static final Storage storage;

    public abstract InputStream fetchFromDataSource();
    public abstract String savePath();

    public Data(String dataType)
    {
	ID.setData(dataID, this);
	this.dataType = dataType;
	downloaded = false;
	meta = null;
	metaID = 0;
    }

    public boolean download()
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
}

