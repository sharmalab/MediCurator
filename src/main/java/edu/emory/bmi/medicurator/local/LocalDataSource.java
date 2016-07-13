package edu.emory.bmi.medicurator.local;

import edu.emory.bmi.medicurator.general.*;
import java.util.*;

public class LocalDataSource extends DataSource
{
    private UUID rootDataSet = null;
    public String root;

    public LocalDataSource(String name, String root)
    {
	super("local." + name);
	this.root = root;
	store();
    }

    public UUID getRootDataSet()
    {
	if (rootDataSet == null)
	{
	    rootDataSet = (new LocalDataSet(dataSourceType, root, "/" + dataSourceType, null)).getID();
	    store();
	}
	return rootDataSet;
    }
}

