package org.repeid.services.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.repeid.models.RepeidTransaction;

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
