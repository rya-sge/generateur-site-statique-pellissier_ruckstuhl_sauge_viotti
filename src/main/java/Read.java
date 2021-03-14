// inspiré de
// https://examples.javacodegeeks.com/core-java/xml/jdom/read-xml-file-in-java-using-jdom-parser-example/

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.*;
import org.jdom2.input.*;

public class Read {

    private static final String xmlFilePath = "src/menu.xml";

    public static void main(String[] args) {

        SAXBuilder saxBuilder = new SAXBuilder();
        File xmlFile = new File(xmlFilePath);

        try {
            Document document = (Document) saxBuilder.build(xmlFile);
            Element rootElement = document.getRootElement();

            List listMenu = rootElement.getChildren("menu");
            for (int iMenu = 0; iMenu < listMenu.size(); ++iMenu) {

                Element menu = (Element) listMenu.get(iMenu);
                System.out.println("nom    : "  + menu.getChildText("nom"));
                System.out.println("pos    : "  + menu.getAttributeValue("position"));

                List listOption = menu.getChildren("option");
                for (int iOption = 0; iOption < listOption.size(); ++iOption) {
                    Element option = (Element) listOption.get(iOption);
                    System.out.println("option : "  + option.getText());
                }
            }
        }

        catch (IOException io) {
            System.out.println(io.getMessage());
        }

        catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }
    }
}