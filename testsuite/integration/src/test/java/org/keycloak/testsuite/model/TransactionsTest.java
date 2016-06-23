package org.keycloak.testsuite.model;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.keycloak.testsuite.rule.KeycloakRule;
import org.repeid.models.RepeidSession;

public class TransactionsTest {

    @ClassRule
    public static KeycloakRule kc = new KeycloakRule();

    @Test
    public void testTransactionActive() {
        RepeidSession session = kc.startSession();

        Assert.assertTrue(session.getTransaction().isActive());
        session.getTransaction().commit();
        Assert.assertFalse(session.getTransaction().isActive());

        session.getTransaction().begin();
        Assert.assertTrue(session.getTransaction().isActive());
        session.getTransaction().rollback();
        Assert.assertFalse(session.getTransaction().isActive());

        session.close();
    }
}
