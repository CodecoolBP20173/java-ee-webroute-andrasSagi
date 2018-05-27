package com.codecool.webroute;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

class Routes {

    @WebRoute(path="/users")
    void onUsers (HttpExchange requestData) throws IOException {
        String response = "This is the user";
        requestData.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = requestData.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @WebRoute(path="/lists")
    void onLists (HttpExchange requestData) throws IOException {
        String response = "These are the lists";
        requestData.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = requestData.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
