package com.ofs.srv.rest;

import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.ofs.srv.config.Constants;
import com.ofs.srv.config.TestAppConfig;
import com.ofs.srv.security.SecurityFilter;

public abstract class AbstractRestServiceTest extends JerseyTest {

    private static final String TEST_WEBAPP_CONTEXT_PATH = "jersey.spring.test.contextPath";
    private static final String TEST_CONTAINER_FACTORY_EXTERNAL = "org.glassfish.jersey.test.external.ExternalTestContainerFactory";

    private String publicKey;
    
    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Override
    protected DeploymentContext configureDeployment() {

    	Properties properties = new Properties();
		try {
	        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("test-application.properties"));
        } catch (IOException e) {
	        e.printStackTrace();
        }
    	publicKey = properties.getProperty("security.public.key");

        return ServletDeploymentContext.forPackages(this.getClass().getPackage().getName())
                .contextPath("services")
                .contextParam("contextClass", AnnotationConfigWebApplicationContext.class.getName())
                .contextParam("contextConfigLocation", TestAppConfig.class.getName())
                .addListener(ContextLoaderListener.class)
                .addFilter(SecurityFilter.class, "securityFilter")
                .build();
    }

    protected abstract String getResourcePath();

    protected String getResourceFullPath() {

        String containerFactory = System.getProperty(TestProperties.CONTAINER_FACTORY);
        if (TEST_CONTAINER_FACTORY_EXTERNAL.equals(containerFactory)) {
            return System.getProperty(TEST_WEBAPP_CONTEXT_PATH) + getResourcePath();
        }
        return getResourcePath();
    }

    public Builder webTarget() {
        return webTarget("");
    }

    public Builder webTarget(String path) {
    	WebTarget t = target(getResourceFullPath() + path);
    	return t.request().header(Constants.SECURITY_HEADER.toString(), publicKey).accept(MediaType.APPLICATION_JSON_TYPE);
    }
}