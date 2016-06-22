package org.repeid.subsystem.server.extension;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.operations.common.GenericSubsystemDescribeHandler;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Definition of subsystem=repeid-server.
 */
public class RepeidSubsystemDefinition extends SimpleResourceDefinition {

    static final SimpleAttributeDefinition WEB_CONTEXT =
        new SimpleAttributeDefinitionBuilder("web-context", ModelType.STRING, true)
            .setAllowExpression(true)
            .setDefaultValue(new ModelNode("repeid"))
            .setRestartAllServices()
            .build();

    static final List<SimpleAttributeDefinition> ALL_ATTRIBUTES = new ArrayList<SimpleAttributeDefinition>();

    static {
        ALL_ATTRIBUTES.add(WEB_CONTEXT);
    }

    private static final Map<String, SimpleAttributeDefinition> DEFINITION_LOOKUP = new HashMap<String, SimpleAttributeDefinition>();
    static {
        for (SimpleAttributeDefinition def : ALL_ATTRIBUTES) {
            DEFINITION_LOOKUP.put(def.getXmlName(), def);
        }
    }

    private static RepeidSubsystemWriteAttributeHandler attrHandler = new RepeidSubsystemWriteAttributeHandler(ALL_ATTRIBUTES);

    protected RepeidSubsystemDefinition() {
        super(RepeidExtension.PATH_SUBSYSTEM,
            RepeidExtension.getResourceDescriptionResolver("subsystem"),
            RepeidSubsystemAdd.INSTANCE,
            RepeidSubsystemRemoveHandler.INSTANCE
        );
    }

    @Override
    public void registerOperations(ManagementResourceRegistration resourceRegistration) {
        super.registerOperations(resourceRegistration);
        resourceRegistration.registerOperationHandler(GenericSubsystemDescribeHandler.DEFINITION, GenericSubsystemDescribeHandler.INSTANCE);
    }

    @Override
    public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
        super.registerAttributes(resourceRegistration);
        for (AttributeDefinition attrDef : ALL_ATTRIBUTES) {
            resourceRegistration.registerReadWriteAttribute(attrDef, null, attrHandler);
        }
    }

    public static SimpleAttributeDefinition lookup(String name) {
        return DEFINITION_LOOKUP.get(name);
    }
}
