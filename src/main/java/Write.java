// sources : https://openclassrooms.com/fr/courses/1766341-structurez-vos-donnees-avec-xml/1768662-dom-exemple-dutilisation-en-java

// 0. imports nécessaires
//import java.io.*;
//import org.w3c.dom.*;
//import javax.xml.parsers.*;
//import javax.xml.transform.*;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Write{
    public static void main(final String[] args) {

        // Etape 1 : récupération d'une instance de la classe "DocumentBuilderFactory"
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            // Etape 2 : création d'un parseur
            final DocumentBuilder builder = factory.newDocumentBuilder();

            // Etape 3 : création d'un Document
            final Document document = builder.newDocument();

            // Etape 4 : création de l'Element racine
            final Element allMenus = document.createElement("menus");
            document.appendChild(allMenus);

            // Etape 5 : création d'un menu
            final Comment commentaire = document.createComment("Fichier");
            allMenus.appendChild(commentaire);

            final Element menu = document.createElement("menu");
            menu.setAttribute("position", "1");
            allMenus.appendChild(menu);

            final Element nom = document.createElement("nom");
            nom.appendChild(document.createTextNode("Fichier"));
            menu.appendChild(nom);

            // Etape 6 : création des options
            final Element nouveau = document.createElement("option");
            nouveau.appendChild(document.createTextNode("Nouveau"));
            menu.appendChild(nouveau);

            final Element sauver = document.createElement("option");
            sauver.appendChild(document.createTextNode("Sauver"));
            menu.appendChild(sauver);

            final Element quitter = document.createElement("option");
            quitter.appendChild(document.createTextNode("Quitter"));
            menu.appendChild(quitter);


            // Etape 7 : finalisation
            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
            final DOMSource source        = new DOMSource(document);
            final StreamResult sortie     = new StreamResult(new File("src/file.xml"));

            //prologue
            document.setXmlStandalone(true);
            document.setXmlVersion("1.1");
            transformer.setOutputProperty(OutputKeys.ENCODING,   "UTF-8");

            //formatage
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            //sortie
            transformer.transform(source, sortie);
        }
        catch (final ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
