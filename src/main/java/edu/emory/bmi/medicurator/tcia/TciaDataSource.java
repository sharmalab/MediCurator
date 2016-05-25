package edu.emory.bmi.medicurator.tcia;

import edu.emory.bmi.medicurator.general.DataSource;
import edu.emory.bmi.medicurator.general.Metadata;
import java.util.UUID;

public class TciaDataSource extends DataSource
{
    public UUID getRootDataSet() { return null; }
    public UUID retrieveDataSet(Metadata meta) { return null; }
    public TciaDataSource() { super("tcia"); }
}
