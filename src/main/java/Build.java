import picocli.CommandLine;

public class Build implements Runnable {
    public static void main(String[] args) {
        CommandLine.run(new Build(), args);
    }

    @Override
    public void run() {
        System.out.println("Hello World!");
    }
}
