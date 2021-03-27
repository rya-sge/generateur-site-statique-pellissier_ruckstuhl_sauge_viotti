/*
Date : 05.03.2021
Groupe : PRSV
Description : Implémentation cmd Clean
 */

import picocli.CommandLine;

import java.io.File;

@CommandLine.Command(
        name = "clean",
        description = "nettoie le site statique"
)

class Clean implements Runnable {

    @CommandLine.Parameters(paramLabel = "<rootDirectory>", description = "Dossier root du site à effacer")
    private String rootDirectory;

    /**
     * Efface un dossier et son contenu.
     * @param directory le dossier à effacer.
     */
    public void eraseNotEmptyDirectory(File directory){
        File[] lFiles = directory.listFiles();
        if (lFiles != null){
            for (File file: lFiles){
                if (file.isDirectory()){
                    eraseNotEmptyDirectory(file);
                }else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

    @Override
    public void run() {

        boolean buildNotFound = true;
        File root = new File(rootDirectory);
        File[] lFiles = root.listFiles();
        if(lFiles != null){
            for (File file: lFiles){
                if (file.isDirectory() && file.getName().equals("build")){
                    eraseNotEmptyDirectory(file);
                    System.out.println("build directory deleted");
                    buildNotFound = false;
                }
            }
            if (buildNotFound){
                System.out.println("directory build not found");
            }
        }else{
            System.out.println("Dossier vide");
        }
    }
}
