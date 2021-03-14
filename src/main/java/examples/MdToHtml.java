package examples;



import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.parser.ParseException;

import java.io.*;

public class MdToHtml {

    public static int main() throws ParseException, IOException {

        Reader in = new FileReader("src/test/java/examples/test.md");
        Writer out = new FileWriter("src/test/java/examples/test.html");

        Markdown md = new Markdown();
        md.transform(in, out);
        return 0;
    }

}
