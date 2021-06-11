package ch.heigvd.prsv;/*
Date : 05.03.2021
Groupe : PRSV
Description : Implémentation cmd Build
 */


import ch.heigvd.prsv.utils.WatchApi;
import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.parser.ParseException;
import picocli.CommandLine;

import java.io.*;
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
import ch.heigvd.prsv.utils.HandlebarUtil;
import ch.heigvd.prsv.utils.JSONConfig;

@CommandLine.Command(
        name = "build",
        description = "Compile le site statique"
)
public class Build implements Callable<Integer> {
    @CommandLine.Parameters(paramLabel = "<rootDirectory>", description = "Dossier root")
    private String rootDirectory;
    @CommandLine.Option(names = "--watch", description = "Regarder en continu si des modifications sont effectuées")
    boolean isWatch;

    @Override
    public Integer call() throws IOException, ParseException {
        //Lecture du fichier md pour connaitre le site et le nom de domaine


        //Copie le contenu du dossier UNIQUEMENT, dans le dosser build
        String source = rootDirectory;
        File srcDir = new File(source);

        String destination = rootDirectory + "/build/";
        File destDir = new File(destination);

        if (destDir.exists()) {
            FileUtils.forceDelete(destDir);
        }

        try {
            FileUtils.copyDirectory(srcDir, destDir, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Copie intégrale des fichiers présent dans build, puis conversion et clean du dossier
        File folder = new File(rootDirectory + "/build/");
        File contentFile = new File(folder + "/" + Constantes.TEMPLATE_DIRECTORY);
        if (contentFile.exists()) {
            FileUtils.forceDelete(contentFile); //Suppression du dossier template
        }

        //Lecture fichier de configuration
        JSONConfig config = new JSONConfig(rootDirectory + "/" + Constantes.CONFIG_FILE_NAME);
        config.read();
        String configTitre = config.getTitre();

        // parcourir récursivement l'arboresnce et l'ajoute à result
        // On fait ensuite un for each sur le contenu de result
        BufferedReader br = null;
        Writer out = null;
        try (Stream<Path> walk = Files.walk(Paths.get(rootDirectory + "/build/"))) {
            List<File> result = walk.filter(Files::isRegularFile)
                    .map(Path::toFile).collect(Collectors.toList());
            for (File f : result) {
                String filename = f.getName();
                int index = filename.lastIndexOf('.');
                if (index > 0 && f.exists()) {
                    String extension = filename.substring(index + 1);
                    if (extension.equals("md")) //Si le fichier est sous format md, il est convertit en html
                    {
                        br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));
                        String line;
                        line = br.readLine();
                        if (line.startsWith("titre : ")) {

                            String titrePage = line.substring(8);// Récupération du titre dans le fichier de config

                            // Création de la map
                            Map<String, String> parameterMap = new HashMap<>();

                            // Pair clé valeur name pour handlebar
                            parameterMap.put("site", configTitre);
                            parameterMap.put("page", titrePage);

                            // Lecture fichier markdown
                                try (Reader in = new FileReader(f)) {
                                    String filenameContent = f.getPath();
                                    filenameContent = filenameContent.replace("md", "");

                                    // Fichier html de destination
                                    filenameContent = filenameContent.replace(filenameContent, source + "/template/content" + ".html");
                                    out = new FileWriter(filenameContent);

                                    // Transformation md ->html
                                    Markdown md = new Markdown();
                                    md.transform(in, out);

                                    //Applique handlebar
                                    HandlebarUtil handlebarLocal = new HandlebarUtil(rootDirectory);
                                    String resultHandlebar = handlebarLocal.transform(parameterMap);

                                    // Supprimer content
                                    File content = new File(filenameContent);
                                    content.delete();

                                    // Ecriture du résultat
                                    out = new FileWriter(f.getPath().replace("md", "html"));
                                    out.write(resultHandlebar);
                                    out.flush();

                                    f.delete();
                                } catch(Exception e){
                                    throw e;
                                } finally {
                                    if(out != null){
                                        out.close();
                                    }
                                }
                        } else {
                            throw new IllegalArgumentException("La 1ère ligne doit être le titre");
                        }

                    } else if (f.getName().equals(Constantes.CONFIG_FILE_NAME)) //On elimine le fichier de config
                    {
                        f.delete();
                    }
                }
            }
            if (isWatch) {
                WatchApi w = new WatchApi(rootDirectory);
                w.watch();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(br != null)
                br.close();

        }
        return 1;
    }

}



