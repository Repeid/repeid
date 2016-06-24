package org.repeid.testsuite.rule;

import org.jboss.logging.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class LoggingRule implements TestRule {

    private final Logger log;

    public LoggingRule(Object test) {
        log = Logger.getLogger(test.getClass());
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                log.debugf("Before %s", description.getMethodName());

                try {
                    base.evaluate();
                } finally {
                    log.debugf("After %s", description.getMethodName());
                }

            }

        };
    }

}
