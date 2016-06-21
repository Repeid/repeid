package org.repeid.subsystem.server.extension;

import org.jboss.as.controller.Extension;
import org.jboss.as.controller.ExtensionContext;
import org.jboss.as.controller.ModelVersion;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.ResourceDefinition;
import org.jboss.as.controller.SubsystemRegistration;
import org.jboss.as.controller.descriptions.StandardResourceDescriptionResolver;
import org.jboss.as.controller.parsing.ExtensionParsingContext;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;
import static org.repeid.subsystem.server.logging.RepeidLogger.ROOT_LOGGER;


/**
 * Main Extension class for the subsystem.
 */
public class RepeidExtension implements Extension {

    static final String SUBSYSTEM_NAME = "repeid-server";
    static final String NAMESPACE = "urn:jboss:domain:repeid-server:1.1";
    static final PathElement PATH_SUBSYSTEM = PathElement.pathElement(SUBSYSTEM, SUBSYSTEM_NAME);

    private static final String RESOURCE_NAME = RepeidExtension.class.getPackage().getName() + ".LocalDescriptions";
    private static final ResourceDefinition REPEID_SUBSYSTEM_RESOURCE = new RepeidSubsystemDefinition();
    private static final RepeidSubsystemParser PARSER = new RepeidSubsystemParser();
    private static final ModelVersion MGMT_API_VERSION = ModelVersion.create(1,1,0);

    static StandardResourceDescriptionResolver getResourceDescriptionResolver(final String... keyPrefix) {
        StringBuilder prefix = new StringBuilder(SUBSYSTEM_NAME);
        for (String kp : keyPrefix) {
            prefix.append('.').append(kp);
        }
        return new StandardResourceDescriptionResolver(prefix.toString(), RESOURCE_NAME, RepeidExtension.class.getClassLoader(), true, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeParsers(final ExtensionParsingContext context) {
        context.setSubsystemXmlMapping(SUBSYSTEM_NAME, NAMESPACE, PARSER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final ExtensionContext context) {
        ROOT_LOGGER.debug("Activating Repeid Extension");
        final SubsystemRegistration subsystem = context.registerSubsystem(SUBSYSTEM_NAME, MGMT_API_VERSION);

        subsystem.registerSubsystemModel(REPEID_SUBSYSTEM_RESOURCE);
        subsystem.registerXMLElementWriter(PARSER);
    }
}
