package edu.emory.bmi.medicurator.general;

import java.util.UUID;

public abstract class Data
{
    private UUID dataID = UUID.randomUUID();
    public UUID getID { return dataID; }

    private Metadata meta;

    public abstract Metadata getMetadata();
    public abstract byte[] getData();

    boolean downloaded;

    download()
    load()
}

