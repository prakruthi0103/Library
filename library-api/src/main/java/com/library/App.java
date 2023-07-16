package com.library;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Hello world!
 *
 */
public class App 

{
    private static final Logger logger = LogManager.getLogger("Library-API");
    public static void main(String[] args ) throws FileNotFoundException, IOException{
        
        logger.info("Application initiation..");
        try {
            logger.info("App created..");
            Server server = new Server(8080);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
