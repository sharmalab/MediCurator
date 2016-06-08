package edu.emory.bmi.medicurator.storage;

import java.io.*;

public class LocalStorage implements Storage
{
    private static String baseDir = LocalStorage.class.getResource("/").getPath();
    private static Storage storage = new LocalStorage();

    public boolean saveToPath(String path, InputStream in)
    {
	try {
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
	catch (FileNotFoundException e) {
	    System.out.println("[ERROR] File Not Found when LocalStorage saveToPath (" + baseDir + path + ")" + e);
	}
	catch (IOException e) {
	    System.out.println("[ERROR] when LocalStorage saveToPath (" + baseDir + path + ")" + e);
	}
	return false;
    }

    public InputStream loadFromPath(String path)
    { 
	InputStream in = null;
	try {
	    in = new FileInputStream(baseDir + path);
	}
	catch (FileNotFoundException e) {
	    System.out.println("[ERROR] File Not Found when LocalStorage loadFromPath (" + baseDir + path + ")" + e);
	}
	return in;
    }

    public static Storage getInstance()
    {
	return storage;
    }
}

