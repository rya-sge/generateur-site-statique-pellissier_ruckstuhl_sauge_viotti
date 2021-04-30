/*
Date : 27.03.2021
Groupe : PRSV
Description : Test cmd Build
 */
import global.ConstantesTest;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BuildTest {
    private final String testFilesPath =  ConstantesTest.TEST_FOLDER;

    @Test
    void call() throws IOException {

        //Suppression du dossier si existant
        File dir = new File(testFilesPath + "BuildTest");
        if(dir.exists())
        {
            FileUtils.forceDelete(dir);
        }
        //Création des dossiers root, résultat final ciblé
        FileUtils.forceMkdir(dir);

        File config = new File(dir + "/" + Constantes.CONFIG_FILE_NAME);
        config.createNewFile();

        File index = new File(dir + "/" + Constantes.INDEX_FILE_NAME);
        index.createNewFile();

        File indexHtml = new File(dir + "/build/");
        indexHtml.mkdir();
        indexHtml = new File(testFilesPath + "BuildTest/build/index.html");
        indexHtml.createNewFile();


        File dir2 = new File(testFilesPath + "BuildTest2");
        if(dir2.exists())
        {
            FileUtils.forceDelete(dir2);
        }

        //Création des dossiers sur lesquels seront lancé la commande Build
        FileUtils.forceMkdir(dir2);
        File config2 = new File(dir2 + "/" + Constantes.CONFIG_FILE_NAME);
        config2.createNewFile();

        File index2 = new File(dir2 + "/" + Constantes.INDEX_FILE_NAME);
        index2.createNewFile();

        String argsBuild = testFilesPath + "BuildTest2";
        Build b = new Build();
        assertEquals(1,new CommandLine(b).execute(argsBuild));

        //Préparation des tests
        File f = new File(testFilesPath + "BuildTest");
        File[] fileList = f.listFiles();
        File f2 = new File(testFilesPath + "BuildTest2");
        File[] fileList2 = f2.listFiles();

        //Vérifie que le nombre de fichiers soit équivalents
        assertEquals(fileList.length, fileList2.length);

        //Vérifie que le fichier index a bien été converti dans le build
        File idxBase = new File(testFilesPath + "BuildTest/build/index.html");
        File idxCreated = new File(testFilesPath + "BuildTest2/build/index.html");
        assertEquals(idxBase.exists(), idxCreated.exists());

        if(dir.exists())
        {
           FileUtils.forceDelete(dir);
        }
        if(dir2.exists())
        {
            FileUtils.forceDelete(dir2);
        }

    }
}
