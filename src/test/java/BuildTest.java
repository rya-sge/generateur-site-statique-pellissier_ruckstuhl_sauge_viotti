import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BuildTest {
    @Test
    void call() throws IOException {

        //Suppression du dossier si existant
        File dir = new File("BuildTest");
        if(dir.exists())
        {
            FileUtils.forceDelete(dir);
        }
        //Création des dossiers root, résultat final ciblé
        FileUtils.forceMkdir(dir);

        File config = new File(dir+"/config.yaml");
        config.createNewFile();

        File index = new File(dir+"/index.md");
        index.createNewFile();

        File indexHtml = new File(dir+"/build/");
        indexHtml.mkdir();
        indexHtml = new File("BuildTest/build/index.html");
        indexHtml.createNewFile();


        File dir2 = new File("BuildTest2");
        if(dir2.exists())
        {
            FileUtils.forceDelete(dir2);
        }

        //Création des dossiers sur lesquels seront lancé la commande Build
        FileUtils.forceMkdir(dir2);
        File config2 = new File(dir2+"/config.yaml");
        config2.createNewFile();

        File index2 = new File(dir2+"/index.md");
        index2.createNewFile();

        String argsBuild = "BuildTest2";
        Build b = new Build();
        Integer buildRet = new CommandLine(b).execute(argsBuild);

        //Préparation des tests
        File f = new File("BuildTest");
        File[] fileList = f.listFiles();
        File f2 = new File("BuildTest2");
        File[] fileList2 = f2.listFiles();

        //Vérifie que le nombre de fichiers soit équivalents
        assertEquals(fileList.length, fileList2.length);

        //Vérifie que le fichier index a bien été converti dans le build
        File idxBase = new File("BuildTest/build/index.html");
        File idxCreated = new File("BuildTest2/build/index.html");
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
