package utils;

import org.json.simple.JSONObject;

import java.io.*;

public class FileHandler {



    public static String readFile(String path) {
        StringBuilder content = new StringBuilder();

        try (FileReader reader = new FileReader(path)) {

            while(true){
                int charCode = reader.read();

                if(charCode == -1){
                    break;
                }

                content.append(charCode);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return content.toString();
        }

        return content.toString();
    }


    public static boolean write(String path, String data){
        try (FileWriter writer = new FileWriter(path)){
            writer.write(data);
            writer.flush();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }




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
