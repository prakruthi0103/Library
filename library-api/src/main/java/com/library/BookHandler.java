package com.library;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class BookHandler implements HttpHandler {
    Gson gson;

    private String getBooks(BookDAO db) {
        HashMap<String, String> json = new HashMap<String, String>();
        for (Book book : db.searchBooks("Secret")) {
            json.put("name", book.name);
            json.put("id", String.valueOf(book.id));
            json.put("publisherName", book.publisherName);
            json.put("authorName", book.authorName);
        }
        return this.gson.toJson(json);
    }

    private String createBook(BookDAO db, Book book) {
        db.addBook(book);
        return "Created";
    }

    private String updateBook(BookDAO db, Book book) {
        db.updateBook(book);
        return "Updated";
    }

    private String deleteBook(BookDAO db, int bookId) {
        db.deleteBook(bookId);
        return "Deleted";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        gson = new Gson();
        BookDAO db = new BookDAO();
        String response;

        System.out.println("triggered book handler...");
        if (exchange.getRequestMethod().equals("GET")) {
            response = getBooks(db);
        } else if (exchange.getRequestMethod().equals("POST")) {
            String input = new String(exchange.getRequestBody().readAllBytes());
            Book payloadBook = gson.fromJson(input, Book.class);
            Book book = new Book(payloadBook.name, payloadBook.publisherName, payloadBook.authorName);
            response = createBook(db, book);
        } else if (exchange.getRequestMethod().equals("PUT")) {
            String input = new String(exchange.getRequestBody().readAllBytes());
            Book payloadBook = gson.fromJson(input, Book.class);
            Book book = new Book(payloadBook.name, payloadBook.publisherName, payloadBook.authorName);
            response = updateBook(db, book);
        }else if (exchange.getRequestMethod().equals("DELETE")) {
            String input = new String(exchange.getRequestBody().readAllBytes());
            Book payloadBook = gson.fromJson(input, Book.class);
            int bookId = payloadBook.id;
            response = deleteBook(db, bookId);
        }else {
            response = "";
        }

        exchange.sendResponseHeaders(200, response.getBytes().length);
        exchange.getResponseHeaders().set("Content-type", "application/json");

        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(response.getBytes());
        responseBody.close();
    }

}
