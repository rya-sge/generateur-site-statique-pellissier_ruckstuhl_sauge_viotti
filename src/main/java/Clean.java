import picocli.CommandLine;

@CommandLine.Command(
        name = "Clean",
        description = "clean un truc"
)

class Clean implements Runnable {
    public static void main(String[] args) {
        CommandLine.run(new Clean(), args);
        }

        @Override
        public void run() {
            System.out.println("Hello World!");
        }
}

