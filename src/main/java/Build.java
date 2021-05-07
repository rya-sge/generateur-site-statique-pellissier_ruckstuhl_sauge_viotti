/*
Date : 05.03.2021
Groupe : PRSV
Description : Implémentation cmd Build
 */

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.parser.ParseException;
import picocli.CommandLine;

import java.io.*;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import utils.HandlebarUtil;
import utils.JSONConfig;

@CommandLine.Command(
        name = "build",
        description = "Compile le site statique"
)
public class Build implements Callable<Integer> {
    @CommandLine.Parameters(paramLabel = "<rootDirectory>", description = "Dossier root")
    private String rootDirectory;

    @Override
    public Integer call() throws IOException, ParseException {
        //Lecture du fichier md pour connaitre le site et le nom de domaine


        //Copie le contenu du dossier UNIQUEMENT, dans le dosser build
        String source = rootDirectory;
        File srcDir = new File(source);

        String destination = rootDirectory + "/build/";
        File destDir = new File(destination);

        if(destDir.exists())
        {
            FileUtils.forceDelete(destDir);
        }

        try {
            FileUtils.copyDirectory(srcDir, destDir, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Copie intégrale des fichiers présent dans build, puis conversion et clean du dossier
        File folder = new File(rootDirectory + "/build/");
        File contentFile = new File (folder + "/" + Constantes.TEMPLATE_DIRECTORY);
        if(contentFile.exists())
        {
            FileUtils.forceDelete(contentFile); //Suppression du dossier template
        }

        //Lecture fichier de configuration
        JSONConfig config = new JSONConfig(rootDirectory + "/" + Constantes.CONFIG_FILE_NAME);
        config.read();
        String configTitre = config.getTitre();

        try(Stream<Path> walk = Files.walk(Paths.get(rootDirectory + "/build/"))){
            List<File> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toFile()).collect(Collectors.toList());
            for(File f : result)
            {
                String filename = f.getName();
                int index = filename.lastIndexOf('.');
                if(index > 0) {
                    String extension = filename.substring(index + 1);
                    if(extension.equals("md")) //Si le fichier est sous format md, il est convertit en html
                    {
                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));
                        String line;
                        line = br.readLine();
                        if(line.startsWith("titre : ")){
                            br.close();

                           String titrePage = line.substring(8);// Récupération du titre dans le fichier de config

                            // Création de la map
                            Map<String, String> parameterMap = new HashMap<>();

                            // Pair clé valeur name => Baeldung
                            parameterMap.put("site", configTitre);
                            parameterMap.put("page", titrePage);

                            // Lecture fichier mardown
                            Reader in = new FileReader(f);
                            String filenameContent = f.getPath();
                            filenameContent = filenameContent.replace("md","");

                            // Fichier html de destination
                            filenameContent = filenameContent.replace(filenameContent,source + "/template/content" + ".html");
                            Writer out = new FileWriter(filenameContent);

                            // Transformation md ->html
                            Markdown md = new Markdown();
                            md.transform(in, out);
                            out.close();
                            in.close();

                            //Appliqué handlebar
                            HandlebarUtil handlebarLocal = new  HandlebarUtil(rootDirectory);
                            String resultHandlebar = handlebarLocal.transform(parameterMap);

                            // Supprimer content
                            File content = new File(filenameContent);
                            content.delete();

                            // Ecriture du résultat
                            out = new FileWriter(f.getPath().replace("md", "html"));
                            out.write(resultHandlebar);
                            out.flush();
                            out.close();

                            f.delete();

                        }else{
                            throw new IllegalArgumentException("La 1ère ligne doit être le titre");
                        }
                    }
                    else if(f.getName().equals(Constantes.CONFIG_FILE_NAME)) //On elimine le fichier de config
                    {
                        f.delete();
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        /*
        File[] listofFiles = folder.listFiles();
        for(File file : listofFiles) //Parcourt de tous les fichiers présents dans le dossier
        {
            if(file.isFile())
            {
                String filename = file.getName();
                int index = filename.lastIndexOf('.');
                if(index > 0) {
                    String extension = filename.substring(index + 1);
                    if(extension.equals("md")) //Si le fichier est sous format md, il est convertit en html
                    {
                        Reader in = new FileReader(file);

                        filename = file.getPath();
                        filename = filename.replace("md","html");

                        Writer out = new FileWriter(filename);
                        Markdown md = new Markdown();
                        md.transform(in, out);

                        out.close();
                        file.delete();
                    }
                    else if(file.getName().equals(Constantes.CONFIG_FILE_NAME)) //On elimine le fichier de config
                    {
                        file.delete(); //
                    }
                }
            }

        }*/
        return 1;
    }

}



