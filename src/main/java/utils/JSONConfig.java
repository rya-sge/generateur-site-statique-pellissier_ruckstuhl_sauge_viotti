/*
Date : 14.03.2021
Groupe : PRSV
Description : class pour le fichier de configuration json
 */

package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONConfig {

    private String domaine, titre, description; // attributs du fichier de config
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

    private JSONObject toJSON(String titre, String domaine, String description){
        JSONObject object = new JSONObject();
        object.put("titre", titre);
        object.put("domaine", domaine);
        object.put("description", description);
        return object;
    }


    /* Méthodes publiques */

    public boolean read() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(path)) {
            Object obj = jsonParser.parse(reader);

            JSONObject jsonObject = (JSONObject) obj;

            //Récupère le nom du site
            String name = (String) jsonObject.get("titre");
            this.titre = name;

            //Récupère le nom de domaine du site
            String domaine = (String) jsonObject.get("domaine");
            this.domaine = domaine;

            //Récupère la description du site
            String description = (String) jsonObject.get("description");
            this.description = description;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public boolean write(){

        JSONObject object = toJSON(titre, domaine, description);

        return this.write(object.toJSONString());

    }

    public void config(String titre, String domaine, String description){
        this.titre = titre;
        this.domaine = domaine;
        this.description = description;
    }


    /* Getters */

    public String getDomaine() {
        return domaine;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }
}
