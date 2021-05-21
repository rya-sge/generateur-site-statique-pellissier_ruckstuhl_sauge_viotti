import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.*;

public class WatchApi {
    String rootDirectory;

    public WatchApi(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public void watch() {
        try{
            Build b = new Build();
            WatchService watchService
                    = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(rootDirectory);

            path.register(
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

