package utils;

import utils.JSONConfig;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JSONConfigTest {

    @org.junit.jupiter.api.Test
    void WriteAndReadShouldBeCorrect() {
        String path = "src/test/testConfig.json";

        JSONConfig config = new JSONConfig(path);

        String name = "Test Site";
        String domain = "test.com";

        config.config(name, domain);

        assertEquals(name, config.getName());
        assertEquals(domain, config.getDomain());

        // écriture du fichier
        assertTrue(config.write());

        // lecture du fichier
        assertTrue(config.read());

        // les attributs ne doivent pas être différents
        assertEquals(name, config.getName());
        assertEquals(domain, config.getDomain());


    }

}