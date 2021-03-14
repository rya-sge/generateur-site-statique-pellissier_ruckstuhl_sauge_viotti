package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Utils {

    public static void createFile(String txt, String name) {
        try {
            File myObj = new File(name);
            System.out.println("File created: " + myObj.getName());
            OutputStreamWriter writer = new OutputStreamWriter( new FileOutputStream(myObj), "UTF-8" );
            writer.write(txt);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
