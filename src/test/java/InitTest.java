import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.readFile;

class InitTest {
    /* https://www.codota.com/code/java/methods/java.lang.System/setIn */

    //Src : https://www.baeldung.com/java-delete-directory

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
    @Test
    void call() {
        String args = "root";

        //Suppression du dossier si il existe déjà
        File f = new File(args);
        deleteDirectory(f);


        String titre = "My Poney Back\nSparkle.com\nUn Lieu Magic où les poney vivent en paix et en harmonie\n";
        InputStream in = new ByteArrayInputStream(titre.getBytes());
        System.setIn(in);

        Init i = new Init();
        new CommandLine(i).execute(args);




        //Vérifie le nombre de fichier créee;
        assertEquals(i.createFileConfig, true);
        assertEquals(i.createIndex, true);
        assertEquals(i.createRootDirectory, true);

        File[] listFile = f.listFiles();
        assertEquals(listFile.length, 2);//config et index

        //Vérifier le contenu du fichier index.md
        assertEquals(readFile(new File(args + '/' + "index.md")), i.getIndex());
    }
}
