package org.repeid.testsuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.DispatcherType;

import org.jboss.logging.Logger;
import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;
import org.repeid.services.filters.RepeidSessionServletFilter;
import org.repeid.services.managers.ApplianceBootstrap;
import org.repeid.services.resources.RepeidApplication;
import org.repeid.util.JsonSerialization;

import io.undertow.Undertow;
import io.undertow.Undertow.Builder;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DefaultServletConfig;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.FilterInfo;
import io.undertow.servlet.api.ServletInfo;

public class RepeidServer {

    private static final Logger log = Logger.getLogger(RepeidServer.class);

    private boolean sysout = false;

    public static class RepeidServerConfig {
        private String host = "localhost";
        private int port = 8081;
        private int workerThreads = Math.max(Runtime.getRuntime().availableProcessors(), 2) * 8;
        private String resourcesHome;

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

        public String getResourcesHome() {
            return resourcesHome;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public void setResourcesHome(String resourcesHome) {
            this.resourcesHome = resourcesHome;
        }

        public int getWorkerThreads() {
            return workerThreads;
        }

        public void setWorkerThreads(int workerThreads) {
            this.workerThreads = workerThreads;
        }
    }

    public static <T> T loadJson(InputStream is, Class<T> type) {
        try {
            return JsonSerialization.readValue(is, type);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse json", e);
        }
    }

    public static void main(String[] args) throws Throwable {
        bootstrapRepeidServer(args);
    }

    public static RepeidServer bootstrapRepeidServer(String[] args) throws Throwable {
        File f = new File(System.getProperty("user.home"), ".repeid-server.properties");
        if (f.isFile()) {
            Properties p = new Properties();
            p.load(new FileInputStream(f));
            System.getProperties().putAll(p);
        }

        RepeidServerConfig config = new RepeidServerConfig();

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-b")) {
                config.setHost(args[++i]);
            }

            if (args[i].equals("-p")) {
                config.setPort(Integer.valueOf(args[++i]));
            }
        }

        if (System.getProperty("repeid.port") != null) {
            config.setPort(Integer.valueOf(System.getProperty("repeid.port")));
        }

        if (System.getProperty("repeid.bind.address") != null) {
            config.setHost(System.getProperty("repeid.bind.address"));
        }

        if (System.getenv("REPEID_DEV_PORT") != null) {
            config.setPort(Integer.valueOf(System.getenv("REPEID_DEV_PORT")));
        }

        if (System.getProperties().containsKey("resources")) {
            String resources = System.getProperty("resources");
            if (resources == null || resources.equals("") || resources.equals("true")) {
                if (System.getProperties().containsKey("maven.home")) {
                    resources = System.getProperty("user.dir").replaceFirst("testsuite.integration.*", "");
                } else {
                    for (String c : System.getProperty("java.class.path").split(File.pathSeparator)) {
                        if (c.contains(File.separator + "testsuite" + File.separator + "integration")) {
                            resources = c.replaceFirst("testsuite.integration.*", "");
                        }
                    }
                }
            }

            File dir = new File(resources).getAbsoluteFile();
            if (!dir.isDirectory()) {
                throw new RuntimeException("Invalid base resources directory");

            }
            if (!new File(dir, "themes").isDirectory()) {
                throw new RuntimeException("Invalid resources forms directory");
            }

            if (!System.getProperties().containsKey("repeid.theme.dir")) {
                System.setProperty("repeid.theme.dir", file(dir.getAbsolutePath(), "themes", "src", "main", "resources", "theme").getAbsolutePath());
            } else {
                String foo = System.getProperty("repeid.theme.dir");
                System.out.println(foo);
            }

            if (!System.getProperties().containsKey("repeid.theme.cacheTemplates")) {
                System.setProperty("repeid.theme.cacheTemplates", "false");
            }

            if (!System.getProperties().containsKey("repeid.theme.cacheThemes")) {
                System.setProperty("repeid.theme.cacheThemes", "false");
            }

            if (!System.getProperties().containsKey("repeid.theme.staticMaxAge")) {
                System.setProperty("repeid.theme.staticMaxAge", "-1");
            }

            config.setResourcesHome(dir.getAbsolutePath());
        }

        if (System.getProperties().containsKey("undertowWorkerThreads")) {
            int undertowWorkerThreads = Integer.parseInt(System.getProperty("undertowWorkerThreads"));
            config.setWorkerThreads(undertowWorkerThreads);
        }

        final RepeidServer repeid = new RepeidServer(config);
        repeid.sysout = true;
        repeid.start();

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-import")) {
                repeid.importRealm(new FileInputStream(args[++i]));
            }
        }

        if (System.getProperties().containsKey("import")) {
            repeid.importRealm(new FileInputStream(System.getProperty("import")));
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                repeid.stop();
            }
        });

        if (System.getProperties().containsKey("startTestsuiteCLI")) {
            //new TestsuiteCLI(repeid).start();
        }

        return repeid;
    }

    private RepeidServerConfig config;

    private RepeidSessionFactory sessionFactory;

    private UndertowJaxrsServer server;

    public RepeidServer() {
        this(new RepeidServerConfig());
    }

    public RepeidServer(RepeidServerConfig config) {
        this.config = config;
    }

    public RepeidSessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public UndertowJaxrsServer getServer() {    	
        return server;
    }

    public RepeidServerConfig getConfig() {
        return config;
    }

    public void importRealm(InputStream realm) {
        //RealmRepresentation rep = loadJson(realm, RealmRepresentation.class);
        //importRealm(rep);
    }

    /*public void importRealm(RealmRepresentation rep) {
        RepeidSession session = sessionFactory.create();;
        session.getTransaction().begin();

        try {
            RealmManager manager = new RealmManager(session);

            if (rep.getId() != null && manager.getRealm(rep.getId()) != null) {
                info("Not importing realm " + rep.getRealm() + " realm already exists");
                return;
            }

            if (manager.getRealmByName(rep.getRealm()) != null) {
                info("Not importing realm " + rep.getRealm() + " realm already exists");
                return;
            }
            manager.setContextPath("/auth");
            RealmModel realm = manager.importRealm(rep);

            info("Imported realm " + realm.getName());

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }*/

    protected void setupDevConfig() {
        if (System.getProperty("repeid.createAdminUser", "true").equals("true")) {
            RepeidSession session = sessionFactory.create();
            try {
                session.getTransaction().begin();
                if (new ApplianceBootstrap(session).isNoMasterUser()) {
                    new ApplianceBootstrap(session).createMasterRealmUser("admin", "admin");
                }
                session.getTransaction().commit();
            } finally {
                session.close();
            }
        }
    }

    public void start() throws Throwable {
        long start = System.currentTimeMillis();

        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplicationClass(RepeidApplication.class.getName());

        Builder builder = Undertow.builder()
                .addHttpListener(config.getPort(), config.getHost())
                .setWorkerThreads(config.getWorkerThreads())
                .setIoThreads(config.getWorkerThreads() / 8);

        server = new UndertowJaxrsServer();
        try {
            server.start(builder);

            DeploymentInfo di = server.undertowDeployment(deployment, "");
            di.setClassLoader(getClass().getClassLoader());
            di.setContextPath("/repeid");
            di.setDeploymentName("Repeid");
            di.setDefaultEncoding("UTF-8");

            di.setDefaultServletConfig(new DefaultServletConfig(true));

            ServletInfo restEasyDispatcher = Servlets.servlet("Repeid REST Interface", HttpServlet30Dispatcher.class);

            restEasyDispatcher.addInitParam("resteasy.servlet.mapping.prefix", "/");
            restEasyDispatcher.setAsyncSupported(true);

            di.addServlet(restEasyDispatcher);

            FilterInfo filter = Servlets.filter("SessionFilter", RepeidSessionServletFilter.class);

            filter.setAsyncSupported(true);

            di.addFilter(filter);
            di.addFilterUrlMapping("SessionFilter", "/*", DispatcherType.REQUEST);

            server.deploy(di);

            sessionFactory = ((RepeidApplication) deployment.getApplication()).getSessionFactory();

            setupDevConfig();

            if (config.getResourcesHome() != null) {
                info("Loading resources from " + config.getResourcesHome());
            }

            info("Started Repeid (http://" + config.getHost() + ":" + config.getPort() + "/repeid) in "
                    + (System.currentTimeMillis() - start) + " ms\n");
        } catch (RuntimeException e) {
            server.stop();
            throw e;
        }
    }

    private void info(String message) {
        if (sysout) {
            System.out.println(message);
        } else {
            log.info(message);
        }
    }

    public void stop() {
        sessionFactory.close();
        server.stop();

        info("Stopped Repeid");
    }

    private static File file(String... path) {
        StringBuilder s = new StringBuilder();
        for (String p : path) {
            s.append(File.separator);
            s.append(p);
        }
        return new File(s.toString());
    }

}
