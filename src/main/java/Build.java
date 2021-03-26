/*
Date : 05.03.2021
Groupe : PRSV
Description : Implémentation cmd Build
 */

import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.parser.ParseException;
import picocli.CommandLine;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.Callable;
import org.apache.commons.io.FileUtils;

@CommandLine.Command(
        name = "Build",
        description = "Build"
)
public class Build implements Callable<Integer> {
    @CommandLine.Parameters(paramLabel = "<rootDirectory>", description = "Dossier root")
    private String rootDirectory;

    @Override
    public Integer call() throws IOException, ParseException {
        //Lecture du fichier md pour connaitre le site et le nom de domaine

        try {

            Path path = Paths.get(rootDirectory+"build");

            //java.nio.file.Files;
            Files.createDirectories(path);

        } catch (IOException e) {

            System.err.println("Failed to create directory : " + e.getMessage());
        }

        //Copie le contenu du dossier UNIQUEMENT dans le dosser build
        String source = rootDirectory;
        File srcDir = new File(source);

        String destination = rootDirectory+"build/";
        File destDir = new File(destination);

        try {
            FileUtils.copyDirectory(srcDir, destDir, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Transforme les fichiers présent dans le dossier build
        File folder = new File(rootDirectory+"build/");
        File[] listofFiles = folder.listFiles();
        for(File file : listofFiles) //Parcourt de tous les fichiers présents dans le dossier
        {
            if(file.isFile())
            {
                String filename = file.getName();
                int index = filename.lastIndexOf('.');
                if(index > 0) {
                    String extension = filename.substring(index + 1);
                    if(extension=="md") //Si le fichier est sous format md, il est convertit en html
                    {
                        Reader in = new FileReader(file.toPath().toString());
                        filename.replace("md","html");
                        Writer out = new FileWriter(file.toPath().toString());
                        Markdown md = new Markdown();
                        md.transform(in, out);
                    }
                    else if(file.getName() == "config.yaml") //On elimine le fichier de config
                    {
                        file.delete(); //
                    }
                }
            }

        }


        return null;
    }

}



