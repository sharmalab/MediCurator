package edu.emory.bmi.medicurator.general;

import edu.emory.bmi.medicurator.infinispan.ID;
import java.util.*;
import java.io.*;

/*
 * MediCurator can manage data of heterogeneous data sources
 * The hypothesis is that the data in each data source is managed as a tree hierarchy
 * Each tree node is a DataSet and DataSource keeps the root DataSet of the tree.
 */
public abstract class DataSource implements Serializable
{
    //the unique ID used to retrieve the DataSource inside MediCurator
    private UUID dataSourceID = UUID.randomUUID();
    public UUID getID() { return dataSourceID; }

    //each DataSource has a type, such as TCIA...
    protected final String dataSourceType;

    //get the root DataSet
    public abstract UUID getRootDataSet();

    //create a new DataSource with its type
    public DataSource(String dataSourceType)
    {
	this.dataSourceType = dataSourceType;
    }

    //get the DataSource's type
    public String getDataSourceType()
    {
	return dataSourceType;
    }

    //store the DataSource to Infinispan
    public void store()
    {
	ID.setDataSource(dataSourceID, this);
    }
}

