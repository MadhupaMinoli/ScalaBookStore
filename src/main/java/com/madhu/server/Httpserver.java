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
import com.madhu.bookStore.Model.Response;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import scala.Tuple2;
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
            Response response=null;
            if("GET".equals(t.getRequestMethod())) {
              response = BookController.getRoute(uri);
            }else if("POST".equals(t.getRequestMethod())) {

                InputStreamReader isr = new InputStreamReader(t.getRequestBody(), StandardCharsets.UTF_8);
                Stream<String> query = new BufferedReader(isr).lines();
                StringBuilder stringBuilder = new StringBuilder();
                query.forEach((s) -> stringBuilder.append(s).append("\n"));
                String requestBody=stringBuilder.toString();
                if (requestBody.isEmpty()){
                    response= new Response("Request body cannot be empty.",400);}
                else{
                    Book postingBook = new Gson().fromJson(requestBody, Book.class);
                    response = BookController.postRoute(postingBook, uri);
                }
            }

            writeResponse(t,response);
        }





        public static void writeResponse(HttpExchange httpExchange, Response response )throws IOException {

            httpExchange.getResponseHeaders().add("Content-type", " application/json; charset=utf-8");
            httpExchange.sendResponseHeaders(response.statusCode(), response.message().length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.message().getBytes());
            os.close();

        }
    }


}
