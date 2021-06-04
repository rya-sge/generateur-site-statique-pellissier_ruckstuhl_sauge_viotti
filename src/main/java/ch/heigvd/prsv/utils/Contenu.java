/*
Date : 14.03.2021
Groupe : PRSV
Description : Représente le contenu d'une page sur le site
 */
package utils;

import java.util.Iterator;
import java.util.LinkedList;

public class Contenu {
    private String titre = "";
    private String auteur = "";
    private String date = "";
    private LinkedList<String> contenu;

    /**
     * @param titre
     * @param auteur
     * @param date
     * @param contenu chaque objet de la linkedList est une ligne
     */
    public Contenu(String titre, String auteur, String date, LinkedList<String> contenu) {
        this.titre = "titre : " + titre + '\n';
        if (!auteur.isEmpty()) {
            this.auteur = "auteur : " + auteur + '\n';
        }
        if (!date.isEmpty()) {
            this.date = "date : " + date + '\n';
        }
        this.contenu = contenu;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(titre);
        builder.append(auteur);
        builder.append(date);
        if(contenu != null){
            Iterator<String> it = contenu.iterator();
            while (it.hasNext()) {
                builder.append((String) (it.next()) + '\n');
            }
        }
        return builder.toString();
    }
}
