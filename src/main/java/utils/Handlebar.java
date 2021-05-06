package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.internal.lang3.Validate;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.github.jknack.handlebars.io.URLTemplateSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class Handlebar {
    void transform(){

        String suffix = ".hbs";
        String prefix = "/template";
        TemplateLoader templateLoader = new ClassPathTemplateLoader(prefix, suffix);
        Handlebars handlebars = new Handlebars(templateLoader);
        try{
            Template template = handlebars.compile("mytemplate");
            System.out.println("hello" + template.apply("Handlebar.java"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/*
 // public final static String TEST_FOLDER = "./target/test/";
    // private final String testFilesPath =  TEST_FOLDER;
    // final private String rootDirectory = testFilesPath  + "root";
    void transform(){
        //TemplateLoader loader = new ClassPathTemplateLoader();
        //loader.setPrefix(rootDirectory +"/template");
        //loader.setPrefix(".");
 */
