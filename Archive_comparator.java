import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Archive_comparator {
    private static File archive_1,archive_2;

    public Archive_comparator(){
        getZip(true,"Open archive #1");
        getZip(false,"Open archive #2");
        compare();
    }

    public Archive_comparator(String[] args)
    {
        if (args.length==2)
        {
            archive_1 = new File(args[0]);
            archive_2 = new File(args[1]);
            if (!archive_1.exists() || !archive_2.exists())throw new InputMismatchException();
        }
        else new Archive_comparator();
        compare();
    }

    private static void getZip(boolean first,String buttonText)
    {
        JFileChooser openFileDialog = new JFileChooser();
        openFileDialog.setDialogTitle("Choose archive");
        openFileDialog.setAcceptAllFileFilterUsed(false);
        openFileDialog.setFileFilter(new FileNameExtensionFilter(".zip archives", "zip"));
        int status = openFileDialog.showDialog(null, buttonText);
        if (status == JFileChooser.APPROVE_OPTION) {
            if (first)archive_1=openFileDialog.getSelectedFile();
            else archive_2=openFileDialog.getSelectedFile();
        }
        else throw new InputMismatchException();
    }

    private static void compare()
    {
        List<String> output = new ArrayList<>();
        output.add("\t'-' - file deleted\n");
        output.add("\t'*' - file updated\n");
        output.add("\t'?' - file renamed\n");
        output.add("\t'+' - file added\n");
        output.add("\t'0' - file unchanged\n\n");
        output.add(""+archive_1.getName()+" | "+archive_2.getName()+"\n\n");
        HashMap<String, Long>archive1_Files = getFiles(archive_1);
        HashMap<String, Long>archive2_Files = getFiles(archive_2);
        Iterator <String> iterator1 = archive1_Files.keySet().iterator();
        while (iterator1.hasNext())
        {
            boolean founded = false;
            String name = iterator1.next();
            Long size = archive1_Files.get(name);

            Iterator <String> iterator2 = archive2_Files.keySet().iterator();
            while (iterator2.hasNext())
            {
                String name_2 = iterator2.next();
                Long size_2 = archive2_Files.get(name_2);
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
        if(!archive2_Files.isEmpty())for (String name:archive2_Files.keySet()) output.add(" | "+name+" +\n"); // if files not founded in first archive, then files has been added
        printResult(output);
    }

    private static HashMap<String,Long> getFiles(File zipFile)
    {
        HashMap<String,Long> files = new HashMap<String, Long>();
        try(ZipInputStream archive = new ZipInputStream(new FileInputStream(zipFile)))
        {
            ZipEntry entry;
            while((entry=archive.getNextEntry())!=null)files.put(entry.getName(),entry.getSize());
        }
        catch(Exception error){
            System.out.println(error.getMessage());
            System.exit(-1);
        }
        return files;
    }

    private static void printResult(List<String>output) {
        try(FileWriter writer = new FileWriter("out\\Compare result.txt", false))
        {
            for (String text:output)
            {
                String[] conv = text.split("\\|");
                if (conv.length<2)writer.write(text);
                else writer.write(String.format("%30s",conv[0])+" | "+String.format("%30s",conv[1]));
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
