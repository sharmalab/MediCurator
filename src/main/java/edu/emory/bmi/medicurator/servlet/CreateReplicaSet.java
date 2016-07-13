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

public class CreateReplicaSet extends Page
{
    public CreateReplicaSet()
    {
	 title = "Create ReplicaSet";
    }

    protected void printSection(final HttpServletRequest request, final HttpServletResponse response, PrintWriter out)
    {
	User user = ID.getUser(UUID.fromString(cookie.get("userid")));
	String replicaName = request.getParameter("name");

	if (replicaName == null)
	{
	    out.println("<form method=\"get\" action=\"/CreateReplicaSet\">");
	    out.println("ReplicaSet Name<br />");
	    out.println("<input type=\"text\" name=\"name\" />");
	    out.println("<input type=\"submit\" name=\"submit\" value=\"create\" />");
	    out.println("</form>");
	}
	else
	{
	    ReplicaSet newSet = new ReplicaSet(replicaName);
	    user.addReplicaSet(newSet.getID());
	    out.println("<h1>New ReplicaSet \"" + replicaName + "\" created</h1>");
	    out.println("<a href=\"/Index\">return</a>");
	}
    }
}

