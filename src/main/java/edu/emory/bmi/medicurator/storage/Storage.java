package edu.emory.bmi.medicurator.storage;

import java.io.InputStream;

public interface Storage
{
    boolean saveToPath(String path, InputStream in);
    InputStream loadFromPath(String path);
}

