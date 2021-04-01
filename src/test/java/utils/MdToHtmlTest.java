package utils;

import global.ConstantesTest;
import org.junit.jupiter.api.Test;
import org.tautua.markdownpapers.parser.ParseException;

import java.io.IOException;

class MdToHtmlTest {

    final String SOURCE = "src/test/java/utils/testfiles/MdToHtmlFiles/in.md";
    final String DESTINATION = ConstantesTest.TEST_FOLDER+"MdToHtmlFiles/out.html";

    @Test
    void isMdToHtmlWorking() throws IOException, ParseException {
        MdToHtml.mdTransform(SOURCE, DESTINATION);
    }
}