package edu.emory.bmi.medicurator.dupdetect;

import edu.emory.bmi.medicurator.general.*;
import edu.emory.bmi.medicurator.infinispan.*;
import edu.emory.bmi.medicurator.image.*;
import java.util.ArrayList;
import java.util.UUID;
import java.io.*;

public class TestDupDetect
{
    private static ArrayList<UUID> imgIDs = new ArrayList<UUID>();

    private static void findImgs(File dir)
    {
	File[] fs = dir.listFiles();
	for (int i = 0; i < fs.length; ++i)
	{
	    if (fs[i].isDirectory())
		findImgs(fs[i]);
	    else
	    {
		imgIDs.add((new DicomImage(fs[i].getAbsolutePath())).getID());
	    }
	}
    }

    public static void main(String[] args) throws Exception
    {

	File baseDir = new File("/home/chenyr/dicoms/");

	findImgs(baseDir);

	DuplicatePair[] dps = DupDetect.detect((UUID[])imgIDs.toArray(new UUID[0]));

	for (int i = 0; i < dps.length; ++i)
	{
	    System.out.println(ID.getImage(dps[i].first).getPath() + "====================" + ID.getImage(dps[i].second).getPath());
	}
    }
}

