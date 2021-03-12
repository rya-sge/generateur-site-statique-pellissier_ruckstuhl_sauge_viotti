/*
Date : 05.03.2021
Groupe : PRSV
 */

import picocli.CommandLine;

@CommandLine.Command(
        name="statique",
        version = "v0.0.1",
        description = "Générateur de site statique développé dans le cadre du cours GEN ",
        mixinStandardHelpOptions = true,
        subcommands={New.class, Serve.class, Build.class, Clean.class}
)
public class Main implements Runnable {
    public static void main(String[] args ){
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run(){
        System.out.println("Hello, world ");

    }
}
