/*
Date : 14.03.2021
Groupe : PRSV
Description :
Class permettant de donner le chemin d'un fichier .md et le
transforme en html dans le répertoire de destination.
 */


/**
 *
 */

package ch.heigvd.prsv.utils;

import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.parser.ParseException;

import java.io.*;

final public class MdToHtml {

    /**
     * Transforme le .md source en .html dans une destination choisie.
     * Peut être utilisé statiquement.
     * @param src chemin du fichier se terminant par le fichier en .md
     * @param dst chemin du fichier se terminant par le nouveau fichier en .html
     */
    public static void mdTransform(String src, String dst){
        Reader in = null;
        try {
            in = new FileReader(src);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Writer out = null;
        try {
            if(!FileHandler.exists(dst))
                FileHandler.create(dst, "");

            out = new FileWriter(dst);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Markdown md = new Markdown();
        try {
            md.transform(in, out);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
