package edu.emory.bmi.medicurator.storage;

import java.io.InputStream;
import edu.emory.bmi.medicurator.Constants;

public class GetStorage
{
    public static Storage get()
    {
	if (Constants.STORAGE.equals("local"))
	    return new LocalStorage();
	if (Constants.STORAGE.equals("hdfs"))
	    return new HdfsStorage();
	return null;
    }
}
