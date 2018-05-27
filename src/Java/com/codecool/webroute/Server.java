package com.codecool.webroute;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        Class cls = Class.forName("com.codecool.webroute.Routes");
        for (Method method: cls.getDeclaredMethods()) {
            if (method.isAnnotationPresent(WebRoute.class)) {
                Annotation annotation = method.getAnnotation(WebRoute.class);
                WebRoute webRoute = (WebRoute) annotation;
                server.createContext(webRoute.path(), new RouteHandler(webRoute.path()));
            }

        }
        server.setExecutor(null);
        server.start();
    }

    static class RouteHandler implements HttpHandler {

        private String path;

        RouteHandler(String path) {
            this.path = path;
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                Class cls = Class.forName("com.codecool.webroute.Routes");
                Object obj = cls.newInstance();
                for (Method method : cls.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(WebRoute.class)) {
                        Annotation annotation = method.getAnnotation(WebRoute.class);
                        WebRoute webRoute = (WebRoute) annotation;
                        if (webRoute.path().equals(path)) {
                            method.invoke(obj, t);
                        }
                    }
                }
            } catch(Exception e) {
                System.out.println(e);
                        }
                    }
                }

}
