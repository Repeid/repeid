package org.repeid.services.filters;

import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.repeid.models.RepeidTransaction;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class RepeidTransactionCommitter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        RepeidTransaction tx = ResteasyProviderFactory.getContextData(RepeidTransaction.class);
        if (tx != null && tx.isActive()) {
            if (tx.getRollbackOnly()) {
                tx.rollback();
            } else {
                tx.commit();
            }
        }
    }

}
