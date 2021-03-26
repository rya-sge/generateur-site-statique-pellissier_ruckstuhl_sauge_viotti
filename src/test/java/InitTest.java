import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Utils.readFile;

class InitTest {

    @Test
    void call() {
        String args = "root";
        Init i = new Init();
        Integer valRetour = new CommandLine(i).execute(args);
        String input ="test";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        File f = new File(args);
        File[] listFile = f.listFiles();

        //Vérifie le nombre de fichier créee;
        assertEquals(valRetour, listFile.length);
        //Vérifier le contenu du fichier
        assertEquals(readFile(new File(args + '/' + "index.md")), i.getIndex());
    }
}
