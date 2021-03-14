import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Write
{
    @SuppressWarnings("unchecked")
    public static void main( String[] args )
    {
        // Charlie Chaplin
        JSONObject Charlie = new JSONObject();
        Charlie.put("prenom", "Charlie");
        Charlie.put("nom",    "Chaplin");
        Charlie.put("eamil",  "charlie@chapelin.fun");

        JSONObject CharlieObject = new JSONObject();
        CharlieObject.put("personne", Charlie);


        // Spider Man
        JSONObject Spider = new JSONObject();
        Charlie.put("prenom", "Spider");
        Charlie.put("nom",    "Man");
        Charlie.put("eamil",  "spider@man.movie");

        JSONObject SpiderObject = new JSONObject();
        SpiderObject.put("personne", Charlie);

        // Ajouter les personnes à la liste
        JSONArray personneList = new JSONArray();
        personneList.add(CharlieObject);
        personneList.add(SpiderObject);

        // Ecriture du fichier JSON
        try (FileWriter file = new FileWriter("src/jsonFile.json")) {

            file.write(personneList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}