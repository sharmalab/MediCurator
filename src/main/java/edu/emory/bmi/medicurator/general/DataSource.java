package edu.emory.bmi.medicurator.general;

import edu.emory.bmi.medicurator.infinispan.ID;
import java.util.UUID;

public abstract class DataSource
{
    private UUID dataSourceID = UUID.randomUUID();
    public UUID getID() { return dataSourceID; }

    protected final String dataSourceType;

    public abstract UUID getRootDataSet();

    public DataSource(String dataSourceType)
    {
	this.dataSourceType = dataSourceType;
    }

    public String getDataSourceType()
    {
	return dataSourceType;
    }

    public void store()
    {
	ID.setDataSource(dataSourceID, this);
    }
}

