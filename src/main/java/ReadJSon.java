import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class ReadJSon {
    private String domain, name;
    private String path;

    ReadJSon(String path)
    {
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }

    public String getName() {
        return name;
    }

    public void openFile()
    {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(path))
        {
            Object obj = jsonParser.parse(reader);

            JSONObject jsonObject = (JSONObject) obj;

            //Récupère le nom du site
            String name = (String) jsonObject.get("name");
            this.name = name;

            //Récupère le nom de domaine du site
            String domain = (String) jsonObject.get("domain");
            this.domain = domain;

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
