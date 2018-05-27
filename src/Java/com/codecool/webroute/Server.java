package com.codecool.webroute;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {

    private static String ROUTER_FILE = "com.codecool.webroute.Routes";
    private static Class routes;

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        routes = Class.forName(ROUTER_FILE);
        for (Method method: routes.getDeclaredMethods()) {
            if (method.isAnnotationPresent(WebRoute.class)) {
                Annotation annotation = method.getAnnotation(WebRoute.class);
                WebRoute webRoute = (WebRoute) annotation;
                server.createContext(webRoute.path(), new RouteHandler(method));
            }

        }
        server.setExecutor(null);
        server.start();
    }

    static class RouteHandler implements HttpHandler {

        private Method method;

        RouteHandler(Method method) {
            this.method = method;
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                Object route = routes.newInstance();
                method.invoke(route, t);
            } catch(Exception e) {
                System.out.println(e);
                        }
                    }
                }

}
