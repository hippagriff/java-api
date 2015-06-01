package com.lumira;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Application initializer that bootstraps the token server application servlets.
 * 
 * @author jon
 * 
 */
public class ServletInitializer extends SpringBootServletInitializer
{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(TokenServerApplication.class);
    }

}
