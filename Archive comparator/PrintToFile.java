import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PrintToFile {
    public static int printResult(List<String> output)
    {
        try(FileWriter writer = new FileWriter("out\\Compare result.txt", false))
        {
            for (String text:output)
            {
                String[] conv = text.split("\\|");
                if (conv.length<2)writer.write(text);
                else writer.write(String.format("%30s",conv[0])+" | "+String.format("%30s",conv[1]));
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            return -1;
        }
        return 1;
    }
}
