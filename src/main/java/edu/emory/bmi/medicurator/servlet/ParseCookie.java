package edu.emory.bmi.medicurator.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;

public class ParseCookie
{
    public static HashMap<String, String> parse(Cookie[] cookies)
    {
	HashMap<String, String> cookie = new HashMap<String, String>();
	if (cookies != null)
	{
	    for (int i = 0; i < cookies.length; i++)
	    {
		cookie.put(cookies[i].getName(), cookies[i].getValue());
	    }
	}
	return cookie;
    }
}

