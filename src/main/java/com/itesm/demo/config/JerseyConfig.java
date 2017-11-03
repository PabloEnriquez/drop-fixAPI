package com.itesm.demo.config;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @author mklfarha Jersey configuration to load endpoints.
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(JacksonJaxbJsonProvider.class);
        packages("com.itesm.demo.endpoint");
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }

}
