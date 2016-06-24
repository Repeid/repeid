package org.repeid.testsuite.model;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.repeid.models.RepeidSession;
import org.repeid.testsuite.rule.RepeidRule;

public class TransactionsTest {

    @ClassRule
    public static RepeidRule kc = new RepeidRule();

    /*@Test
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
    }*/
}
