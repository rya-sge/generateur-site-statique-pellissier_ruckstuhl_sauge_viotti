/*
Date : 07.05.2021
Groupe : PRSV
Description : Vérifie les valeurs de retour de Clean,java
 */

import ch.heigvd.prsv.Serve;
import global.ConstantesTest;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import ch.heigvd.prsv.utils.FileHandler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServeTest {
    private final String testFilesPath =  ConstantesTest.TEST_FOLDER + "Serve/";

    @Test
    void serveCallReturn1IfRootDoesNotExist(){
        File rootPath = new File(testFilesPath);
        rootPath.mkdirs();

        String rootName = testFilesPath + "notExist/";
        Serve s = new Serve();
        Integer val = new CommandLine(s).execute(rootName);

        FileHandler.eraseNotEmptyDirectory(rootPath);
        assertEquals(val, 1);
    }

    @Test
    void serveCallReturn2IfRootIsNotDirectory(){
        File rootPath = new File(testFilesPath);
        rootPath.mkdirs();

        String newFile = testFilesPath + "notDirectory";
        FileHandler.create(newFile);

        Serve s = new Serve();
        Integer val = new CommandLine(s).execute(newFile);

        FileHandler.eraseNotEmptyDirectory(rootPath);
        assertEquals(val, 2);
    }

    @Test
    void serveCallReturn3IfFolderEmpty(){
        String rootName = testFilesPath + "emptyFolder/";
        File rootPath = new File(rootName);
        rootPath.mkdirs();

        Serve s = new Serve();
        Integer val = new CommandLine(s).execute(rootName);

        FileHandler.eraseNotEmptyDirectory(new File(testFilesPath));
        assertEquals(val, 3);
    }

    @Test
    void serveCallReturn3IfFolderDoesNotContainsBuild(){

        String rootName = testFilesPath + "noBuild";
        File root = new File(rootName);
        root.mkdirs();

        String subRoot = rootName + "/subDir";
        String subRoot2 = rootName + "/subDir2";
        String file1 = rootName + "/file1.txt";

        File subRootF = new File(subRoot);
        File subRoot2F = new File(subRoot2);
        File file1F = new File(file1);

        subRootF.mkdirs();
        subRoot2F.mkdirs();
        try {
            file1F.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Serve s = new Serve();
        Integer val = new CommandLine(s).execute(rootName);

        FileHandler.eraseNotEmptyDirectory(new File(testFilesPath));
        assertEquals(val, 3);
    }

    @Test
    void serveCallReturn0IfServerIsLaunchedAndUserCouldExitIt(){

        String rootName = testFilesPath + "withBuild";
        File root = new File(rootName);
        root.mkdirs();

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

        subRootF.mkdirs();
        subRoot2F.mkdirs();
        buildF.mkdirs();
        subBuildF.mkdirs();
        try {
            file1F.createNewFile();
            file2F.createNewFile();
            file3F.createNewFile();
            file4F.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayInputStream in = new ByteArrayInputStream("exit".getBytes());
        System.setIn(in);

        Serve s = new Serve();
        Integer val = new CommandLine(s).execute(rootName);

        FileHandler.eraseNotEmptyDirectory(new File(testFilesPath));
        assertEquals(val, 0);
    }
}