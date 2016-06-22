package org.repeid.subsystem.server.extension;

import org.jboss.as.ee.component.EEModuleDescription;
import org.jboss.as.server.deployment.DeploymentPhaseContext;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.DeploymentUnitProcessingException;
import org.jboss.as.server.deployment.DeploymentUnitProcessor;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.ServiceTarget;

/**
 * DUP responsible for setting the web context of a Repeid auth server.
 */
public class RepeidServerDeploymentProcessor implements DeploymentUnitProcessor {

    private static final ServiceName cacheContainerService = ServiceName.of("jboss", "infinispan", "repeid");

    @Override
    public void deploy(DeploymentPhaseContext phaseContext) throws DeploymentUnitProcessingException {
        DeploymentUnit deploymentUnit = phaseContext.getDeploymentUnit();
        RepeidAdapterConfigService config = RepeidAdapterConfigService.INSTANCE;
        String deploymentName = deploymentUnit.getName();

        if (!config.isKeycloakServerDeployment(deploymentName)) {
            return;
        }

        final EEModuleDescription description = deploymentUnit.getAttachment(org.jboss.as.ee.component.Attachments.EE_MODULE_DESCRIPTION);
        String webContext = config.getWebContext();
        if (webContext == null) {
            throw new DeploymentUnitProcessingException("Can't determine web context/module for Repeid Server");
        }
        description.setModuleName(webContext);

        addInfinispanCaches(phaseContext);
    }

    private void addInfinispanCaches(DeploymentPhaseContext context) {
        if (context.getServiceRegistry().getService(cacheContainerService) != null) {
            ServiceTarget st = context.getServiceTarget();
            st.addDependency(cacheContainerService);
            st.addDependency(cacheContainerService.append("organizations"));
            st.addDependency(cacheContainerService.append("documents"));
            st.addDependency(cacheContainerService.append("naturalPersons"));
            st.addDependency(cacheContainerService.append("legalPersons"));
            st.addDependency(cacheContainerService.append("work"));
            st.addDependency(cacheContainerService.append("organizationVersions"));
            //st.addDependency(cacheContainerService.append("realms"));
            //st.addDependency(cacheContainerService.append("users"));
            //st.addDependency(cacheContainerService.append("sessions"));
            //st.addDependency(cacheContainerService.append("offlineSessions"));
            //st.addDependency(cacheContainerService.append("loginFailures"));
            //st.addDependency(cacheContainerService.append("work"));
            //st.addDependency(cacheContainerService.append("realmVersions"));
        }
    }

    @Override
    public void undeploy(DeploymentUnit du) {
    }
}
