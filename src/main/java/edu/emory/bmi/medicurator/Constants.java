package edu.emory.bmi.medicurator;

import java.util.ArrayList;
import edu.emory.bmi.medicurator.general.*;
import edu.emory.bmi.medicurator.infinispan.*;
import edu.emory.bmi.medicurator.tcia.*;

/*
 * Constant values and settings in MediCurator.
 * Contains:
 *     DATA_BASE_DIR : The prefix of data path used when store data in local disk.
 *     TCIA_API_KEY : The api_key used in TCIA data source.
 *     PROXY_HOST, PROXY_PORT : Proxy used when sending query to data source. 
 *                              If don't use proxy, set null.
 */

public class Constants
{
    public static String STORAGE = "hdfs"; // or "local"
    public static String LOCAL_BASEDIR = Constants.class.getResource("/").getPath();
    public static String HDFS_URI = null;//"hdfs://162.105.203.138:9000/";
    public static String HDFS_BASEDIR = null;//"/user/chenyr/medicurator/";

    public static String TCIA_API_KEY = "ba449283-f680-4dc8-9df4-69545cc33f94";
    public static String PROXY_HOST = null;//"proxy1.megvii-inc.com";
    public static Integer PROXY_PORT = null;//25;
    public static String PROXY_USERNAME= null;//"megvii";
    public static String PROXY_PASSWORD = null;//"face++";
    public static ArrayList<DataSource> DATA_SOURCES = new ArrayList<DataSource>();

    static
    {
	if (ID.getDataSourceID("tcia") == null)
	{
	    DataSource tcia = new TciaDataSource();
	    ID.setDataSourceID("tcia", tcia.getID());
	}
	DATA_SOURCES.add(ID.getDataSource(ID.getDataSourceID("tcia")));
    }
}

