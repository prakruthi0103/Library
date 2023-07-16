package com.library;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HealthHandler implements HttpHandler{

    private static final Logger logger = LogManager.getLogger("Library-API");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("checking app status in Health Handler...");
        Gson gson = new Gson();
        HashMap<String, String> json = new HashMap<String, String>();
        json.put("status", "running");
        String response = gson.toJson(json);

        exchange.sendResponseHeaders(200, response.getBytes().length);
        exchange.getResponseHeaders().set("Content-type", "application/json");
        
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(response.getBytes());

        logger.info("Health Handler status updated!");
        responseBody.close();
    }
    
}
