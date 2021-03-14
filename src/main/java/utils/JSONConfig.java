package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONConfig {

    private String domain, name; // attributs du fichier de config
    private final String path;

    public JSONConfig(String path){
        this.path = path;
    }


    /* Méthodes privées */

    // écrit dans le fichier.
    // TODO: Une classe qui gère la lecture/écriture des fichiers
    private boolean write(String data){
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

    private JSONObject toJSON(String siteName, String domain){
        JSONObject object = new JSONObject();
        object.put("name", siteName);
        object.put("domain", domain);

        return object;
    }


    /* Méthodes publiques */

    public boolean read() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(path)) {
            Object obj = jsonParser.parse(reader);

            JSONObject jsonObject = (JSONObject) obj;

            //Récupère le nom du site
            String name = (String) jsonObject.get("name");
            this.name = name;

            //Récupère le nom de domaine du site
            String domain = (String) jsonObject.get("domain");
            this.domain = domain;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean write(){

        JSONObject object = toJSON(name, domain);

        return this.write(object.toJSONString());

    }

    public void config(String siteName, String domain){
        this.name = siteName;
        this.domain = domain;
    }


    /* Getters */

    public String getDomain() {
        return domain;
    }

    public String getName() {
        return name;
    }

}
