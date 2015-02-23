package com.ofs.portal.config;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.ofs.portal.filter.RequestFilter;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("com.ofs.portal")
@Import({ LocalDSConfig.class })
public class AppConfig {

    @Value("${ofs.srv.url}")
    private String ofsSrvUrl;

    @Value("${ofs.srv.security.key}")
    private String ofsSrvSecurityKey;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public WebTarget ofsSrvEndpoint() {

    	Client client = JerseyClientBuilder.createClient();
        return client.register(new RequestFilter(ofsSrvSecurityKey)).target(ofsSrvUrl);
    }
}
