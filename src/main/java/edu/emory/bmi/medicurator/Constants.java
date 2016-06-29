package edu.emory.bmi.medicurator;

import java.util.ArrayList;
import edu.emory.bmi.medicurator.general.*;
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
    public static String DATA_BASE_DIR = Constants.class.getResource("/").getPath();
    public static String TCIA_API_KEY = "ba449283-f680-4dc8-9df4-69545cc33f94";
    public static String PROXY_HOST = "proxy1.megvii-inc.com";
    public static Integer PROXY_PORT = 25;
    public static String PROXY_USERNAME= "megvii";
    public static String PROXY_PASSWORD = "face++";
    public static ArrayList<DataSource> DATA_SOURCES = new ArrayList<DataSource>();

    static
    {
	DATA_SOURCES.add(new TciaDataSource());
    }
}

