package utils;


import java.io.*;

public class FileHandler {

    public static String readFile(String path) {
        StringBuilder content = new StringBuilder();

        try{

            FileReader reader = new FileReader(path);
            while(true){
                int charCode = reader.read();

                if(charCode == -1){
                    break;
                }

                content.append((char)charCode);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return content.toString();
        }

        return content.toString();
    }


    public static boolean write(String path, String data){
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(data);
            writer.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public static boolean create(String path) {

        return create(path, "");
    }

    public static boolean create(String path, String data) {
        try {

            File file = new File(path);
            File dir = new File(file.getParent());
            dir.mkdirs();
            file.createNewFile();

            if(! data.isEmpty()){
                write(path, data);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public static boolean exists(String path){
        try {
            File f = new File(path);
            System.out.printf("\n-----------\n %s existe ? %s", path, (f.exists() ? "true" : "false"));
            return f.exists();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
