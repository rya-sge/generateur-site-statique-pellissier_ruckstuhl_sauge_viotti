import global.ConstantesTest;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.FileHandler.readFile;

public class Handler {
    private final String testFilesPath =  ConstantesTest.TEST_FOLDER;
    @Test
    void test(){

        String fichierModele  = "pageModel.html";
        String fichierCree =  testFilesPath + "/page.html";

        //Appel la fonction handler pour créer le fichier /page.html

        //Lecture du contenu des fichiers crées
        String fichierModeleContenu = readFile(fichierModele);
        String fichierCreeContenu =  readFile(fichierCree);

        //Le contenu entre le modele et le résutat doit être le même
        assertEquals(fichierModeleContenu, fichierModeleContenu);
    }

}
