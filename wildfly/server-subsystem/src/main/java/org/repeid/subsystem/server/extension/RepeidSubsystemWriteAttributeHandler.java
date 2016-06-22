package org.repeid.subsystem.server.extension;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinition;

import java.util.List;
import org.jboss.as.controller.ModelOnlyWriteAttributeHandler;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.registry.Resource;
import org.jboss.dmr.ModelNode;

/**
 * Update an attribute on an Auth Server.
 */
public class RepeidSubsystemWriteAttributeHandler extends ModelOnlyWriteAttributeHandler { //extends ReloadRequiredWriteAttributeHandler {

    public RepeidSubsystemWriteAttributeHandler(List<SimpleAttributeDefinition> definitions) {
        this(definitions.toArray(new AttributeDefinition[definitions.size()]));
    }

    public RepeidSubsystemWriteAttributeHandler(AttributeDefinition... definitions) {
        super(definitions);
    }

    @Override
    protected void finishModelStage(OperationContext context, ModelNode operation, String attributeName, ModelNode newValue, ModelNode oldValue, Resource model) throws OperationFailedException {
        if (!context.isNormalServer() || attribNotChanging(attributeName, newValue, oldValue)) {
            super.finishModelStage(context, operation, attributeName, newValue, oldValue, model);
            return;
        }

        String deploymentName = ServerUtil.getDeploymentName(operation);

        if (attributeName.equals(RepeidSubsystemDefinition.WEB_CONTEXT.getName())) {
            RepeidAdapterConfigService.INSTANCE.setWebContext(newValue.asString());
            ServerUtil.addStepToRedeployServerWar(context, deploymentName);
        }

        super.finishModelStage(context, operation, attributeName, newValue, oldValue, model);
    }

    private boolean attribNotChanging(String attributeName, ModelNode newValue, ModelNode oldValue) {
        SimpleAttributeDefinition attribDef = RepeidSubsystemDefinition.lookup(attributeName);
        if (!oldValue.isDefined()) {
            oldValue = attribDef.getDefaultValue();
        }
        if (!newValue.isDefined()) {
            newValue = attribDef.getDefaultValue();
        }
        return newValue.equals(oldValue);
    }
}
