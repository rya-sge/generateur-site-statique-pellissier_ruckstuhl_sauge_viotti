package utils;


import java.io.*;

public class FileHandler {

    /**
     * Lit un fichier
     * @param path le chemin vers le fichier
     * @return le contenu sous forme de String
     */
    public static String readFile(String path) {
        StringBuilder content = new StringBuilder();

        try{
            // Lit le contenu du fichier
            FileReader reader = new FileReader(path);
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
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(data);
            writer.close();
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
            File dir = new File(file.getParent());
            if(dir.mkdirs()){
                throw new Exception("Erreur de création des dossiers parents");
            }

            if(file.createNewFile()){
                throw new Exception("Erreur de création du fichier");
            }

            // S'il y a du contenu à écrire
            if(! data.isEmpty()){
                write(path, data);
            }

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

}
