/*
Date : 14.03.2021
Groupe : PRSV
Description : Test cmd Init
*/

import ch.heigvd.prsv.Constantes;
import ch.heigvd.prsv.Init;
import global.ConstantesTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import picocli.CommandLine;
import ch.heigvd.prsv.utils.JSONConfig;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static ch.heigvd.prsv.utils.FileHandler.eraseNotEmptyDirectory;
import static ch.heigvd.prsv.utils.FileHandler.readFile;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InitTest {
    private final String testFilesPath =  ConstantesTest.TEST_FOLDER;
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
        File fileRoot = new File(rootDirectory);
        eraseNotEmptyDirectory(fileRoot);

        String input = titre + '\n' + domaine + '\n' + description + '\n';

        /* Src : https://www.codota.com/code/java/methods/java.lang.System/setIn */
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        new CommandLine(i).execute(rootDirectory);

        //Vérifie le nombre de fichier créee;
        assertTrue(i.createFileConfig);
        assertTrue(i.createIndex);
        assertTrue(i.createRootDirectory);
        assertTrue(i.createLayout);
        assertTrue(i.createMenu);
        assertTrue(i.createPage);
        assertTrue(i.createImage);

        File[] listFile = fileRoot.listFiles();
        assertEquals(listFile.length, 4);//config, index, content et template

        File fileContent = new File(rootDirectory + '/' + Constantes.CONTENT_DIRECTORY);
        listFile = fileContent.listFiles();
        assertEquals(listFile.length, 2); //CONTENT_DIRECTORY contient 2 fichiers


        File fileTemplate = new File(rootDirectory + '/' + Constantes.TEMPLATE_DIRECTORY);
        listFile = fileTemplate.listFiles();
        assertEquals(listFile.length, 2); //menu et layout

        //Vérifier le contenu du fichier INDEX_FILE_NAME
        assertEquals(readFile(rootDirectory + '/' + Constantes.INDEX_FILE_NAME), i.getIndex());

        //Vérifier le contenu du fichier PAGE_PATH
        assertEquals(readFile(rootDirectory + '/' + Constantes.PAGE_PATH), i.getPage());
    }

    @Test
    @Order(2)
    /**
     * Vérification du fichier de configuration crée au test 1
     */
    void testFileConfig() {
        //Vérification contenu json
        String path = rootDirectory + '/' + Constantes.CONFIG_FILE_NAME;

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
        assertFalse(i.createFileConfig);
        assertFalse(i.createIndex);
        assertFalse(i.createRootDirectory);
        assertFalse(i.createLayout);
        assertFalse(i.createMenu);
        assertFalse(i.createContentDirectory);
        assertFalse(i.createPage);
        assertFalse(i.createImage);
    }
}
