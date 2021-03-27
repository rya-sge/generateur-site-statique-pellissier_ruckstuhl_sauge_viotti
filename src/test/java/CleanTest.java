import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;


class CleanTest {

    @Test
    void cleanDoesNotCrashIfRootDoesNotContainsBuildFolder(){
        File root = new File("noBuild");
        root.mkdir();

    }

    @Test
    void cleanDoesNotCrashIfRootisEmpty(){
        File root = new File("emptyFolder");
        root.mkdir();
        Clean c = new Clean();
        c.
        Assertions.assertThrows(RuntimeException.class, () -> {
            c.run();
        });
    }

}