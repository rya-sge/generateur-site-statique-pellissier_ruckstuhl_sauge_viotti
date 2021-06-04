package utils;

import ch.heigvd.prsv.utils.HandlebarUtil;
import global.ConstantesTest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ch.heigvd.prsv.utils.FileHandler.readFile;

/*
Ce test doit être lancé dans la commande build !!!
Car il lui faut une arborescence
 */
public class HandlerbarTest {
    private final String testFilesPath = ConstantesTest.TEST_FOLDER + "BuildTest";

    //@Test
    public void test() throws IOException {

        //Création de la map
        Map<String, String> parameterMap = new HashMap<>();

        //Pair clé valeur name pour handlebar
        parameterMap.put("site", "Titre du site");
        parameterMap.put("page", "Titre de la page");
        parameterMap.put("content", "contenu");


        //Création du content
        String filename = testFilesPath + "/template/content.html";
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8));
        writer.write("Contenu de test");
        writer.close();


        //Appel la fonction handler pour créer le fichier /page.html
        HandlebarUtil b = new HandlebarUtil(testFilesPath);
        String result = b.transform(parameterMap);

        //Suppression fichier test
        File f = new File(filename);
        f.delete();

        String model = readFile("src/test/java/utils/testfiles/result.html");
        model = model.replaceAll("\r\n", "\n");
        assertEquals(model, result);
    }

}
