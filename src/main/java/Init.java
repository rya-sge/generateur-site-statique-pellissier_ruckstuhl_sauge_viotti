/*
Date : 05.03.2021
Groupe : PRSV
Description : Implémentation cmd Init
 */

import picocli.CommandLine;
import utils.Contenu;
import utils.FileHandler;
import utils.JSONConfig;

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
    @CommandLine.Parameters(paramLabel = "<rootDirectory>", description = "Dossier root")
    private String rootDirectory;

    /**
     * @return
     */
    public String getIndex() {
        return index.toString();
    }

    private Contenu index;

    //Nom des fichiers
    private String indexFileName = "index.md";
    private String configFileName = "config.json";


    //Booléean indiquant si un fichir existe ou pas
    public boolean createIndex = false;
    public boolean createRootDirectory = false;
    public boolean createFileConfig = false;


    /**
     * Source : http://www.codeurjava.com/2015/07/java-obtenir-la-date-et-heure-courante-avec-date-et-calendar.html
     */
    void createIndex() {
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
     * @return
     */
    @Override
    public Integer call() {
        //Init
        createIndex = false;
        createRootDirectory = false;
        createFileConfig = false;

        //Path
        String pathIndex = rootDirectory + '/' + indexFileName;
        String pathFileConfig = rootDirectory + '/' + configFileName;
        System.out.println("Initialisation du site static");

        //RootDirectory
        if (exists(rootDirectory)) {
            System.out.println("Le dossier root existe déjà");
        } else {
            File root = new File(rootDirectory);
            root.mkdirs();
            System.out.println("Le dossier root a été créee");
            createRootDirectory = true;
        }

        //Fichier de configuration
        if (exists(pathFileConfig)) {
            System.out.println("Le fichier de config existe déjà");
        } else {
            JSONConfig config = new JSONConfig(pathFileConfig);
            System.out.println("Le fichier de configuration doit être crée :");
            Scanner sc = new Scanner(System.in);

            //Titre
            System.out.println("Veuillez saisir un titre:");
            String titre = sc.nextLine();

            //Domaine
            System.out.println("Veuillez saisir un domaine:");
            String domaine = sc.nextLine();

            //Description
            System.out.println("Veuillez saisir une description:");
            String description = sc.nextLine();

            //Ecriture du fichier
            config.config(titre, domaine, description);
            config.write();
            createFileConfig = true;
        }

        //Index du site
        if (exists(pathIndex)) {
            System.out.println("index.md existe déjà");

        } else {
            createIndex();
            FileHandler.create(pathIndex, getIndex().toString());
            createIndex = true;
        }

        return 1;
    }

}
