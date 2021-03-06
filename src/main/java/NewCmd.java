import picocli.CommandLine;

@CommandLine.Command(
        name = "new",
        description = "créer un nouveau truc"
)
public class NewCmd implements Runnable{

    @CommandLine.Parameters(paramLabel = "<nom>", description = "nom du truc à créer")
    private String nom;

    @Override
    public void run() {
        System.out.printf("%s : is that what you wanted, Sir ?", nom);
    }
}
