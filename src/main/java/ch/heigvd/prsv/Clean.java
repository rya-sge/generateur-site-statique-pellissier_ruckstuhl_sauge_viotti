package ch.heigvd.prsv;/*
Date : 05.03.2021
Groupe : PRSV
Description : Implémentation cmd Clean
 */

import picocli.CommandLine;
import ch.heigvd.prsv.utils.FileHandler;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "clean",
        description = "nettoie le site statique"
)
public
class Clean implements Callable<Integer> {

    @CommandLine.Parameters(paramLabel = "<rootDirectory>", description = "Dossier root du site à effacer")
    private String rootDirectory;

    @Override
    public Integer call() {
        File root = new File(rootDirectory);
        File[] lFiles = root.listFiles();
        if(lFiles != null) {
            for (File file : lFiles) {
                if (file.isDirectory() && file.getName().equals("build")) {
                    FileHandler.eraseNotEmptyDirectory(file);
                    return 1;
                }
            }
        }
        return 0;
    }
}
