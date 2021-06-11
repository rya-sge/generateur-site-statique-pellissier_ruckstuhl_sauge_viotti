package ch.heigvd.prsv.benchmark;

/*
Date : 04.06.2021
Groupe : PRSV
Description : Effectuer un benchmark avec JMH
 */
import ch.heigvd.prsv.utils.FileHandler;
import ch.heigvd.prsv.Build;
import ch.heigvd.prsv.Init;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import picocli.CommandLine;

import java.io.File;

public class BenchmarkRunner {
    static String dir = "./benchmark";

    @Benchmark @BenchmarkMode(Mode.AverageTime) // Changer selon le mode souhaité
    public void init() {
        new CommandLine(new Build()).execute(dir.toString());
    }


    //Permet de lancer le benchmark de façon indépendante au reste de l'application
    public static void main(String[] args) throws Exception {
        File dossier = new File(dir);
        FileHandler.eraseNotEmptyDirectory(dossier);
        dossier.mkdir();
        new CommandLine(new Init()).execute(dossier.toString());
        org.openjdk.jmh.Main.main(args);
    }
}
