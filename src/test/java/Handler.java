import global.ConstantesTest;
import org.junit.Test;

import static utils.FileHandler.eraseNotEmptyDirectory;
import static utils.FileHandler.readFile;

public class Handler {
    private final String testFilesPath =  ConstantesTest.TEST_FOLDER;
    @Test
    void test(){
        String fileResultat  = "pageModel.html";
        String fichierCree =  testFilesPath + "/page.html";
        String content = readFile(fileResultat);
    }

}
