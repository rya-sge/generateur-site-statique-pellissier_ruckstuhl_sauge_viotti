/*
Date : 05.03.2021
Groupe : PRSV
 */

import picocli.CommandLine;

@CommandLine.Command(
        name="lab02",
        version = "v. lab02 GEN",
        description = "Interdum et malesuada fames ac ante ipsum primis in faucibus. ",
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
