package utils;

import com.github.jknack.handlebars.Handlebars;

public class Handlebar {

    void transform(){
        Handlebars handlebars = new Handlebars();

        Template template = handlebars.compileInline("Hello {{this}}!");

        System.out.println(template.apply("Handlebars.java"));
    }
}
