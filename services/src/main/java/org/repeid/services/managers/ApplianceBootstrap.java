package org.repeid.services.managers;

import org.repeid.models.RepeidSession;
import org.repeid.services.ServicesLogger;

public class ApplianceBootstrap {

    private static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;
    private final RepeidSession session;

    public ApplianceBootstrap(RepeidSession session) {
        this.session = session;
    }

    public boolean isNewInstall() {
        return false;
    }

    public boolean isNoMasterUser() {
        return false;
    }

    public boolean createMasterRealm(String contextPath) {
        return true;
    }

    public void createMasterRealmUser(String username, String password) {

    }

}
