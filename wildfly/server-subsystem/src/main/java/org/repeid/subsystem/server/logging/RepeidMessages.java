package org.repeid.subsystem.server.logging;

import org.jboss.logging.Messages;
import org.jboss.logging.annotations.MessageBundle;

/**
 * This interface to be fleshed out later when error messages are fully externalized.
 */
@MessageBundle(projectCode = "REPEID")
public interface RepeidMessages {

    /**
     * The messages
     */
    RepeidMessages MESSAGES = Messages.getBundle(RepeidMessages.class);
}
