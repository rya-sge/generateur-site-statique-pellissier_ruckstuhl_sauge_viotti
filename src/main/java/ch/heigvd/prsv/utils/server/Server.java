package ch.heigvd.prsv.utils.server;
/*
Date : 23.04.2021
Modifié: 30.04.2021
Groupe : PRSV
Description : Implémentation utils.server.Server http fortement inspiré de https://github.com/ianopolous/simple-http-server
 */

import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;

public class Server {

    /**
     * Démarre le serveur http sur le localhost
     * @param pathToRoot le chemin du dossier root du site web
     * @param port le numéro de port sur lequel le serveur écoute
     * @throws IOException s'il y a un problème avec le démarrage du serveur ou le chemin passé en paramètre
     */
    public static void run(String pathToRoot, int port) throws IOException {
        HttpServer httpServer = HttpServer.create();
        httpServer.createContext("/", new StaticHandler(pathToRoot));
        httpServer.bind(new InetSocketAddress("localhost", port), 100);
        httpServer.start();
        System.out.println("Le serveur a été démarré sur le port " + port);
    }
}