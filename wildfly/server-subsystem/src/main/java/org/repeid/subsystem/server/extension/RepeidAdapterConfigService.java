package org.repeid.subsystem.server.extension;

/**
 * This service keeps track of the entire Repeid management model so as to provide
 * adapter configuration to each deployment at deploy time.
 */
public final class RepeidAdapterConfigService {

    static final RepeidAdapterConfigService INSTANCE = new RepeidAdapterConfigService();

    static final String DEPLOYMENT_NAME = "repeid-server.war";

    private String webContext;


    private RepeidAdapterConfigService() {
    }

    void setWebContext(String webContext) {
        this.webContext = webContext;
    }

    String getWebContext() {
        return webContext;
    }

    boolean isKeycloakServerDeployment(String deploymentName) {
        return DEPLOYMENT_NAME.equals(deploymentName);
    }
}
