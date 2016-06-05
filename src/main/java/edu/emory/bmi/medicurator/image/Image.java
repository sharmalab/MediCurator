package edu.emory.bmi.medicurator.image;

import edu.emory.bmi.medicurator.general.Metadata;
import edu.emory.bmi.medicurator.infinispan.ID;
import java.security.MessageDigest;
import java.util.UUID;
import java.math.BigInteger;

public abstract class Image
{
    private UUID imageID = UUID.randomUUID();
    public UUID getID() { return imageID; }


    protected String path;
    protected String md5;
    protected UUID metaID;

    public abstract Metadata getMetadata();
    public abstract byte[] getRawImage();

    public Image(String path)
    {
	this.path = path;
	metaID = null;
	md5 = null;
    }

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
		updateInf();
	    }
	    catch (Exception e) {}
	}
	return md5;
    }

    public String getPath()
    {
	return path;
    }

    public UUID getMetaID()
    {
	return getMetadata().getID();
    }

    protected void updateInf()
    {
	ID.setImage(getID(), this);
    }
}

