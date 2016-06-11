package edu.emory.bmi.medicurator;

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
    public static String PROXY_HOST = null;
    public static Integer PROXY_PORT = null;
}

