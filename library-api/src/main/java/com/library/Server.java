package com.library;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.net.httpserver.*;

public class Server {
    private int port;
        
    private static final Logger logger = LogManager.getLogger("Library-API");
    
    Server(int port) throws IOException{
        this.port =port;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/health", new HealthHandler());
        server.createContext("/books", new BookHandler());
        server.start();
           logger.info("Server starting...");
        System.out.println("Application is running on port "+ this.port);
        
    }
}
