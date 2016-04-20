/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.repeid.services.filters;

import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.repeid.common.ClientConnection;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;
import org.repeid.models.RepeidTransaction;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RepeidSessionServletFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");

        final HttpServletRequest request = (HttpServletRequest) servletRequest;

        RepeidSessionFactory sessionFactory = (RepeidSessionFactory) servletRequest.getServletContext().getAttribute(RepeidSessionFactory.class.getName());
        RepeidSession session = sessionFactory.create();
        ResteasyProviderFactory.pushContext(RepeidSession.class, session);
        ClientConnection connection = new ClientConnection() {
            @Override
            public String getRemoteAddr() {
                return request.getRemoteAddr();
            }

            @Override
            public String getRemoteHost() {
                return request.getRemoteHost();
            }

            @Override
            public int getReportPort() {
                return request.getRemotePort();
            }
        };
        session.getContext().setConnection(connection);
        ResteasyProviderFactory.pushContext(ClientConnection.class, connection);

        RepeidTransaction tx = session.getTransaction();
        ResteasyProviderFactory.pushContext(RepeidTransaction.class, tx);
        tx.begin();

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // RepeidTransactionCommitter is responsible for committing the transaction, but if an exception is thrown it's not invoked and transaction
            // should be rolled back
            if (session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }

            session.close();
            ResteasyProviderFactory.clearContextData();
        }
    }

    @Override
    public void destroy() {
    }
}
