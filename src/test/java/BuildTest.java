/*
Date : 27.03.2021
Groupe : PRSV
Description : Test cmd Build
 */

import ch.heigvd.prsv.Build;
import ch.heigvd.prsv.Init;
import global.ConstantesTest;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import ch.heigvd.prsv.utils.Contenu;
import ch.heigvd.prsv.utils.FileHandler;
import utils.HandlebarTest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BuildTest {
    private final String testFilesPath = ConstantesTest.TEST_FOLDER;
    final private String rootDirectory = testFilesPath + "/BuildTest";


    void createArborescence() {
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
        if (new File(rootDirectory).exists()) {
            FileUtils.forceDelete(new File(rootDirectory));
        }

        createArborescence();


        //Suppression du dossier si existant
        File dir = new File(testFilesPath + "BuildTest");

        //Création des dossiers root, résultat final ciblé

        listDir.add(new File(dir + "/build/content/"));
        listDir.add(new File(dir + "/build/"));

        for (File d : listDir) {
            FileUtils.forceMkdir(d);
        }

        listFiles.add(new File(dir + "/build/content/page.html"));
        listFiles.add(new File(dir + "/build/content/image.png"));
        listFiles.add(new File(dir + "/build/index.html"));

        for (File f : listFiles) {
            f.createNewFile();
        }

        //Création du second dossier
        File dir2 = new File(testFilesPath + "BuildTest2");
        if (dir2.exists()) {
            FileUtils.forceDelete(dir2);
        }


        //Création des dossiers sur lesquels seront lancé la commande Build
        FileUtils.copyDirectory(dir, dir2);
        File dir2Build = new File(dir2 + "/build");
        if (dir2Build.exists()) {
            FileUtils.forceDelete(dir2Build);
        }

        //Test handlebar
        HandlebarTest ht = new HandlebarTest();
        ht.test();

        Build b = new Build();
        assertEquals(1, new CommandLine(b).execute(dir2.toString()));

        //Préparation des tests
        File[] fileList = dir.listFiles();
        File[] fileList2 = dir2.listFiles();

        //Vérifie que le nombre de fichiers soit équivalents
        assertEquals(fileList.length, fileList2.length);

        //Vérifie que le fichier index a bien été converti dans le build
        File idxBase = new File(testFilesPath + "BuildTest/build/index.html");
        File idxCreated = new File(testFilesPath + "BuildTest2/build/index.html");
        assertEquals(idxBase.exists(), idxCreated.exists());

        if (dir.exists()) {
            //   FileUtils.forceDelete(dir);
        }
        if (dir2.exists()) {
            //    FileUtils.forceDelete(dir2);
        }


        Thread t = new Thread() {
            public void run() {
                new CommandLine(b).execute(dir2.toString(), "--watch");
            }
        };
        t.start();


        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String titre = "Mon premier article";
        String date = format.format(calendar.getTime());
        LinkedList<String> c = new LinkedList<String>();
        c.add("\n# Mon titre #\n");
        c.add("## Mon sous-titre ##\n");
        c.add("Le contenu de mon article.");
        c.add("![Une image](./image.png)");
        Contenu page = new Contenu(titre, "", date, c);

        try {
            //En attend pour être sûr que le build a pu se faire
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        }

        FileHandler.create(dir2 + "/content/test.md", page.toString());

        try {
            //En attend pour être sûr que le build a pu se faire
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Création du fichier
        //Il faut vérifier "à la main" que la commande build a fonctionné
        //Car Avec des assert, ceux-ci étaient réalisés avant la fermeture du fichier et ne fonctionnaient pas
        FileHandler.create(dir2 + "/content/test2.md", page.toString());

        try {
            //En attend pour être sûr que le build a pu se faire
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        }

        //Mettre fin à la watchApi
        t.interrupt();
    }
}
