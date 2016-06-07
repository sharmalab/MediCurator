package edu.emory.bmi.medicurator.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import edu.emory.bmi.medicurator.general.*;
import edu.emory.bmi.medicurator.infinispan.*;
import edu.emory.bmi.medicurator.dupdetect.*;
import edu.emory.bmi.medicurator.image.*;
import java.util.ArrayList;
import java.util.UUID;
import java.io.*;

public class TestDupDetect
{
    ArrayList<UUID> imgIDs;

    @Before
    public void setupImg()
    {
	File baseDir = new File(TestDupDetect.class.getResource("/dicoms").getPath());
	imgIDs = new ArrayList<UUID>();
	for (File img : baseDir.listFiles())
	{
	    String path = img.getAbsolutePath();
	    imgIDs.add((new DicomImage(path)).getID());
	}
    }

    @Test
    public void testDupDetect() throws Exception
    {
	DuplicatePair[] dps = DupDetect.detect((UUID[])imgIDs.toArray(new UUID[0]));
	for (DuplicatePair dp : dps)
	{
	    System.out.println(ID.getImage(dp.first).getPath() + "  =  " + ID.getImage(dp.second).getPath());
	}
    }
}

