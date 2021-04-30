/*
Date : 05.03.2021
Modifié: 30.04.2021
Groupe : PRSV
Description : Implémentation cmd Serve fortement inspiré de https://github.com/ianopolous/simple-http-server
 */

import picocli.CommandLine;
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
        File root = new File(rootDirectory);
        File[] lFiles = root.listFiles();
        if(lFiles != null) {
            for (File file : lFiles) {

                // Lancer le serveur, seulement si un dossier build est trouvé dans le root donné.
                if (file.isDirectory() && file.getName().equals("build")) {
                    try {

                        Server.run(rootDirectory + "/build", 8000);

                        // Garder le server ouvert tant que l'utilisateur ne tape pas exit
                        while (true){
                            System.out.println("Ecrire \"exit\" pour arrêter le serveur");
                            exit = sc.nextLine();
                            if (exit.equals("exit")){
                                break;
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // Un problème à eu lieu avec le serveur
                    return 1;
                }
            }
        }
        // Pas de build dans le root ou le root n'est pas un dossier.
        return 2;
    }
}