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
import org.repeid.models.RepeidSessionFactory;
import org.repeid.services.DefaultRepeidSessionFactory;
import org.repeid.services.resources.admin.impl.AdminRootImpl;
import org.repeid.services.resources.impl.ServerVersionResourceImpl;
import org.repeid.services.util.JsonConfigProvider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RepeidApplication extends Application {

    protected Set<Object> singletons = new HashSet<>();
    protected Set<Class<?>> classes = new HashSet<>();

    protected RepeidSessionFactory sessionFactory;
    protected String contextPath;

    public RepeidApplication(@Context ServletContext context, @Context Dispatcher dispatcher) {
        loadConfig();

        this.contextPath = context.getContextPath();
        this.sessionFactory = createSessionFactory();

        // for injection
        dispatcher.getDefaultContextObjects().put(RepeidApplication.class, this);
        ResteasyProviderFactory.pushContext(RepeidApplication.class, this);
        context.setAttribute(RepeidSessionFactory.class.getName(), this.sessionFactory);

        singletons.add(new ServerVersionResourceImpl());
        singletons.add(new AdminRootImpl());
    }

    public static RepeidSessionFactory createSessionFactory() {
        DefaultRepeidSessionFactory factory = new DefaultRepeidSessionFactory();
        factory.init();
        return factory;
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

}
