import picocli.CommandLine;
import utils.WatchApiRegister;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class WatchApi {
    String rootDirectory;

    public WatchApi(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    /**
     * Register the given directory and all its sub-directories with the WatchService.
     */
    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories


    }
    public void watch() {
        try{
            Build b = new Build();
            WatchService watchService
                    = FileSystems.getDefault().newWatchService();
            //Path path = Paths.get(rootDirectory);

            Path dir = Paths.get(rootDirectory);
            new WatchApiRegister(dir, true).processEvents();

           /* path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            /*
            Faire un walFileTree => faire un récursif
             */
            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println(
                            "Event kind:" + event.kind()
                                    + ". File affected: " + event.context() + ".");
                   new CommandLine(b).execute(rootDirectory.toString());
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

