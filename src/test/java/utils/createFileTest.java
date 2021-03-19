package utils;

import org.junit.jupiter.api.Test;
import utils.FileHandler;

class createFileTest {
    @Test
    void name() {
        String file ="index.txt";
        FileHandler.createFile("Le fichier a été crée", file);
    }
}