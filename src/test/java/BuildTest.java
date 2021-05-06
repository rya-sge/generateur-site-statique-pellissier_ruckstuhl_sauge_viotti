/*
Date : 27.03.2021
Groupe : PRSV
Description : Test cmd Build
 */
import global.ConstantesTest;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import utils.HandlerbarTest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BuildTest {
    private final String testFilesPath =  ConstantesTest.TEST_FOLDER;
    final private String rootDirectory = testFilesPath  + "/BuildTest";


    void createArborescence(){
        final String titre = "My Poney Back";
        final String domaine = "Sparkle.com";
        final String description = "Un Lieu Magic où les poney vivent en paix et en harmonie";
        Init i = new Init();
        String input = titre + '\n' + domaine + '\n' + description + '\n';

        /* Src : https://www.codota.com/code/java/methods/java.lang.System/setIn */
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        new CommandLine(i).execute(rootDirectory);
    }
    @Test
    void call() throws IOException {

        List<File> listFiles = new ArrayList<>();
        List<File> listDir = new ArrayList<>();
        if(new File(rootDirectory).exists())
        {
            FileUtils.forceDelete(new File(rootDirectory));
        }

        createArborescence();



        //Suppression du dossier si existant
        File dir = new File(testFilesPath + "BuildTest");
        //Création des dossiers root, résultat final ciblé

        listDir.add(new File(dir+"/build/content/"));
        listDir.add(new File(dir + "/build/"));

        for(File d : listDir)
        {
            FileUtils.forceMkdir(d);
        }

        listFiles.add(new File(dir + "/build/content/page.html"));
        listFiles.add(new File(dir + "/build/content/image.png"));
        listFiles.add(new File(dir + "/build/index.html"));

        for(File f : listFiles)
        {
            f.createNewFile();
        }

        //Création du second dossier
        File dir2 = new File(testFilesPath + "BuildTest2");
        if(dir2.exists())
        {
            FileUtils.forceDelete(dir2);
        }


        //Création des dossiers sur lesquels seront lancé la commande Build
        FileUtils.copyDirectory(dir,dir2);
        File dir2Build = new File (dir2 + "/build");
        if(dir2Build.exists())
        {
            FileUtils.forceDelete(dir2Build);
        }

        //Test handlebar
        HandlerbarTest ht = new HandlerbarTest();
        ht.test();

        Build b = new Build();
        assertEquals(1,new CommandLine(b).execute(dir2.toString()));

        //Préparation des tests
        File[] fileList = dir.listFiles();
        File[] fileList2 = dir2.listFiles();

        //Vérifie que le nombre de fichiers soit équivalents
        assertEquals(fileList.length, fileList2.length);

        //Vérifie que le fichier index a bien été converti dans le build
        File idxBase = new File(testFilesPath + "BuildTest/build/index.html");
        File idxCreated = new File(testFilesPath + "BuildTest2/build/index.html");
        assertEquals(idxBase.exists(), idxCreated.exists());

        if(dir.exists())
        {
        //   FileUtils.forceDelete(dir);
        }
        if(dir2.exists())
        {
        //    FileUtils.forceDelete(dir2);
        }

    }
}
