package edu.emory.bmi.medicurator.tcia;

/*
 * The hierarchy of TCIA dat
 * root -> collection -> patient -> study -> series -> image
 */
public enum TciaHierarchy
{
    ROOT,
    COLLECTION,
    PATIENT,
    STUDY,
    SERIES,
    IMAGE
}

