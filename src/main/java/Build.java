/*
Date : 05.03.2021
Groupe : PRSV
Description : Implémentation class Build
 */

import picocli.CommandLine;

@CommandLine.Command(
        name = "Build",
        description = "Build"
)
public class Build implements Runnable {
    @CommandLine.Parameters(paramLabel = "<nom>", description = "nom du truc à créer")
    private String nom;

    @Override
    public void run() {
        System.out.printf("%s : is that what you wanted, Sir ?", nom);
    }

}



