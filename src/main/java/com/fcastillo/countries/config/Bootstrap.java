/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fcastillo.countries.config;

import io.swagger.models.Contact;
import io.swagger.models.Info;
import io.swagger.models.License;
import io.swagger.models.Swagger;
import io.swagger.models.auth.ApiKeyAuthDefinition;
import io.swagger.models.auth.In;
import io.swagger.models.auth.OAuth2Definition;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.models.auth.BasicAuthDefinition;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 *
 * @author fcastillo
 */
@WebServlet(name = "Bootstrap", loadOnStartup = 2)
public class Bootstrap extends HttpServlet {

    @Override
    public void init(ServletConfig config) {
        Info info = new Info()
                .title("API de Paises")
                .description("Interfaz para la consulta de paises.");

        ServletContext context = config.getServletContext();

        Swagger swagger = new Swagger().info(info)
                .basePath("/countries/api");
        swagger.securityDefinition("basicAuth", new BasicAuthDefinition());
        //swagger.securityDefinition("api_key", new ApiKeyAuthDefinition("api_key", In.HEADER));
        new SwaggerContextService().withServletConfig(config).updateSwagger(swagger);
    }
}
