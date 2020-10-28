import java.io.File;
import java.util.*;

public class Archive_comparator {

    public Archive_comparator(){
        GetZipJFile gp = new GetZipJFile();
        File archive1 = gp.GetArchive(true);
        File archive2 = gp.GetArchive(false);
        executeCompare(archive1,archive2);
    }

    public Archive_comparator(String[] args)
    {
        if (args.length==2)
        {
            File archive1 = new File(args[0]);
            File archive2 = new File(args[1]);
            if (!archive1.exists() || !archive2.exists())throw new InputMismatchException();
            executeCompare(archive1,archive2);
        }
    }

    private static void executeCompare(File archive1, File archive2){
        CompareZipFiles czf = new CompareZipFiles();
        PrintToFile ptf = new PrintToFile();
        int status = ptf.printResult(czf.CompareArchives(archive1,archive2));
        if (status < 1)System.out.println("\n\nThe program ended with error.");
    }

}
