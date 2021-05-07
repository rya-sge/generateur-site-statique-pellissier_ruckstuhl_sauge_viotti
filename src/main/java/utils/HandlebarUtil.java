package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;
import java.util.Map;

public class HandlebarUtil {
    private final String rootDirectory;
    private final String suffix;
    private final String template;

    public HandlebarUtil(String rootDirectory, String suffix, String template) {
        this.rootDirectory = rootDirectory;
        this.suffix = suffix;
        this.template = template;
    }
    public HandlebarUtil(String rootDirectory){
       this( rootDirectory,".html", "layout");
    }

    public String transform(Map<String, String> parameterMap){
        //Compilation
        String prefix = rootDirectory + "/" + "template";
        TemplateLoader templateLoader = new FileTemplateLoader(prefix, suffix);
        Handlebars handlebars = new Handlebars().with(templateLoader);
        try {
            Template template = handlebars.compile(this.template);
            return template.apply(parameterMap);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}



