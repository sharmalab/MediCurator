package edu.emory.bmi.medicurator.tcia;

import edu.emory.bmi.medicurator.general.DataSet;
import java.util.UUID;

public class TciaDataSet extends DataSet
{
     public UUID getParent() { return null; }
     public UUID[] getSubsets() { return null; }
     public UUID[] getData() { return null; }
     public TciaDataSet() { super("tcia"); }
}

