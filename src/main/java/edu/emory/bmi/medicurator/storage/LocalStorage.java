package edu.emory.bmi.medicurator.storage;

import edu.emory.bmi.medicurator.Constants;
import java.io.*;


/*
 * Implementation of local disk storage.
 * Specify the baseDir then everything will save to [baseDir]+[relative path]
 */
public class LocalStorage implements Storage, Serializable
{
    //get path prefix from Constants
    private  String baseDir = Constants.DATA_BASE_DIR;

    //save data from InputStream to a relative path
    //requried parent directories will be created if not exists
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

    //get the InputStream of a file with specified relative path
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
}

