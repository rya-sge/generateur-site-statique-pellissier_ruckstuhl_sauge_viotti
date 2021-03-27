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

        //Création du répertoire de base
        File dir = new File("root");
        if(dir.exists())
        {
            FileUtils.forceDelete(dir);
        }
        //Création des dossiers root, résultat final ciblé
        String argsInit = "root";
        Init i = new Init();
        Integer initRet = new CommandLine(i).execute(argsInit);
        File config = new File(dir+"/config.yaml");
        config.createNewFile();
        File index = new File(dir+"/build/");
        index.mkdir();
        index = new File("root/build/index.html");
        index.createNewFile();



        File dir2 = new File("root2");
        if(dir2.exists())
        {
            FileUtils.forceDelete(dir2);
        }
        //Création des dossiers root, résultat final ciblé
        String argsInit2 = "root2";
        Init i2 = new Init();
        Integer initRet2 = new CommandLine(i2).execute(argsInit2);
        String argsBuild = "root2";
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
        File index1 = new File("root/build/index.html");
        assertEquals(index1.exists(), new File("root2/build/index.html").exists());
    }
}
