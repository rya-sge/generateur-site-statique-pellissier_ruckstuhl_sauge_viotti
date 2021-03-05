import picocli.CommandLine;

class HelloWorldCommand implements Runnable {
    public static void main(String[] args) {
        CommandLine.run(new HelloWorldCommand(), args);
        }

        @Override
        public void run() {
            System.out.println("Hello World!");
        }
}

