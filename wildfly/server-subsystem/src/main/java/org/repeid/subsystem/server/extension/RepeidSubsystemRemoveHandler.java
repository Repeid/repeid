package org.repeid.subsystem.server.extension;

import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.OperationStepHandler;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.ReloadRequiredRemoveStepHandler;
import org.jboss.as.controller.operations.common.Util;
import org.jboss.dmr.ModelNode;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.DEPLOYMENT;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.REMOVE;
import org.jboss.as.controller.registry.ImmutableManagementResourceRegistration;

/**
 * Remove an auth-server from a realm.
 */
public final class RepeidSubsystemRemoveHandler extends ReloadRequiredRemoveStepHandler {

    static RepeidSubsystemRemoveHandler INSTANCE = new RepeidSubsystemRemoveHandler();

    private RepeidSubsystemRemoveHandler() {}

    @Override
    protected void performRemove(OperationContext context, ModelNode operation, ModelNode model) throws OperationFailedException {
        String deploymentName = ServerUtil.getDeploymentName(operation);
        RepeidAdapterConfigService.INSTANCE.setWebContext(null);

        if (requiresRuntime(context)) { // don't do this on a domain controller
            addStepToRemoveServerWar(context, deploymentName);
        }

        super.performRemove(context, operation, model);
    }

    private void addStepToRemoveServerWar(OperationContext context, String deploymentName) {
        PathAddress deploymentAddress = PathAddress.pathAddress(PathElement.pathElement(DEPLOYMENT, deploymentName));
        ModelNode op = Util.createOperation(REMOVE, deploymentAddress);
        context.addStep(op, getRemoveHandler(context, deploymentAddress), OperationContext.Stage.MODEL);
    }

    private OperationStepHandler getRemoveHandler(OperationContext context, PathAddress address) {
        ImmutableManagementResourceRegistration rootResourceRegistration = context.getRootResourceRegistration();
        return rootResourceRegistration.getOperationHandler(address, REMOVE);
    }
}
