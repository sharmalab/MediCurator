package edu.emory.bmi.medicurator.storage;

import java.io.*;
 
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import java.io.IOException;

import org.apache.hadoop.hdfs.*;  

/*
 * Implementation of HDFS storage
 */
public class HdfsStorage implements Storage
{
    private String hdfsPath = "hdfs://162.105.203.138:9000/user/chenyr/medicurator/"; 

    //save data from InputStream to a relative path
    //required parent directories will be created if not exists
    public boolean saveToPath(String path, InputStream in)
    {
	try{
	    Configuration conf = new Configuration();
	    FileSystem hdfs = FileSystem.get(conf);
	    Path dst = new Path(hdfsPath+path);
	    if(hdfs.exists(dst))
	    {
		System.out.print(hdfsPath+path+"already exists");
		return true;
	    }
	    //Create a new file and write data to it.
	    FSDataOutputStream out = hdfs.create(dst);
	    byte[] b = new byte[1024];
	    int numBytes = 0;
	    while ((numBytes = in.read(b)) > 0) {
		out.write(b, 0, numBytes);
	    }
	    //Close all the file descripters
	    in.close();
	    out.close();
	    hdfs.close();
	    return true;
	}
	catch (FileNotFoundException e) {
	    System.out.println("[ERROR] File Not Found when HdfsStorage saveToPath (" + path+ ")" + e);
	}
	catch (IOException e) {
	    System.out.println("[ERROR] IOException when HdfsStorage saveToPath (" + path+ ")" + e);
	}
	return false;
    }

    //get the InputStream of a file with specified relative path
    public InputStream loadFromPath(String path)
    {
	InputStream in=null;
	try{
	    Configuration conf = new Configuration(); 
	    FileSystem fileSystem = FileSystem.get(conf);
	    Path hdfspath = new Path(hdfsPath+path);
	    if (!fileSystem.exists(hdfspath)) { 
		System.out.println("File does not exists"); 
		return in; 
	    }
	    in = fileSystem.open(hdfspath);
    	//String filename = hdfspath.substring(file.lastIndexOf('/') + 1, file.length());

	//IutputStream out = new BufferedOutputStream(new FileOutputStream( new File(filename)));
	}
	catch (FileNotFoundException e) {
	    System.out.println("[ERROR] File Not Found when HdfsStorage loadFromPath (" + path+ ")" + e);
	}
	catch (IOException e) {
	    System.out.println("[ERROR] IOException when HdfsStorage loadFromPath (" + path+ ")" + e);
	}
	return in;
    }
}

