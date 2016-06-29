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

public class Download extends Page
{
    public Download()
    {
	title = "Download";
    }

    protected void printSection(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
    {
	UUID datasetID = UUID.fromString(request.getParameter("datasetID"));
	DataSet dataset = ID.getDataSet(datasetID);
	dataset.download();
	out.println("<h1>" + dataset.getKeyword() + "  downloaded</h1>");
    }
}

