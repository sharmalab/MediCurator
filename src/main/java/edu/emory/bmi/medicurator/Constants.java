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
    public static String DATA_BASE_DIR = "/tmp/medicurator/";
    public static String TCIA_API_KEY = "";
    public static String PROXY_HOST = null;
    public static int PROXY_PORT = null;
}

