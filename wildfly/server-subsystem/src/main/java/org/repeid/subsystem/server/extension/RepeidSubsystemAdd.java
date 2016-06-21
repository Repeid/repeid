package org.repeid.subsystem.server.extension;

import static org.repeid.subsystem.server.extension.RepeidExtension.SUBSYSTEM_NAME;
import static org.repeid.subsystem.server.extension.RepeidSubsystemDefinition.ALL_ATTRIBUTES;
import static org.repeid.subsystem.server.extension.RepeidSubsystemDefinition.WEB_CONTEXT;

import org.jboss.as.controller.AbstractBoottimeAddStepHandler;
import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.registry.Resource;
import org.jboss.as.server.AbstractDeploymentChainStep;
import org.jboss.as.server.DeploymentProcessorTarget;
import org.jboss.as.server.deployment.Phase;
import org.jboss.dmr.ModelNode;

/**
 * The Repeid subsystem add update handler.
 */
class RepeidSubsystemAdd extends AbstractBoottimeAddStepHandler {

    static final RepeidSubsystemAdd INSTANCE = new RepeidSubsystemAdd();

    @Override
    protected void performBoottime(final OperationContext context, final ModelNode operation, final ModelNode model) {
        context.addStep(new AbstractDeploymentChainStep() {
            @Override
            protected void execute(DeploymentProcessorTarget processorTarget) {
                processorTarget.addDeploymentProcessor(SUBSYSTEM_NAME,
                        Phase.POST_MODULE, // PHASE
                        Phase.POST_MODULE_VALIDATOR_FACTORY - 1, // PRIORITY
                        new RepeidServerDeploymentProcessor());
            }
        }, OperationContext.Stage.RUNTIME);
    }

    protected void populateModel(final OperationContext context, final ModelNode operation, final Resource resource) throws  OperationFailedException {
        ModelNode model = resource.getModel();

        // set attribute values from parsed model
        for (AttributeDefinition attrDef : ALL_ATTRIBUTES) {
            attrDef.validateAndSet(operation, model);
        }

        // returns early if on domain controller
        if (!requiresRuntime(context)) {
            return;
        }

        // don't want to try to start server on host controller
        if (!context.isNormalServer()) {
            return;
        }

        ModelNode webContextNode = resource.getModel().get(WEB_CONTEXT.getName());
        if (!webContextNode.isDefined()) {
            webContextNode = WEB_CONTEXT.getDefaultValue();
        }
        String webContext = webContextNode.asString();

        ServerUtil serverUtil = new ServerUtil(operation);
        serverUtil.addStepToUploadServerWar(context);
        RepeidAdapterConfigService.INSTANCE.setWebContext(webContext);
    }
}
