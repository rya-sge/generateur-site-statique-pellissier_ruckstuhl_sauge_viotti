import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.io.File;

public class Serve implements Runnable{
    public static void main(String[] args) {
        CommandLine.run(new Serve(), args);
    }

    @Override
    public void run() {
        System.out.println("Serve command!");
    }
}