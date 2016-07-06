import edu.emory.bmi.medicurator.storage.HdfsStorage;
import java.io.*;

/*
 *  testhdfs
 */

public class testhdfs 
{
    public static void main(String[] args)
    {
	try{
	    HdfsStorage storage = new HdfsStorage();
	    InputStream in = new FileInputStream("hdfs://162.105.203.138:9000/user/chenyr/medicurator/src/main/java/edu/emory/bmi/medicurator/storage/a.txt");
	    storage.saveToPath("s.txt",in);
	    
	    InputStream h=storage.loadFromPath("s.txt");
	    byte[] b = new byte[1024];
	    int numBytes = 0;
	    OutputStream os = new FileOutputStream("hdfs://162.105.203.138:9000/user/chenyr/medicurator/src/main/java/edu/emory/bmi/medicurator/storage/out.txt");
	    while((numBytes = h.read(b))>0){
		 os.write(b,0,numBytes);
	    }
	    in.close();
	    h.close();
	    os.close();
	}
	catch (IOException e) {
	    System.out.println("[ERROR] IOException when HdfsStorage saveToPath (" + path+ ")" + e);
	}
	catch (FileNotFoundException e) {
	    System.out.println("[ERROR] File Not Found when HdfsStorage saveToPath (" + path+ ")" + e);
	}
	return;
    }
}
