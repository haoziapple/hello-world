package com.example.demo.swagger;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

/**
 * Swagger Spec to Java POJOs:https://github.com/swagger-api/swagger-parser
 * @author wanghao
 */
public class TestSwaggerParser {
    public static void main(String[] args) {
        Swagger swagger = new SwaggerParser().read("http://192.168.1.37:9009/v2/api-docs");
        System.out.println(swagger);
    }
}
