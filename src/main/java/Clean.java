import picocli.CommandLine;

class Clean implements Runnable {
    public static void main(String[] args) {
        CommandLine.run(new clean(), args);
        }

        @Override
        public void run() {
            System.out.println("Hello World!");
        }
}

