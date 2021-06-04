package utils;

import static org.junit.jupiter.api.Assertions.*;

import ch.heigvd.prsv.utils.FileHandler;
import global.ConstantesTest;

class FileHandlerTest {
    private final String toReadFiles =  "src/test/java/utils/testfiles/";
    private final String toCreateFiles =  ConstantesTest.TEST_FOLDER;

    @org.junit.jupiter.api.Test
    void ReadShouldBeCorrect() {

        String expectedString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed pulvinar enim vitae dolor sodales ultricies. Vestibulum auctor id lacus quis vestibulum. Duis dictum gravida ante, mollis bibendum leo vestibulum at. Mauris dapibus tortor eros, malesuada laoreet turpis ultrices ut. Donec iaculis risus neque, a ullamcorper tortor semper sit amet. Suspendisse vitae est enim. Vestibulum molestie aliquet enim, in accumsan nibh. Suspendisse potenti. Ut metus diam, cursus nec nulla id, congue aliquam velit. Pellentesque elementum pretium mauris, eget rutrum velit congue eget.";
        String filename = toReadFiles +"loremIpsum.txt";

        String returnedString = FileHandler.readFile(filename);

        assertEquals(expectedString, returnedString);
    }

    @org.junit.jupiter.api.Test
    void ShouldCreateNewFileAndWriteInIt() {
        String testString = "Pack my box with five dozen liquor jugs.";
        String filename = toCreateFiles +"liquor.txt";

        assertTrue(FileHandler.create(filename, testString));
        assertTrue(FileHandler.exists(filename));
        assertEquals(FileHandler.readFile(filename), testString);
    }

    @org.junit.jupiter.api.Test
    void WriteThenReadShouldBeCorrect() {

        String testString = "The quick brown fox jumps over the lazy dog ···";

        String filename = toCreateFiles +"fox.txt";

        assertTrue(FileHandler.create(filename, testString));
        assertTrue(FileHandler.exists(filename));
        assertEquals(FileHandler.readFile(filename), testString);
    }

    @org.junit.jupiter.api.Test
    void ShouldCreateDirectory() {

        String filename = toCreateFiles +"dir/test.txt";

        assertTrue(FileHandler.create(filename));

        assertTrue(FileHandler.exists(filename));
    }


}