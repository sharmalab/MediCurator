package edu.emory.bmi.medicurator.tcia;

import edu.emory.bmi.medicurator.general.Data;
import java.io.InputStream;

public class TciaData extends Data
{
    public InputStream fetchFromDataSource() { return null; }
    public String savePath() { return null; }
    public TciaData() { super("tcia"); }
}


