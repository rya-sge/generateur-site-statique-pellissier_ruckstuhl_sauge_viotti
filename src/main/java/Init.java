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

    //Booléean indiquant si un fichier existe ou pas
    //Util pour les tests
    public  boolean createIndex = false;
    public boolean createRootDirectory = false;
    public boolean createFileConfig = false;
    public boolean createLayout  = false;
    public boolean createMenu = false;
    public boolean createContentDirectory = false;

    private String layoutContent =  "<html lang=\"en\">\n" +
            "<head>\n" +
            "<meta charset=\"utf-8\">\n" +
            "<title>{{ site.titre }} | {{ page.titre }}</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "{% include menu.html }\n" +
            "{{ content }}\n" +
            "</body>\n" +
            "</html>";

    private String menuContent =
            "<ul>\n" +
            "<li><a href=\"/index.html\">home</a></li>\n" +
            "<li><a href=\"/content/page.html\">page</a></li>\n" +
            "</ul>";
    /**
     * Source : http://www.codeurjava.com/2015/07/java-obtenir-la-date-et-heure-courante-avec-date-et-calendar.html
     */
    void createIndex() {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String titre = "Mon premier article";
        String date = format.format(calendar.getTime());
        LinkedList<String> c = new LinkedList();
        c.add("# Bienvenue vers le meilleur générateur");
        c.add("## Page d'accueil");
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
        createLayout = false;
        createContentDirectory = false;
        //Path
        String pathIndex = rootDirectory + '/' + Constantes.INDEX_FILE_NAME;
        String pathFileConfig = rootDirectory + '/' + Constantes.CONFIG_FILE_NAME;
        String pathLayout = rootDirectory + '/' + Constantes.LAYOUT_FILE_NAME;
        String pathMenu = rootDirectory + '/' + Constantes.MENU_FILE_NAME;
        String pathContent = rootDirectory + '/' + Constantes.CONTENT_DIRECTORY;

        System.out.println("Initialisation du site statique");

        //RootDirectory
        if (exists(rootDirectory)) {
            System.out.println("Le dossier root existe déjà");
        } else {
            File root = new File(rootDirectory);
            root.mkdirs();
            System.out.println("Le dossier root a été créé");
            createRootDirectory = true;
        }

        //Fichier de configuration
        if (exists(pathFileConfig)) {
            System.out.println("Le fichier de configuaration existe déjà");
        } else {
            JSONConfig config = new JSONConfig(pathFileConfig);
            System.out.println("Le fichier de configuration doit être créé :");
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
            System.out.println(Constantes.INDEX_FILE_NAME + " existe déjà");

        } else {
            createIndex();
            FileHandler.create(pathIndex, getIndex());
            createIndex = true;
        }

        //Layout
        if (exists(pathLayout)) {
            System.out.println(Constantes.LAYOUT_FILE_NAME + " existe déjà");

        } else {
            FileHandler.create(pathLayout, layoutContent);
            createLayout = true;
        }

        //Menu
        if (exists(pathMenu)) {
            System.out.println(Constantes.MENU_FILE_NAME + " existe déjà");

        } else {
            FileHandler.create(pathMenu, menuContent);
            createMenu = true;
        }

        // Content
        if (exists(pathContent)) {
            System.out.println("Le dossier" + Constantes.CONTENT_DIRECTORY + " existe déjà");

        } else {
            new File(pathContent).mkdir( );
            createContentDirectory = true;
        }
        return 1;
    }

}
