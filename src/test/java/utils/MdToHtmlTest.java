package utils;

import org.junit.jupiter.api.Test;
import org.tautua.markdownpapers.parser.ParseException;

import java.io.IOException;



class MdToHtmlTest {

    final String SOURCE = "src/test/java/utils/MdToHtmlFiles/in.md";
    final String DESTINATION = "src/test/java/utils/MdToHtmlFiles/out.html";

    @Test
    void isMdToHtmlWorking() throws IOException, ParseException {
        MdToHtml.mdTransform(SOURCE, DESTINATION);
    }
}