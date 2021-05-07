/*
Date : 05.03.2021
Modifié: 30.04.2021
Groupe : PRSV
Description : Implémentation cmd Serve. Le server se lance sur le port 8282
 */

import picocli.CommandLine;
import utils.FileHandler;

import java.util.Scanner;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "serve",
        description = "serve cmd"
)

class Serve implements Callable<Integer> {

    @CommandLine.Parameters(paramLabel = "<rootDirectory>")
    private String rootDirectory;

    Scanner sc = new Scanner(System.in);
    String exit = new String();

    @Override
    public Integer call() {
        if (!FileHandler.exists(rootDirectory)){
            // L'élément n'existe pas
            return 1;
        }
        File root = new File(rootDirectory);
        if (!root.isDirectory()){
            // L'élément n'est pas un dossier
            return 2;
        }
        File[] lFiles = root.listFiles();
        if(lFiles != null) {
            for (File file : lFiles) {

                // Lancer le serveur, seulement si un dossier build est trouvé dans le root donné.
                if (file.isDirectory() && file.getName().equals("build")) {
                    try {

                        Server.run(rootDirectory + "/build", 8080);

                        // Garder le server ouvert tant que l'utilisateur ne tape pas exit
                        while (true){
                            System.out.println("Ecrire \"exit\" pour arrêter le serveur");
                            exit = sc.nextLine();
                            if (exit.equals("exit")){
                                // Le processus a été terminé par l'utilisateur
                                return 0;
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        // un probléme à eu lieu avec le serveur
                        return 4;
                    }
                }
            }
        }
        // Le root ne contient pas build.
        return 3;
    }
}