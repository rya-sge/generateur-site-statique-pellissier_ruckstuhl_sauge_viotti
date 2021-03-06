package ch.heigvd.prsv.utils;
/*
Date : 07.05.2021
Groupe : PRSV
Description : class pour le moteur de template handlebar
*/

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.Map;

public class HandlebarUtil {
    private final String suffix;
    private final String template;
    private final String prefix;

    /**
     * @param rootDirectory dossier où Handlebar s'exécutera
     * @param suffix extension des fichiers template
     * @param template chemin du fichier template
     */
    public HandlebarUtil(String rootDirectory, String suffix, String template) {
        this.suffix = suffix;
        this.template = template;
        this.prefix = rootDirectory + "/" + "template";
    }

    /**
     * @param rootDirectory dossier où Handlebar s'exécutera
     */
    public HandlebarUtil(String rootDirectory) {
        this(rootDirectory, ".html", "layout");
    }

    /**
     * @param parameterMap mots-clé pour la correspondance
     * @return le contenu du fichier après avoir appliqué le template
     */
    public String transform(Map<String, String> parameterMap) {
        TemplateLoader templateLoader = new FileTemplateLoader(prefix, suffix);
        Handlebars handlebars = new Handlebars().with(templateLoader);
        try {
            //Compilation du template
            Template template = handlebars.compile(this.template);
            return template.apply(parameterMap);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}



