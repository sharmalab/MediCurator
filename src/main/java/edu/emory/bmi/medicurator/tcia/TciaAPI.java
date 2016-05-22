import java.lang.StringBuilder;
import java.util.Scanner;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;

public class TciaQuery
{
    private final String BASE_URL = "https://services.cancerimagingarchive.net/services/v3/TCIA/query/";
    private final String API_KEY = "ba449283-f680-4dc8-9df4-69545cc33f94";

    StringBuilder query;
    String method;

    public TciaQuery(String method)
    {
	query = new StringBuilder();
	this.method = method;
	String format = "format=json&";
	if (method.equals("getImage") || method.equals("getSingleImage")) 
	    format = "";
	query.append(BASE_URL + cmd + "?" + format + "api_key=" + API_KEY + "&");
    }

    public void param(String key, String, value)
    {
	query.append(key + "=" + value + "&");
    }

    public String getQuery()
    {
	return query.toString();
    }

    public String getResult()
    {
	HttpGet request = new HttpGet(getQuery());
	HttpResponse response = httpClient.execute(request);
	HttpEntity entity = response.getEntity();
	Scanner s = new Scanner(entity.getContent());
	return s.hasNext() ? s.next() : "";
    }
}

