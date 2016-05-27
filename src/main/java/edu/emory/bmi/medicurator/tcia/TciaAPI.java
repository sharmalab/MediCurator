package edu.emory.bmi.medicurator.tcia;

import edu.emory.bmi.medicurator.general.Metadata;

import java.lang.StringBuilder;
import java.util.Scanner;
import java.io.InputStream;


public class TciaAPI
{
    public static Metadata[] getCollectionValues();
    public static Metadata[] getModalityValues(String collection, String bodyPartExamined);
    public static Metadata[] getBodyPartValues(String collection, String modality);
    public static Metadata[] getManufacturerValues(String collection, String modality);
    public static Metadata[] getPatient(String collection);
    public static Metadata[] getPatientsByModality(String collection, String modality);
    public static Metadata[] getPatientStudy(String collection, String patiendID, String stuyInstanceUID);
    public static Metadata[] getSeries(String collection, String studyInstanceUID, String patientID, String seriesInstanceUID, String modality, String bodyPartExamined, String manufacturerModelName, String manufacturer);
    public static Metadata[] getSeriesSize(String seriesInstanceUID);
    public static InputStream getImage(String seriesInstanceUID);
    public static Metadata[] newPatientsInCollection(String date, String collection);
    public static Metadata[] newStudiesInPatientCollection(String date, String collection, String patiendID);
    public static Metadata[] getSOPInstanceUIDs(String seriesInstanceUID);
    public static InputStream getSingleImage(String seriesInstanceUID, String SOPInstanceUID);
}

