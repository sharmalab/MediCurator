package edu.emory.bmi.medicurator.test;

import edu.emory.bmi.medicurator.general.*;
import edu.emory.bmi.medicurator.tcia.*;
import edu.emory.bmi.medicurator.infinispan.*;
import edu.emory.bmi.medicurator.image.*;
import edu.emory.bmi.medicurator.storage.*;

public class TestTcia
{
    public static void main(String[] args) throws Exception
    {
	TciaDataSource source = new TciaDataSource();
	TciaDataSet root = (TciaDataSet)ID.getDataSet(source.getRootDataSet());
	TciaDataSet collection = (TciaDataSet)ID.getDataSet(root.getSubsets()[0]);
	System.out.println(collection.getKeyword());
	TciaDataSet patient = (TciaDataSet)ID.getDataSet(collection.getSubsets()[0]);
	System.out.println(patient.getKeyword());
	TciaDataSet study = (TciaDataSet)ID.getDataSet(patient.getSubsets()[0]);
	System.out.println(study.getKeyword());
	TciaDataSet series = (TciaDataSet)ID.getDataSet(study.getSubsets()[0]);
	System.out.println(series.getKeyword());
	series.download();
	study.download();
	System.out.println("Finish");
    }
}
