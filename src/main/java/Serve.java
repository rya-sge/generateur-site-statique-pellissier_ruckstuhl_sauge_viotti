/*
Date : 05.03.2021
Groupe : PRSV
Description : Implémentation cmd Serve
 */

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.io.File;

@CommandLine.Command(
        name = "serve",
        description = "serve cmd"
)

public class Serve implements Runnable{

    @Override
    public void run() {
        System.out.println("Serve command!");
    }
}