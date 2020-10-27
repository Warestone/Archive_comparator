import javax.imageio.IIOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class GetFilesZip {
    public HashMap<String, Long> GetFiles(File zipFile)
    {
        HashMap<String,Long> files = new HashMap<String, Long>();
        try(ZipInputStream archive = new ZipInputStream(new FileInputStream(zipFile)))
        {
            ZipEntry entry;
            while((entry=archive.getNextEntry())!=null)files.put(entry.getName(),entry.getSize());
        }
        catch(IOException error){
            System.out.println(error.getMessage());
            System.exit(-1);
        }
        return files;
    }
}
