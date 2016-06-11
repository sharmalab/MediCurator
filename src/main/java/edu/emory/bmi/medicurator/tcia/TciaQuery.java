package edu.emory.bmi.medicurator.tcia;

import java.lang.StringBuilder;
import java.util.Scanner;

import edu.emory.bmi.medicurator.Constants;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.*;
import javax.net.ssl.*;
import java.security.cert.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.HttpEntity;
import java.io.InputStream;

/*
 * Build query of TCIA RESTful API, send query and get result
 * User needs to fill the TCIA_API_KEY in Constants before usage
 */
public class TciaQuery
{
    private static final String BASE_URL = "https://services.cancerimagingarchive.net/services/v3/TCIA/query/";
    private static final String API_KEY = Constants.TCIA_API_KEY;

    StringBuilder query;
    String method;

    //create a new query with specified method(getPatient, getSeries, getImage...)
    public TciaQuery(String method)
    {
	query = new StringBuilder();
	this.method = method;
	String format = "format=json&";
	if (method.equals("getImage") || method.equals("getSingleImage")) 
	    format = "";
	query.append(BASE_URL + method + "?" + format + "api_key=" + API_KEY + "&");
    }

    //add a parameter
    public void param(String key, String value)
    {
	if (value == null) return;
	query.append(key + "=" + value + "&");
    }

    //get the query URL
    public String getQuery()
    {
	return query.toString();
    }

    //create a insecure ssl client
    private HttpClient getInsecureClient() throws Exception
    {
	SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
		public boolean isTrusted(X509Certificate[] chain,
		    String authType) throws CertificateException {
		return true;
		}
		}).build();
	SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
	return HttpClientBuilder.create().setSSLSocketFactory(sslsf).build();

    }

    //send query and get text result
    public String getResult()
    {
	try{
	    HttpGet request = new HttpGet(getQuery());
	    if (Constants.PROXY_HOST != null && Constants.PROXY_PORT != null)
	    {
		request.setConfig(RequestConfig.custom().setProxy(new HttpHost(Constants.PROXY_HOST, Constants.PROXY_PORT, "http")).build());
	    }
	    HttpClient client = getInsecureClient();
	    HttpResponse response = client.execute(request);
	    HttpEntity entity = response.getEntity();
	    Scanner s = new Scanner(entity.getContent());
	    return s.hasNextLine() ? s.nextLine() : "";
	}
	catch (Exception e) { 
	    System.out.println("[ERROR] when excute TCIA TESTful API " + getQuery() + " -- " + e);
	    return "";
	}
    }

    //send query and get raw data result
    public InputStream getRawResult()
    {
	try{
	    HttpGet request = new HttpGet(getQuery());
	    if (Constants.PROXY_HOST != null && Constants.PROXY_PORT != null)
	    {
		request.setConfig(RequestConfig.custom().setProxy(new HttpHost(Constants.PROXY_HOST, Constants.PROXY_PORT, "http")).build());
	    }
	    HttpClient client = getInsecureClient();
	    HttpResponse response = client.execute(request);
	    HttpEntity entity = response.getEntity();
	    return entity.getContent();
	}
	catch (Exception e) {
	    System.out.println("[ERROR] when excute TCIA TESTful API " + getQuery() + " -- " + e);
	    return null;
	}
    }
}

