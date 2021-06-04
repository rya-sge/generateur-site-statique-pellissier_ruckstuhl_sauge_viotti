package ch.heigvd.prsv.utils;

import global.ConstantesTest;

import static org.junit.jupiter.api.Assertions.*;

class JSONConfigTest {

    @org.junit.jupiter.api.Test
    void WriteAndReadShouldBeCorrect() {
        String path = ConstantesTest.TEST_FOLDER+"testConfig.json";

        JSONConfig config = new JSONConfig(path);

        String titre = "My Little Pony";
        String domaine = "BestPonyEver.com";
        String description = "Friendship is Magic";
        config.config(titre, domaine, description);

        assertEquals(titre, config.getTitre());
        assertEquals(domaine, config.getDomaine());

        // écriture du fichier
        assertTrue(config.write());

        // lecture du fichier
        assertTrue(config.read());

        // les attributs ne doivent pas être différents
        assertEquals(titre, config.getTitre());
        assertEquals(domaine, config.getDomaine());


    }

}
