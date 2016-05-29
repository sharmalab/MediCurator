package edu.emory.bmi.medicurator.tcia;

import edu.emory.bmi.medicurator.general.Metadata;

import org.json.JSONObject;
import org.json.JSONArray;
import java.lang.StringBuilder;
import java.util.Scanner;
import java.io.InputStream;
import java.util.Iterator;


public class TciaAPI
{
    public static Metadata[] parseJSON(String json)
    {
	JSONArray arr = new JSONArray(json);
	if (arr.length() == 0) return null;
	Metadata[] metas = new Metadata[arr.length()];
	for (int i = 0; i < arr.length(); ++i)
	{
	    JSONObject obj = arr.getJSONObject(i);
	    metas[i] = new Metadata();
	    for (Iterator iter = obj.keys(); iter.hasNext(); )
	    {
		String key = (String)iter.next();
		metas[i].put(key, obj.getString(key));
	    }
	}
	return metas;
    }
    public static Metadata[] getCollectionValues() throws Exception
    {
	TciaQuery query = new TciaQuery("getCollectionValues");
	String result = query.getResult();
	return parseJSON(result);
    }
    public static Metadata[] getModalityValues(String collection, String bodyPartExamined) throws Exception
    {
	TciaQuery query = new TciaQuery("getModalityValues");
	query.param("collection", collection);
	query.param("bodyPartExamined", bodyPartExamined);
	String result = query.getResult();
	return parseJSON(result);
    }
    public static Metadata[] getBodyPartValues(String collection, String modality) throws Exception
    {
	TciaQuery query = new TciaQuery("getBodyPartValues");
	query.param("collection", collection);
	query.param("modality", modality);
	String result = query.getResult();
	return parseJSON(result);
    }
    public static Metadata[] getManufacturerValues(String collection, String modality) throws Exception
    {
	TciaQuery query = new TciaQuery("getManufacturerValues");
	query.param("collection", collection);
	query.param("modality", modality);
	String result = query.getResult();
	return parseJSON(result);
    }
    public static Metadata[] getPatient(String collection) throws Exception
    {
	TciaQuery query = new TciaQuery("getPatient");
	query.param("collection", collection);
	String result = query.getResult();
	return parseJSON(result);
    }
    public static Metadata[] getPatientsByModality(String collection, String modality) throws Exception
    {
	TciaQuery query = new TciaQuery("getPatientByModality");
	query.param("collection", collection);
	query.param("modality", modality);
	String result = query.getResult();
	return parseJSON(result);
    }
    public static Metadata[] getPatientStudy(String collection, String patientID, String studyInstanceUID) throws Exception
    {
	TciaQuery query = new TciaQuery("getPatientStudy");
	query.param("collection", collection);
	query.param("patientID", patientID);
	query.param("studyInstanceUID", studyInstanceUID);
	String result = query.getResult();
	return parseJSON(result);
    }
    public static Metadata[] getSeries(String collection, String patientID, String studyInstanceUID, String seriesInstanceUID , String modality, String bodyPartExamined, String manufacturerModelName, String manufacturer) throws Exception
    {
	TciaQuery query = new TciaQuery("getSeries");
	query.param("collection", collection);
	query.param("patientID", patientID);
	query.param("studyInstanceUID", studyInstanceUID);
	query.param("seriesInstanceUID", seriesInstanceUID);
	query.param("modality", modality);
	query.param("bodyPartExamined", bodyPartExamined);
	query.param("manufacturerModelName", manufacturerModelName);
	query.param("manufacturer", manufacturer);
	String result = query.getResult();
	return parseJSON(result);
    }
    public static Metadata[] getSeriesSize(String seriesInstanceUID) throws Exception
    {
	TciaQuery query = new TciaQuery("getSeriesSize");
	query.param("seriesInstanceUID", seriesInstanceUID);
	String result = query.getResult();
	return parseJSON(result);
    }
    public static InputStream getImage(String seriesInstanceUID) throws Exception
    {
	TciaQuery query = new TciaQuery("getImage");
	query.param("seriesInstanceUID", seriesInstanceUID);
	return query.getRawResult();
    }
    public static Metadata[] newPatientsInCollection(String date, String collection) throws Exception
    {
	TciaQuery query = new TciaQuery("newPatientsInCollection");
	query.param("date", date);
	query.param("collection", collection);
	String result = query.getResult();
	return parseJSON(result);
    }
    public static Metadata[] newStudiesInPatientCollection(String date, String collection, String patientID) throws Exception
    {
	TciaQuery query = new TciaQuery("newStudiesInPatientCollection");
	query.param("date", date);
	query.param("collection", collection);
	query.param("patientID", patientID);
	String result = query.getResult();
	return parseJSON(result);
    }
    public static Metadata[] getSOPInstanceUIDs(String seriesInstanceUID) throws Exception
    {
	TciaQuery query = new TciaQuery("getSOPInstanceUIDs");
	query.param("seriesInstanceUID", seriesInstanceUID);
	String result = query.getResult();
	return parseJSON(result);
    }
    public static InputStream getSingleImage(String seriesInstanceUID, String SOPInstanceUID) throws Exception
    {
	TciaQuery query = new TciaQuery("getSingleImage");
	query.param("seriesInstanceUID", seriesInstanceUID);
	query.param("SOPInstanceUID", SOPInstanceUID);
	return query.getRawResult();
    }
}

