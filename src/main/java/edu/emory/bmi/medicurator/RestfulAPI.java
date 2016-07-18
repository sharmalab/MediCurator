package edu.emory.bmi.medicurator;

import edu.emory.bmi.medicurator.general.*;
import edu.emory.bmi.medicurator.tcia.*;
import edu.emory.bmi.medicurator.infinispan.*;
import edu.emory.bmi.medicurator.image.*;
import edu.emory.bmi.medicurator.storage.*;
import edu.emory.bmi.medicurator.servlet.*;

import java.lang.StringBuilder;
import java.io.*;
import java.util.*;

public class RestfulAPI
{
    //about User

    private static String toJSON(String name, String value)
    {
	return "{\"" + name + "\":\"" + value + "\"}";
    }

    private static String toJSON(String name, String[] values)
    {
	StringBuilder json = new StringBuilder();
	json.append("[");
	for (String value : values)
	{
	    json.append(toJSON(name, value));
	    json.append(",");
	}
	json.append("]");
	return json.toString();
    }

    public static String signup(String username, String password) // return userid or failed
    {
	if(username == null || password == null)
	{
	    return "Failed";
	}
	else
	{
	    if(User.lookup(username) != null)
	    {
		return "Failed";
	    }
	    else
	    {
		User user = new User(username,password);
		return user.getID().toString();
	    }
	}	
    }

    public static String login(String username, String password) // return userid
    {
	if(username == null || password == null)
	{
	    return "Failed";
	}
	else
	{
	    User user = User.lookup(username);
	    if(user == null)
		return  "Failed";
	    else
	    {
		if(!user.checkPassword(password))
		{
		    return "Failed";
		}
		else
		{
		    return user.getID().toString();
		}
	    }
	}
    }

    public static String[] getReplicaSets(String userid)  // return array of replicaset id 
    {
	User user = ID.getUser(UUID.fromString(userid));
	String[] a = new String[user.getReplicaSets().length];
	for(int i = 0; i < user.getReplicaSets().length;i++)
	{
	    a[i] = user.getReplicaSets()[i].toString();
	}
	return a;

    }

    public static String createReplicaSet(String userid, String replicaName)  // return new replicaset id
    {	
	User user = ID.getUser(UUID.fromString(userid));
	ReplicaSet newSet = new ReplicaSet(replicaName);
	user.addReplicaSet(newSet.getID());
	return newSet.getID().toString();
    }

    //about ReplicaSet
    public static String[] getDataSets(String replicasetID)  // return array of dataset id
    {
	ReplicaSet replicaset = ID.getReplicaSet(UUID.fromString(replicasetID));
	String[] a = new String[replicaset.getDataSets().length];
	for(int i = 0; i < replicaset.getDataSets().length; i++)
	{
	    a[i] = replicaset.getDataSets()[i].toString();
	}
	return a; 
    }

    public static String addDataSet(String replicasetID, String datasetID)
    {
	ReplicaSet replicaset = ID.getReplicaSet(UUID.fromString(replicasetID));
	replicaset.putDataSet(UUID.fromString(datasetID));
	return "DatasetID Has been added.";
    }

    public static String removeDataSet(String replicasetID, String datasetID)
    {
	ReplicaSet replicaset = ID.getReplicaSet(UUID.fromString(replicasetID));
	replicaset.removeDataSet(UUID.fromString(datasetID));
	return "DatasetID has been removed.";
    }

    //about DataSet

    public static String[] getRootDatasets()
    {
	ArrayList<String> roots = new ArrayList<String>();
	for (DataSource source : Constants.DATA_SOURCES)
	{
	    roots.add(source.getRootDataSet().toString());
	}
	return roots.toArray(new String[0]);
    }

    public static String[] getSubSets(String datasetID)  // return array of subset id 
    {
	DataSet dataset = ID.getDataSet(UUID.fromString(datasetID));
	String[] a=new String[dataset.getSubsets().length];
	for(int i = 0; i < dataset.getSubsets().length; ++i)
	{
	    a[i] = dataset.getSubsets()[i].toString();
	}
	return a;
    }

    public static boolean downloadDataSets(String datasetID)
    {
	DataSet dataset = ID.getDataSet(UUID.fromString(datasetID));
	return dataset.download();
    }

    public static boolean downloadOneDataSet(String datasetID)
    {
	DataSet dataset = ID.getDataSet(UUID.fromString(datasetID));
	return dataset.self_download();
    }

    public static boolean deleteDataSet(String datasetID)
    {
	DataSet dataset = ID.getDataSet(UUID.fromString(datasetID));
	return dataset.delete();
    }
}

