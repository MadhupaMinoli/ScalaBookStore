package com.madhu.server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.madhu.bookStore.Controller.BookController;

import com.madhu.bookStore.Model.Book;
import com.madhu.bookStore.Service.BookService;
import com.madhu.bookStore.Utility.UrlSplitter;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import scala.collection.mutable.HashMap;
import scala.collection.mutable.StringBuilder;

public class Httpserver {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            server.createContext("/books", new MyHandler());

        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            URI uri= t.getRequestURI();
            String route=null;
            if("GET".equals(t.getRequestMethod())) {
                route =  BookController.getRoute(uri);
            }else if("POST".equals(t.getRequestMethod())) {

                InputStreamReader isr = new InputStreamReader(t.getRequestBody(), StandardCharsets.UTF_8);
                Stream<String> query = new BufferedReader(isr).lines();
                StringBuilder stringBuilder = new StringBuilder();
                query.forEach((s) -> stringBuilder.append(s).append("\n"));
                String requestBody=stringBuilder.toString();

                System.out.println(requestBody);
                System.out.println(new Gson().fromJson(requestBody, Book.class));
                BookController.postRoute(new Gson().fromJson(requestBody, Book.class));
            }


            writeResponse(t,route);
        }





        public static void writeResponse(HttpExchange httpExchange, String response) throws IOException {
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


}
