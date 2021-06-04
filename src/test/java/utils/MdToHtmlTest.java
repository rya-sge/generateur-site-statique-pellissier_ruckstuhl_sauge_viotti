package ch.heigvd.prsv.utils;

import global.ConstantesTest;
import org.junit.jupiter.api.Test;
import org.tautua.markdownpapers.parser.ParseException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MdToHtmlTest {

    final String SOURCE = "src/test/java/utils/testfiles/MdToHtmlFiles/in.md";
    final String DESTINATION = ConstantesTest.TEST_FOLDER+"MdToHtmlFiles/out.html";

    @Test
    void isMdToHtmlWorking() throws IOException, ParseException {
        MdToHtml.mdTransform(SOURCE, DESTINATION);
        assertTrue(FileHandler.exists(DESTINATION));
        assertNotEquals(FileHandler.readFile(DESTINATION).length(),0);
    }
}