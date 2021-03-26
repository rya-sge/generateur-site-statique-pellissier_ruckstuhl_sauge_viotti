/*
Date : 05.03.2021
Groupe : PRSV
Description : Implémentation cmd Init
 */

import picocli.CommandLine;
import utils.Contenu;
import utils.FileHandler;
import utils.JSONConfig;
//import utils.Utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.Callable;


import static utils.FileHandler.exists;

@CommandLine.Command(name = "init", description = "Initialiser un site static")
public class Init implements Callable<Integer> {
    //@CommandLine.Option(names = {"-u", "--user"}, description = "User name")
    @CommandLine.Parameters(paramLabel = "<rootDirectory>", description = "Dossier root")
    private String rootDirectory;

    public String getIndex() {
        return index.toString();
    }

    private Contenu index;

    //Nom
    private String indexFileName = "index.md";
    private String configFileName = "config.json";

    //Path
    private String pathIndex = rootDirectory + '/' + indexFileName;
    private String pathFileConfig = rootDirectory + '/' + configFileName;

    //Booléean indiquant si un fichir existe ou pas
    public static boolean createIndex = false;
    public static boolean createRootDirectory = false;
    public static boolean createFileConfig= false;


    Init(){
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String titre = "Mon premier article";
        String date = format.format(calendar.getTime());
        LinkedList<String> c = new LinkedList();
        c.add("# Bienvenue vers le meilleur generateur");
        c.add("## Page d'acceuil");
        c.add("### Votre contenu");
        index = new Contenu(titre, "", date, c);
    }

    /**
     * Source : http://www.codeurjava.com/2015/07/java-obtenir-la-date-et-heure-courante-avec-date-et-calendar.html
     */
    @Override
    public Integer call() {
        System.out.println("Initialisation du site static");
        int nombreFichierCree = 0;

        //RootDirectory
        if(exists(rootDirectory)){
            System.out.println("Le dossier root existe déjà");
        }else{
            File root = new File(rootDirectory);
            root.mkdirs();
            System.out.println("Le dossier root a été créee");
            ++nombreFichierCree;
            createRootDirectory = true;
        }

        //config.json
        if( exists(pathFileConfig)){
            System.out.println("Le fichier de config existe déjà");
        }else{
            String titre = "", description = "", domaine = "";
            JSONConfig config = new JSONConfig(pathFileConfig);
            config.config(titre, domaine, description);
            createIndex = true;
            FileHandler.create(pathFileConfig, getIndex().toString() );
           /* System.out.println("Le fichier de configuration doit créer :");
            Scanner sc = new Scanner(System.in);

            System.out.println("Veuillez saisir un titre:");
            String titre = sc.nextLine();

            System.out.println("Veuillez saisir une description:");
            String description = sc.nextLine();

            System.out.println("Veuillez saisir un domaine:");
            String domaine = sc.nextLine();
            FileHandler.create(pathFileConfig, getIndex().toString() );
            */
        }

        //index.md
        if( exists(pathIndex)){
            System.out.println("index.md existe déjà");

        }else{
            FileHandler.create(pathIndex, getIndex().toString() );
            createFileConfig = true;
            ++nombreFichierCree;

        }


        return nombreFichierCree; //Nombre de fichier créee

    }

}
