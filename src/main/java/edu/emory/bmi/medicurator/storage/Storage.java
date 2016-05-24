package edu.emory.bmi.medicurator.storage;

import java.io.InputStream;

public interface Storage
{
    public boolean saveToPath(String path, InputStream in);
    public InputStream loadFromPath(String path);
}

