import java.io.File;
import java.util.*;

public class Archive_comparator {
    private static File archive1, archive2;

    public Archive_comparator(){
        GetZipJFile gp = new GetZipJFile();
        archive1 = gp.GetArchive(true);
        archive2 = gp.GetArchive(false);
    }

    public Archive_comparator(String[] args)
    {
        if (args.length==2)
        {
            archive1 = new File(args[0]);
            archive2 = new File(args[1]);
            if (!archive1.exists() || !archive2.exists())throw new InputMismatchException();
        }
    }

    public void executeCompare(){
        CompareZipFiles czf = new CompareZipFiles();
        PrintToFile ptf = new PrintToFile();
        int status = ptf.printResult(czf.CompareArchives(archive1,archive2));
        if (status < 1)System.out.println("\n\nThe program ended with error.");
    }

}
