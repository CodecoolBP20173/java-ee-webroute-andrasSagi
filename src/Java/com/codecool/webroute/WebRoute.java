package com.codecool.webroute;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface WebRoute {

    enum Method {
        GET, POST, DELETE, PUT
    }

    Method method() default Method.GET;
    String path();
}
