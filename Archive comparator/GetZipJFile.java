import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.InputMismatchException;

public class GetZipJFile {
    public static File GetArchive(boolean first)
    {
        String buttonText;
        File archive;
        if (first)buttonText = "Open archive #1";
        else buttonText = "Open archive #2";
        JFileChooser openFileDialog = new JFileChooser();
        openFileDialog.setDialogTitle("Choose archive");
        openFileDialog.setAcceptAllFileFilterUsed(false);
        openFileDialog.setFileFilter(new FileNameExtensionFilter(".zip archives", "zip"));
        int status = openFileDialog.showDialog(null, buttonText);
        if (status == JFileChooser.APPROVE_OPTION) {
            archive =openFileDialog.getSelectedFile();
        }
        else throw new InputMismatchException();
        return archive;
    }
}
