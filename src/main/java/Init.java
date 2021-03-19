/*
Date : 05.03.2021
Groupe : PRSV
Description : Implémentation cmd Init
 */

import picocli.CommandLine;
import utils.Contenu;
import utils.Utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.concurrent.Callable;


@CommandLine.Command(name = "init", description = "Initialiser un site static")
public class Init implements Callable<Integer> {
    //@CommandLine.Option(names = {"-u", "--user"}, description = "User name")
    @CommandLine.Parameters(paramLabel = "<rootDirectory>", description = "Dossier root")
    private String rootDirectory;

    public String getIndex() {
        return index.toString();
    }

    private Contenu index;
    private String name = "index.md";

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
        File root = new File(rootDirectory);
        root.mkdirs();
        Utils.createFile(getIndex().toString(), root + "/" + name);
        return 1; //Nombre de fichier créee
    }

}


