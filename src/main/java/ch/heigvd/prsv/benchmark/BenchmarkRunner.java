package ch.heigvd.prsv.benchmark;
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
    @Benchmark @BenchmarkMode(Mode.AverageTime)
    public void init() {
        new CommandLine(new Build()).execute(dir.toString());
    }
    public static void main(String[] args) throws Exception {
        File dossier = new File(dir);
        FileHandler.eraseNotEmptyDirectory(dossier);
        dossier.mkdir();
        new CommandLine(new Init()).execute(dossier.toString());
        org.openjdk.jmh.Main.main(args);
    }
}
