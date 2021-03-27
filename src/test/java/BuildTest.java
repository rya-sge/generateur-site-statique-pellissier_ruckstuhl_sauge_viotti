import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Utils.readFile;

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
        String argsInit = "BuildTest";
        Init i = new Init();
        Integer initRet = new CommandLine(i).execute(argsInit);

        File config = new File(dir+"/config.yaml");
        config.createNewFile();

        File index = new File(dir+"/build/");
        index.mkdir();
        index = new File("BuildTest/build/index.html");
        index.createNewFile();



        File dir2 = new File("BuildTest2");
        if(dir2.exists())
        {
            FileUtils.forceDelete(dir2);
        }

        //Création des dossiers sur lesquels seront lancé la commande Build
        String argsInit2 = "BuildTest2";
        Init i2 = new Init();
        Integer initRet2 = new CommandLine(i2).execute(argsInit2);

        String argsBuild = "BuildTest2";
        File config2 = new File(dir2+"/config.yaml");
        config2.createNewFile();

        Build b = new Build();
        Integer buildRet = new CommandLine(b).execute(argsBuild);

        File f = new File(argsInit);
        File[] fileList = f.listFiles();

        File f2 = new File(argsInit2);
        File[] fileList2 = f2.listFiles();

        //Vérifie que le nombre de fichiers soit équivalents
        assertEquals(fileList.length, fileList2.length);

        //Vérifie que le fichier index a bien été converti dans le build
        File index1 = new File("BuildTest/build/index.html");
        File index2 = new File("BuildTest2/build/index.html");
        assertEquals(index1.exists(), index2.exists());

        if(dir.exists())
        {
           FileUtils.forceDelete(dir);
        }
        if(dir2.exists())
        {
            //FileUtils.deleteDirectory(dir2);
        }

    }
}
