package edu.emory.bmi.medicurator.storage;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

public class LocalStorage implements Storage
{
    private String baseDir = "/tmp/medicurator";

    public boolean saveToPath(String path, InputStream in) throws Exception
    {
	File file = new File(baseDir + path);
	File parent = new File(file.getParent());
	parent.mkdirs();
	FileOutputStream out = new FileOutputStream(file);
	byte[] buffer = new byte[65536];
	int len;
	while ((len = in.read(buffer)) != -1)
	{
	    out.write(buffer, 0, len);
	}
	out.close();
	return true;
    }

    public InputStream loadFromPath(String path) throws Exception
    { 
	return new FileInputStream(path);
    }
}
