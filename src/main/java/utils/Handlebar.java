package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import java.io.IOException;

public class Handlebar {
    public final static String TEST_FOLDER = "./target/test/";
    private final String testFilesPath =  TEST_FOLDER;
    final private String rootDirectory = testFilesPath  + "root";
    void transform(){


        TemplateLoader loader = new ClassPathTemplateLoader();
        //loader.setPrefix(rootDirectory +"/template");
        loader.setPrefix("");
        Handlebars handlebars = new Handlebars(loader);

        try{
            Template template = handlebars.compile("mytemplate");
            //System.out.println("hello" + template.apply("Handlebar.java"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
