import java.io.*; import java.util.*; import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.Tag;

public class Read
{
    public static void main(String[] args) throws Exception
    {
	InputStream in = new FileInputStream(new File("/Users/xupengli/Downloads/a.dcm"));
	DicomInputStream dcm = new DicomInputStream(in);
	DicomObject img = dcm.readDicomObject();
	System.out.println(img.isRoot());

	for (Iterator iter = img.datasetIterator(); iter.hasNext(); )
	{
	    DicomElement e = (DicomElement)iter.next();
	    System.out.println(Integer.toHexString(e.tag()) + "   " + img.nameOf(e.tag()) + "  " + img.getString(e.tag()));
	}
	byte[] data = img.getBytes(Tag.PixelData);
	System.out.println(data.length);
    }
}
