import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CompareZipFiles {
    public static List<String> CompareArchives(File archive1, File archive2)
    {
        GetFilesZip gfz = new GetFilesZip();
        List<String> output = new ArrayList<>();
        output.add("\t'-' - file deleted\n");
        output.add("\t'*' - file updated\n");
        output.add("\t'?' - file renamed\n");
        output.add("\t'+' - file added\n");
        output.add("\t'0' - file unchanged\n\n");
        output.add(""+ archive1.getName()+" | "+ archive2.getName()+"\n\n");
        HashMap<String, Long> archive1Files = gfz.GetFiles(archive1);
        HashMap<String, Long>archive2Files = gfz.GetFiles(archive2);
        Iterator<String> iterator1 = archive1Files.keySet().iterator();
        while (iterator1.hasNext())
        {
            boolean founded = false;
            String name = iterator1.next();
            Long size = archive1Files.get(name);

            Iterator <String> iterator2 = archive2Files.keySet().iterator();
            while (iterator2.hasNext())
            {
                String name_2 = iterator2.next();
                Long size_2 = archive2Files.get(name_2);
                if (name.equals(name_2)&& size.compareTo(size_2) == 0)                      // if files same -- status 0
                {
                    output.add(name+" 0 | "+name_2+" 0\n");
                    iterator2.remove();
                    founded = true;
                    break;
                }
                if (!name.equals(name_2) && size.compareTo(size_2) == 0)                     // if files has same size,but name -- status ?
                {
                    output.add(name+" ? | "+name_2+" ?\n");
                    iterator2.remove();
                    founded = true;
                    break;
                }
                if (name.equals(name_2)&& size.compareTo(size_2) != 0)                       // if files has same name, but size  -- status *
                {
                    output.add(name+" * | "+name_2+" *\n");
                    iterator2.remove();
                    founded = true;
                    break;
                }
            }
            if (!founded)output.add(name+" - | \n");                                                          // if files not founded in second archive, then files has been deleted
        }
        if(!archive2Files.isEmpty())for (String name:archive2Files.keySet()) output.add(" | "+name+" +\n"); // if files not founded in first archive, then files has been added
        return output;
    }
}
