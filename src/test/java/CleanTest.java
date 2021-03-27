import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CleanTest {

    @Test
    void cleanCallReturn0IfRootDoesNotExist(){
        String rootName = "notExist";
        Clean c = new Clean();
        Integer val = new CommandLine(c).execute(rootName);

        assertEquals(val, 0);
    }

    @Test
    void cleanCallReturn0IfFolderEmpty(){
        String rootName = "emptyFolder";
        File root = new File(rootName);
        root.mkdir();
        Clean c = new Clean();
        Integer val = new CommandLine(c).execute(rootName);

        assertEquals(val, 0);
    }

    @Test
    void cleanCallReturn0IfFolderDoesNotContainsBuild(){

        String rootName = "noBuild";
        File root = new File(rootName);
        root.mkdir();

        String subRoot = rootName + "/subDir";
        String subRoot2 = rootName + "/subDir2";
        String file1 = rootName + "/file1.txt";

        File subRootF = new File(subRoot);
        File subRoot2F = new File(subRoot2);
        File file1F = new File(file1);

        subRootF.mkdir();
        subRoot2F.mkdir();
        try {
            file1F.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Clean c = new Clean();
        Integer val = new CommandLine(c).execute(rootName);

        assertEquals(val, 0);
    }

    @Test
    void cleanCallReturn1IfFolderContainsBuild(){

        String rootName = "withBuild";
        File root = new File(rootName);
        root.mkdir();

        String subRoot = rootName + "/subDir";
        String subRoot2 = rootName + "/subDir2";
        String build = rootName + "/build";
        String subBuild = build + "/subBuild";
        String file1 = rootName + "/file1.txt";
        String file2 = subRoot + "/file2.txt";
        String file3 = build + "/file3.txt";
        String file4 = subBuild + "/file4.txt";

        File subRootF = new File(subRoot);
        File subRoot2F = new File(subRoot2);
        File buildF = new File(build);
        File subBuildF = new File(subBuild);
        File file1F = new File(file1);
        File file2F = new File(file2);
        File file3F = new File(file3);
        File file4F = new File(file4);

        subRootF.mkdir();
        subRoot2F.mkdir();
        buildF.mkdir();
        subBuildF.mkdir();
        try {
            file1F.createNewFile();
            file2F.createNewFile();
            file3F.createNewFile();
            file4F.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Clean c = new Clean();
        Integer val = new CommandLine(c).execute(rootName);

        assertEquals(val, 1);
    }
}