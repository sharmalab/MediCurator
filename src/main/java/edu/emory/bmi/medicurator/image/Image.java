package edu.emory.bmi.medicurator.image;

import edu.emory.bmi.medicurator.general.Metadata;
import edu.emory.bmi.medicurator.infinispan.ID;
import edu.emory.bmi.medicurator.storage.*;
import java.security.MessageDigest;
import java.util.UUID;
import java.math.BigInteger;

/*
 * Image is the actual data to be managed in MediCurator.
 * Each Image consists of a Metadata and a byte[] as its raw data.
 * Each Image has a relative path as its storage location.
 * We calculate the MD5 value as hash code for comparing two Images' raw data.
 */
public abstract class Image
{
    //the unique ID used to retrieve the Image inside MediCurator
    private UUID imageID = UUID.randomUUID();
    public UUID getID() { return imageID; }

    //a storage instance used to store data locally
    protected Storage storage = LocalStorage.getInstance();

    //the relative path of the Image
    protected String path;

    //the md5 hash value of the image raw data
    protected String md5;

    //the ID of the Image's Metadata
    protected UUID metaID;

    //get Metadata
    public abstract Metadata getMetadata();

    //get raw data
    public abstract byte[] getRawImage();

    //create a new Image with its relative path
    public Image(String path)
    {
	this.path = path;
	metaID = null;
	md5 = null;
    }

    //calculate hash code of image raw data
    public String getHashCode()
    {
	if (md5 == null)
	{
	    try {
		byte[] data = getRawImage();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();
		md.update(data);
		BigInteger code = new BigInteger(1, md.digest());
		md5 = code.toString(16);
		while(md5.length() < 32) md5 = "0" + md5;
		store();
	    }
	    catch (Exception e) {
		System.out.println("[ERROR] when calculate md5 hashcode " + path + " -- " + e);
		md5 = UUID.randomUUID().toString();
	    }
	}
	return md5;
    }

    //get relative path
    public String getPath()
    {
	return path;
    }

    //get Metadata ID
    public UUID getMetaID()
    {
	return getMetadata().getID();
    }
    
    //store the Image to Infinispan
    protected void store()
    {
	ID.setImage(imageID, this);
    }
}

