/*
Date : 05.03.2021
Groupe : PRSV
 */
import picocli.CommandLine;
import utils.Version;

@CommandLine.Command(
        name="statique",
        versionProvider = Version.class,
        description = "Générateur de site statique développé dans le cadre du cours GEN ",
        mixinStandardHelpOptions = true,
        subcommands={New.class, Serve.class, Build.class, Clean.class, Init.class}
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
