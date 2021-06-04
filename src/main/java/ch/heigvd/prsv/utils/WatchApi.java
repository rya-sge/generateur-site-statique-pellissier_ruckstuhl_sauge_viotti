package ch.heigvd.prsv.utils;/*
Date : 03.06.2021
Groupe : PRSV
Description : Watch Api pour le serveur http

Sources :
Le code utilisé provient de la documentation officielle d'Oracle :
https://docs.oracle.com/javase/tutorial/essential/io/notification.html
 */
import ch.heigvd.prsv.Build;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import static java.nio.file.StandardWatchEventKinds.*;

public class WatchApi {
    Build b = new Build();
    boolean inWorking = false;
    String rootDirectory;

    public class WatchApiRegister {

        private final WatchService watcher;
        private final Map<WatchKey,Path> keys;
        private boolean trace = false;
        private  boolean recursive = false;
        @SuppressWarnings("unchecked")
        <T> WatchEvent<T> cast(WatchEvent<?> event) {
            return (WatchEvent<T>)event;
        }

        /**
         * Register the given directory with the WatchService
         */
        private void register(Path dir) throws IOException {
            WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            if (trace) {
                Path prev = keys.get(key);
                if (prev == null) {
                    System.out.format("register: %s\n", dir);
                } else {
                    if (!dir.equals(prev)) {
                        System.out.format("update: %s -> %s\n", prev, dir);
                    }
                }
            }
            keys.put(key, dir);
        }

        /**
         * Register the given directory, and all its sub-directories, with the
         * WatchService.
         */
        private void registerAll(final Path start) throws IOException {
            // register directory and sub-directories
            Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                        throws IOException
                {
                    if(!dir.getFileName().toString().contains("build")){
                        register(dir);
                        System.out.println("Not : ignore : " + dir.getFileName());
                        return FileVisitResult.CONTINUE;

                    }else{
                        System.out.println("ignore : " + dir.getFileName());
                        return FileVisitResult.SKIP_SUBTREE;
                    }

                }
            });
        }

        /**
         * Creates a WatchService and registers the given directory
         */
        public WatchApiRegister(Path dir, boolean recursive) throws IOException {
            this.watcher = FileSystems.getDefault().newWatchService();
            this.keys = new HashMap<WatchKey,Path>();
            this.recursive = recursive;

            if (recursive) {
                System.out.format("Scanning %s ...\n", dir);
                registerAll(dir);
                System.out.println("Done.");
            } else {
                if(!dir.getFileName().toString().contains("build")){
                    register(dir);
                    System.out.println("Not : ignore : " + dir.getFileName());
                }
            }

            // enable trace after initial registration
            this.trace = true;
        }

        /**
         * Process all events for keys queued to the watcher
         */
        public void processEvents() throws InterruptedException {
            WatchKey key;
            while ((key = watcher.take()) != null && !inWorking) {
                for (WatchEvent<?> event : key.pollEvents()) {

                        System.out.println(
                                "Event kind:" + event.kind()
                                        + ". File affected: " + event.context() + ".");
                        new CommandLine(WatchApi.this.b).execute(WatchApi.this.rootDirectory.toString());
                    inWorking = true;
                    break;
                }
                key.reset();
            }
            inWorking = false;
        }



    }


    public WatchApi(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }


    /**
     * Fonction principale
     */
    public void watch() {
        try{
            while(true){
                Path dir = Paths.get(rootDirectory);
                new WatchApiRegister(dir, true).processEvents();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

