package edu.emory.bmi.medicurator.storage;

import java.io.InputStream;

/*
 * local storage driver interface
 * includs save and load methods
 */
public interface Storage
{
    boolean saveToPath(String path, InputStream in);
    InputStream loadFromPath(String path);
}
