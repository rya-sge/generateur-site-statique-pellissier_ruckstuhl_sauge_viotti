package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;



import global.ConstantesTest;
import org.junit.Test;
import picocli.CommandLine;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.FileHandler.readFile;

public class HandlerbarTest {
    private final String testFilesPath =  ConstantesTest.TEST_FOLDER + "BuildTest";
    @Test
    public void test(){
//Création de la map
        Map<String, String> parameterMap = new HashMap<>();

        //Pair clé valeur name => Baeldung
        parameterMap.put("site", "Titre du site");
        parameterMap.put("page", "Titre de la page");
        parameterMap.put("content", "contenu");

        String fichierModele  = "pageModel.html";
        String fichierCree =  testFilesPath + "/page.html";

        String titrePage = "Mon titre";// process the line.
        Handlebars handlebars = new Handlebars();
        //Template template = handlebars.compileInline("Hi {{site}}! Hi {{page}}! Hi {{content}}!");
        //Appel la fonction handler pour créer le fichier /page.html
        HandlebarUtil b = new HandlebarUtil(testFilesPath);
        /*String result = b.transform(parameterMap, fichierCree);
        String model = readFile( "src/test/java/utils/testfiles/result.html");
        model = model.replaceAll("\r\n","\n");
        assertEquals(model,result);*/
        //Lecture du contenu des fichiers crées
        /*String fichierModeleContenu = readFile(fichierModele);
        String fichierCreeContenu =  readFile(fichierCree);

        //Le contenu entre le modele et le résutat doit être le même
        assertEquals(fichierModeleContenu, fichierModeleContenu);*/
    }

}
