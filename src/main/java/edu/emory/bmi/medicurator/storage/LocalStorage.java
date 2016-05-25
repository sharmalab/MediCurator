package edu.emory.bmi.medicurator.storage;

import java.io.InputStream;

public class LocalStorage implements Storage
{
    public boolean saveToPath(String path, InputStream in) { return false; }
    public InputStream loadFromPath(String path) { return null; }
}
