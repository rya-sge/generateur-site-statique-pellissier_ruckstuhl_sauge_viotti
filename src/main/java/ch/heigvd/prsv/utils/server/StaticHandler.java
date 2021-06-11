package ch.heigvd.prsv.utils.server;/*
Date : 23.04.2021
Modifié: 30.04.2021
Groupe : PRSV
Description : Implémentation handler pour utils.server.Server http fortement inspiré de https://github.com/ianopolous/simple-http-server
 */

import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;
import java.util.zip.*;

public class StaticHandler implements HttpHandler
{
    private static Map<String, Asset> data = new HashMap<>();
    private final String pathToRoot;

    /**
     * Constructeur
     * @param pathToRoot chemin du dossier root
     * @throws IOException
     */
    public StaticHandler(String pathToRoot) throws IOException {
        this.pathToRoot = pathToRoot.endsWith("/") ? pathToRoot : pathToRoot + "/";

        File[] files = new File(pathToRoot).listFiles();
        if (files == null)
            throw new IllegalStateException("Couldn't find webroot: "+pathToRoot);
        for (File f: files)
            processFile("", f);
    }

    /**
     * Class Asset
     */
    private static class Asset {
        public final byte[] data;

        /**
         * Constructeur
         * @param data donnée à insérer
         */
        public Asset(byte[] data) {
            this.data = data;
        }
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        try {
            // Les // sont remplacés avec /
            path = path.substring(1);
            path = path.replaceAll("//", "/");
            if (path.length() == 0)
                path = "index.html";

            // Si l'URL n'est pas valide, celle de l'index est renvoyée
            boolean fromFile = new File(pathToRoot + path).exists();
            InputStream in = fromFile ? new FileInputStream(pathToRoot + path)
                    : ClassLoader.getSystemClassLoader().getResourceAsStream(pathToRoot + path);
            if (in == null) {
                // renvoie l'index si la page n'est pas reconnue
                String toAsset = pathToRoot + "index" + ".html";
                fromFile = new File(toAsset).exists();
                in = fromFile ? new FileInputStream(toAsset)
                        : ClassLoader.getSystemClassLoader().getResourceAsStream(toAsset);
            }

            // Stocker les données
            Asset res = new Asset(readResource(in));

            // Recevoir réponse
            httpExchange.getResponseHeaders().set("Content-Type", "text/html");

            // Pas obligatoir
            if (httpExchange.getRequestMethod().equals("HEAD")) {
                httpExchange.getResponseHeaders().set("Content-Length", "" + res.data.length);
                httpExchange.sendResponseHeaders(200, -1);
                return;
            }

            // Envoyer réponse
            httpExchange.sendResponseHeaders(200, res.data.length);
            httpExchange.getResponseBody().write(res.data);
            httpExchange.getResponseBody().close();

            // Gestion des erreurs
        } catch (NullPointerException t) {
            System.err.println("Error retrieving: " + path);
            t.printStackTrace();
            httpExchange.sendResponseHeaders(404, 0);
            httpExchange.getResponseBody().close();
        } catch (Throwable t) {
            System.err.println("Error retrieving: " + path);
            t.printStackTrace();
        }
    }

    /**
     * Faire le process d'un fichier
     * @param path chemin du fichier
     * @param f Le fichier
     * @throws IOException
     */
    private static void processFile(String path, File f) throws IOException {
        if (!f.isDirectory())
            data.put(path + f.getName(), new Asset(readResource(new FileInputStream(f))));
        if (f.isDirectory())
            for (File sub: f.listFiles())
                processFile(path + f.getName() + "/", sub);
    }

    /**
     * Lire les ressource du fichier
     * @param in le fichier à lire
     * @return les donnée du fichier en tableau de bytes
     * @throws IOException
     */
    private static byte[] readResource(InputStream in) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        OutputStream gout = new DataOutputStream(bout);
        byte[] tmp = new byte[4096];
        int r;
        while ((r=in.read(tmp)) >= 0)
            gout.write(tmp, 0, r);
        gout.flush();
        gout.close();
        in.close();
        return bout.toByteArray();
    }

    /**
     * Les ressources par rapport à l'élément directory
     * @param directory l'élément à lire
     * @return Liste des ressources
     */
    private static List<String> getResources(String directory)
    {
        ClassLoader context = Thread.currentThread().getContextClassLoader();

        List<String> resources = new ArrayList<>();

        ClassLoader cl = StaticHandler.class.getClassLoader();
        if (!(cl instanceof URLClassLoader))
            throw new IllegalStateException();
        URL[] urls = ((URLClassLoader) cl).getURLs();

        int slash = directory.lastIndexOf("/");
        String dir = directory.substring(0, slash + 1);
        for (int i=0; i<urls.length; i++)
        {

            if (!urls[i].toString().endsWith(".jar"))
                continue;
            try(JarInputStream jarStream = new JarInputStream(urls[i].openStream()))
            {
                while (true)
                {
                    ZipEntry entry = jarStream.getNextEntry();
                    if (entry == null)
                        break;
                    if (entry.isDirectory())
                        continue;

                    String name = entry.getName();
                    slash = name.lastIndexOf("/");
                    String thisDir = "";
                    if (slash >= 0)
                        thisDir = name.substring(0, slash + 1);

                    if (!thisDir.startsWith(dir))
                        continue;
                    resources.add(name);
                }
            }
            catch (IOException e) { e.printStackTrace();}
        }



        try(InputStream stream = context.getResourceAsStream(directory))
        {
            if (stream != null)
            {
                StringBuilder sb = new StringBuilder();
                char[] buffer = new char[1024];
                try (Reader r = new InputStreamReader(stream))
                {
                    while (true)
                    {
                        int length = r.read(buffer);
                        if (length < 0)
                        {
                            break;
                        }
                        sb.append(buffer, 0, length);
                    }
                }

                for (String s : sb.toString().split("\n"))
                {
                    if (s.length() > 0 && context.getResource(directory + s) != null)
                    {
                        resources.add(s);
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return resources;
    }
}