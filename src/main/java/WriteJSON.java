import java.io.FileWriter;
import java.io.IOException;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class WriteJSON {

    private String siteName;
    private String domain;
    private final String file;

    WriteJSON(String file){
        this.file = file;
    }

    private JSONObject toJSON(String siteName, String domain){
        JSONObject object = new JSONObject();
        object.put("name", siteName);
        object.put("domain", domain);

        return object;
    }

    private boolean writeFile(String data){
        try (FileWriter writer = new FileWriter(file)){
            writer.write(data);
            writer.flush();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }






    public boolean writeFile(String siteName, String domain){

        JSONObject object = toJSON(siteName, domain);

        return this.writeFile(object.toJSONString());

    }

}
