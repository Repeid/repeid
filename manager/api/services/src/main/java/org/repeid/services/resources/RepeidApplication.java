package org.repeid.services.resources;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.keycloak.Config;
import org.keycloak.common.util.SystemEnvProperties;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.DefaultKeycloakSessionFactory;
import org.keycloak.services.util.JsonConfigProvider;
import org.repeid.manager.api.rest.impl.AdminRootImpl;
import org.repeid.manager.api.rest.info.impl.ServerVersionResourceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RepeidApplication extends Application {

    protected Set<Object> singletons = new HashSet<>();
    protected Set<Class<?>> classes = new HashSet<>();

    protected KeycloakSessionFactory sessionFactory;
    protected String contextPath;

    /**
     * @javax.servlet.ServletContext. The attributes stored in the
     *                                ServletContext are available to all
     *                                servlets in your application, and between
     *                                requests and sessions. The ServletContext
     *                                attributes are still stored in the memory
     *                                of the servlet container. That means that
     *                                the same problems exists as does with the
     *                                session attributes, in server clusters.
     * @example context.setAttribute("someValue", "aValue");
     * @example context.getAttribute("someValue");
     **/

    /**
     * @org.jboss.resteasy.core.Dispatcher. Admin context objects that can be
     *                                      injectec
     *                                      using @javax.ws.rs.core.Context
     * @example dispatcher.getDefaultContextObjects().put(class, object); Now
     *          you can inject class using @javax.ws.rs.core.Context
     * 
     **/

    /**
     * ResteasyProviderFactory. Many times we get into situation where we have
     * to pass data from-to multiple layers in our application. An example can
     * be application using interceptors. Suppose we have two interceptors in
     * our application, one for login check and second for putting audit
     * information in database. We want to use the User object from first
     * interceptor, into second interceptor.
     */
    public RepeidApplication(@Context ServletContext context, @Context Dispatcher dispatcher) {
        loadConfig();

        this.contextPath = context.getContextPath();
        this.sessionFactory = createSessionFactory();

        // for injection

        dispatcher.getDefaultContextObjects().put(RepeidApplication.class, this);
        ResteasyProviderFactory.pushContext(RepeidApplication.class, this);
        context.setAttribute(KeycloakSessionFactory.class.getName(), this.sessionFactory);

        singletons.add(new ServerVersionResourceImpl());
        singletons.add(new AdminRootImpl());
    }

    public static void loadConfig() {
        try {
            JsonNode node = null;

            String configDir = System.getProperty("jboss.server.config.dir");
            if (configDir != null) {
                FileSystem fs = FileSystems.getDefault();
                Path path = Paths.get(configDir + fs.getSeparator() + "repeid-server.json");
                if (Files.exists(path) && Files.isRegularFile(path)) {
                    node = new ObjectMapper().readTree(Files.newInputStream(path));
                }
            }

            if (node == null) {
                String s = "META-INF/repeid-server.json";
                URL resource = Thread.currentThread().getContextClassLoader().getResource(s);
                if (resource != null) {
                    node = new ObjectMapper().readTree(resource);
                }
            }

            if (node != null) {
                Properties properties = new SystemEnvProperties();
                Config.init(new JsonConfigProvider(node, properties));
                return;
            } else {
                throw new RuntimeException("Config 'repeid-server.json' not found");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    public static KeycloakSessionFactory createSessionFactory() {
        DefaultKeycloakSessionFactory factory = new DefaultKeycloakSessionFactory();
        factory.init();
        return factory;
    }

}
