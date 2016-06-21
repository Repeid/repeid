package org.repeid.subsystem.server.logging;

import org.jboss.logging.BasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.annotations.MessageLogger;

/**
 * This interface to be fleshed out later when error messages are fully externalized.
 */
@MessageLogger(projectCode = "REPEID")
public interface RepeidLogger extends BasicLogger {

    /**
     * A logger with a category of the package name.
     */
    RepeidLogger ROOT_LOGGER = Logger.getMessageLogger(RepeidLogger.class, "org.jboss.keycloak");
}
