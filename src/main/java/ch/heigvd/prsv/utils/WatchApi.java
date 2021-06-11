package ch.heigvd.prsv.utils;
/*
Date : 03.06.2021
Groupe : PRSV
Description : Watch Api pour le serveur http

Sources :
Le code utilisé provient de la documentation officielle d'Oracle :
https://docs.oracle.com/javase/tutorial/essential/io/notification.html

Remarques :
Les system.out-println ont été laissé à des fin de debuggage.
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

    /**
     * Classe interne pour enregistrer les répertoires
     */
    public class WatchApiRegister {

        private final WatchService watcher;
        private final Map<WatchKey, Path> keys;
        private boolean trace = false;
        private boolean recursive = false;

        @SuppressWarnings("unchecked")
        <T> WatchEvent<T> cast(WatchEvent<?> event) {
            return (WatchEvent<T>) event;
        }

        /**
         * Enregistre le dossier donné avec le WatchService
         * @param dir dossier à enregistrer
         * @throws IOException si le dossier n'est pas valide
         */
        private void register(Path dir) throws IOException {
            WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            if (trace) {
                keys.get(key);
                //A décommenter pour le debug si besoin
                //Path prev = keys.get(key);
                /*if (prev == null) {
                    System.out.format("register: %s\n", dir);
                } else {
                    if (!dir.equals(prev)) {
                        System.out.format("update: %s -> %s\n", prev, dir);
                    }
                }*/
            }
            keys.put(key, dir);
        }

        /**
         * Enregistrer le répertoire ainsi que les sous-répertoires avec le watch service
         * @param start dossier de départ
         * @throws IOException si un dossier n'existe pas
         */
        private void registerAll(final Path start) throws IOException {
            // Enreigstrer les répertoires et sous-répertoires
            Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                        throws IOException {
                    if (!dir.getFileName().toString().contains("build")) {
                        register(dir);
                        // System.out.println("Not : ignore : " + dir.getFileName());
                        return FileVisitResult.CONTINUE;

                    } else {
                        // System.out.println("ignore : " + dir.getFileName());
                        return FileVisitResult.SKIP_SUBTREE;
                    }

                }
            });
        }

        /**
         * Crée un WatchService et enregistre le dossier donné
         * @param dir dossier à passer en paramètre
         * @param recursive activer ou non la récursivité
         * @throws IOException lorsqu'un dossier n'est pas valide
         */
        public WatchApiRegister(Path dir, boolean recursive) throws IOException {
            this.watcher = FileSystems.getDefault().newWatchService();
            this.keys = new HashMap<WatchKey, Path>();
            this.recursive = recursive;

            if (recursive) {
                // System.out.format("Scanning %s ...\n", dir);
                registerAll(dir);
                // System.out.println("Done.");
            } else {
                if (!dir.getFileName().toString().contains("build")) {
                    register(dir);
                    // System.out.println("Not : ignore : " + dir.getFileName());
                }
            }

            // enable trace after initial registration
            this.trace = true;
        }

        /**
         * Traite tous les événements passés dans la file
         * @throws InterruptedException lorsque le thread est interrompu de manière inattendue
         */
        public void processEvents() throws InterruptedException {
            WatchKey key;
            while ((key = watcher.take()) != null && !inWorking) {
                for (WatchEvent<?> event : key.pollEvents()) {

                        /*System.out.println(
                                "Event kind:" + event.kind()
                                        + ". File affected: " + event.context() + ".");*/
                    new CommandLine(WatchApi.this.b).execute(WatchApi.this.rootDirectory.toString());
                    inWorking = true;
                    break;
                }
                key.reset();
            }
            inWorking = false;
        }
    }

    /**
     * @param rootDirectory dossier à observer
     */
    public WatchApi(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }


    /**
     * Fonction principale
     */
    public void watch() {
        try {
            while (true) {
                Path dir = Paths.get(rootDirectory);
                new WatchApiRegister(dir, true).processEvents();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(InterruptedException e){
            System.out.println("La WatchAPI s'est arrêtée avec succès");
            Thread.currentThread().interrupt();
        }
    }
}

