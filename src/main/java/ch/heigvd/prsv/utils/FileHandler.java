/*
Date : 26.03.2021
Groupe : PRSV
Description : class de traitements de fichiers
 */

package ch.heigvd.prsv.utils;
import java.io.*;

public class FileHandler {

    /**
     * Lit un fichier
     * @param path le chemin vers le fichier
     * @return le contenu sous forme de String
     */
    public static String readFile(String path) {
        StringBuilder content = new StringBuilder();


        try(FileReader reader = new FileReader(path)){

            // Lit le contenu du fichier
            while(true){
                int charCode = reader.read();

                if(charCode == -1){
                    break;
                }

                content.append((char)charCode);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return content.toString();
        }

        return content.toString();
    }


    /**
     * Ecrit des données dans un fichier.
     * @param path le chemin vers le fichier
     * @param data les données à écrire
     * @return true si l'écriture s'est bien passée, sinon false
     */
    public static boolean write(String path, String data){
        try(FileWriter writer = new FileWriter(path)){
            writer.write(data);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Crée un fichier vide
     * @param path chemin vers le fichier
     * @return true si la création s'est bien passée
     */
    public static boolean create(String path) {

        return create(path, "");
    }

    /**
     * Créer un fichier avec le contenu passé en paramètre
     * @param path chemin du fichier
     * @param data données à écrire dans le fichier
     * @return true si la création s'est bien passée
     */
    public static boolean create(String path, String data) {
        try {

            File file = new File(path);

            // Crée les répertoires parents s'ils n'existent pas encore
            if(!createParentsDirectories(file)){
                throw new Exception("Erreur de création des dossiers parents");
            }

            write(path, data);



        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Contrôle si le fichier passé en paramètre existe
     * @param path le chemin à vérifier
     * @return true ou false
     */
    public static boolean exists(String path){
        try {
            File f = new File(path);
            return f.exists();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Efface un dossier et son contenu.
     * @param directory le dossier à effacer.
     */
    public static boolean eraseNotEmptyDirectory(File directory){
        File[] lFiles = directory.listFiles();
        if (lFiles != null){
            for (File file: lFiles){
                if (file.isDirectory()){
                    eraseNotEmptyDirectory(file);
                }else {
                    file.delete();
                }
            }
        }
        return directory.delete();
    }

    /**
     * Créer l'arborescence de dossiers qu'un fichier a besoin pour être stocké
     * @param file le fichier dont il faut extraire le chemin pour créer ses parents
     * @return true si les dossiers parents ont été créés ou existent déjà
     */
    public static boolean createParentsDirectories(File file){
        File dir = new File(file.getParent());
        if(!exists(dir.toString())){
            return dir.mkdirs();
        }
        return true;
    }
}
