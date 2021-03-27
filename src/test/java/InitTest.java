/*
Date : 27.03.2021
Groupe : PRSV
Description : Test cmd Init
 */

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import picocli.CommandLine;
import utils.JSONConfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static utils.FileHandler.eraseNotEmptyDirectory;
import static utils.FileHandler.readFile;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InitTest {
    private final String testFilesPath =  "src/test/java/utils/testfiles/";
    final private String rootDirectory = testFilesPath  + "root";
    final String titre = "My Poney Back";
    final String domaine = "Sparkle.com";
    final String description = "Un Lieu Magic où les poney vivent en paix et en harmonie";
    Init i = new Init();

    @Test
    @Order(1)
    /**
     * Lancement de la commande init.
     * Si des fichiers issus d'un précédent test existent déjà, ils sont supprimés
     */
    void call() {

        //Suppression du dossier si il existe déjà
        File f = new File(rootDirectory);
        eraseNotEmptyDirectory(f);

        String input = titre + '\n' + domaine + '\n' + description + '\n';

        /* Src : https://www.codota.com/code/java/methods/java.lang.System/setIn */
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        new CommandLine(i).execute(rootDirectory);

        //Vérifie le nombre de fichier créee;
        assertEquals(i.createFileConfig, true);
        assertEquals(i.createIndex, true);
        assertEquals(i.createRootDirectory, true);

        File[] listFile = f.listFiles();
        assertEquals(listFile.length, 2);//config et index

        //Vérifier le contenu du fichier index.md
        assertEquals(readFile(rootDirectory + '/' + "index.md"), i.getIndex());
    }

    @Test
    @Order(2)
    /**
     * Vérification du fichier de configuration crée au test 1
     */
    void testFileConfig() {
        //Vérification contenu json
        String path = rootDirectory + "/config.json";

        JSONConfig config = new JSONConfig(path);

        // lecture du fichier
        assertTrue(config.read());

        // les attributs ne doivent pas être différents
        assertEquals(titre, config.getTitre());
        assertEquals(domaine, config.getDomaine());
        assertEquals(description, config.getDescription());
    }

    @Test
    @Order(3)
    /**
     * Lancement d'init alors que les fichiers existent déjà
     */
    void testFichierExistant() {
        //Test sur un dossier existant
        new CommandLine(i).execute(rootDirectory);
        assertEquals(i.createFileConfig, false);
        assertEquals(i.createIndex, false);
        assertEquals(i.createRootDirectory, false);
    }
}
