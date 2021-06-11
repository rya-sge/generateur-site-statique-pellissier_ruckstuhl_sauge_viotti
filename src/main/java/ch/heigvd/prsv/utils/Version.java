/*
Date : 27.03.2021
Groupe : PRSV
Description : Class permettant d'obtenir la version du projet.
 */

package ch.heigvd.prsv.utils;

import picocli.CommandLine.IVersionProvider;

public class Version implements IVersionProvider {

    /**
     * Récupère la version de l'application dans les métadonnées
     * @return le nom de l'application et sa version
     */
    public String[] getVersion(){
        String currentVersion = this.getClass().getPackage().getImplementationVersion();
        String appName = this.getClass().getPackage().getImplementationTitle();
        String strVersion = String.format("%s %s", appName, currentVersion);
        return new String[]{strVersion};
    }
}
