package utils;

import org.junit.jupiter.api.Test;
import utils.Utils;

class createFileTest {
    @Test
    void name() {
        String file ="index.txt";
        Utils.createFile("Le fichier a été crée", file);
    }
}