package utils;

import java.io.*;
import java.util.Arrays;

public class Utils {

    /**
     *
     * @param contenu
     * @param name
     */
    public static void createFile(String contenu, String name) {
        try {
            File myObj = new File(name);
            System.out.println("File created: " + myObj.getName());
            OutputStreamWriter writer = new OutputStreamWriter( new FileOutputStream(myObj), "UTF-8" );
            try{
                writer.write(contenu);
                writer.flush();
            } finally{
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Les sauts de lignes sont ajoutés à la fin de chaque ligne
     * @param file
     * @return Contenu du fichier en String.
     */
    public static String readFile(File file) {
        try {
            BufferedReader  reader = new BufferedReader(new InputStreamReader( new FileInputStream(file), "UTF-8" ));
            try{
                String b = null;
                StringBuilder builder = new StringBuilder();
                while((b = reader.readLine()) != null){
                    builder.append(b + '\n');
                }
                return builder.toString();
            } finally{
               reader.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }
}
