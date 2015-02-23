package com.ofs.portal.rest;

import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.ofs.portal.config.AppConfig;

public abstract class AbstractRestServiceTest extends JerseyTest {

    private static final String TEST_WEBAPP_CONTEXT_PATH = "jersey.spring.test.contextPath";
    private static final String TEST_CONTAINER_FACTORY_EXTERNAL = "org.glassfish.jersey.test.external.ExternalTestContainerFactory";

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Override
    protected DeploymentContext configureDeployment() {
        return ServletDeploymentContext.forPackages(this.getClass().getPackage().getName())
                .contextPath("/")
                .contextParam("contextClass", AnnotationConfigWebApplicationContext.class.getName())
                .contextParam("contextConfigLocation", AppConfig.class.getName())
                .addListener(ContextLoaderListener.class)
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
}
