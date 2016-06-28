package edu.emory.bmi.medicurator.servlet;

import edu.emory.bmi.medicurator.general.*;
import edu.emory.bmi.medicurator.tcia.*;
import edu.emory.bmi.medicurator.infinispan.*;
import edu.emory.bmi.medicurator.image.*;
import edu.emory.bmi.medicurator.storage.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class ShowReplicaSet extends Page
{
    public ShowReplicaSet()
    {
	title = "ReplicaSet";
    }

    protected void printSection(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
    {
	UUID replicaSetID = UUID.fromString(request.getParameter("replicasetID"));
	UUID dataSetID = null;
	if (request.getParameter("datasetID") != null)
	{
	    dataSetID = UUID.fromString(request.getParameter("datasetID"));
	}
	
	ReplicaSet replicaSet = ID.getReplicaSet(replicaSetID);
	if (dataSetID != null)
	{
	    replicaSet.putDataSet(dataSetID);
	}
	out.println("<h1>" + replicaSet.getName() + "</h1>");
	for (UUID datasetID : replicaSet.getDataSets())
	{
	    DataSet dataSet = ID.getDataSet(datasetID);
	    out.println("<p>" + dataSet.getKeyword() + "</p>");
	}
	out.println("<input type=\"button\" onclick=\"location='/SelectDataSet?replicasetID=" + 
			replicaSetID.toString() + "'\" value=\"Add DataSet\" />");
    }
}

