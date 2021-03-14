import static org.junit.jupiter.api.Assertions.assertTrue;

class WriteJSONTest {

    @org.junit.jupiter.api.Test
    void tryToWrite() {

        String path = "test-temp.json";
        String name = "Test Site";
        String domain = "test.com";
        WriteJSON writer = new WriteJSON(path);

        assertTrue(writer.writeFile(name, domain));
    }

}