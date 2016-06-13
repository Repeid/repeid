
package org.repeid.services.scheduled;

import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;
import org.repeid.services.ServicesLogger;
import org.repeid.timer.ScheduledTask;

public class ScheduledTaskRunner implements Runnable {

    protected static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

    protected final RepeidSessionFactory sessionFactory;
    protected final ScheduledTask task;

    public ScheduledTaskRunner(RepeidSessionFactory sessionFactory, ScheduledTask task) {
        this.sessionFactory = sessionFactory;
        this.task = task;
    }

    @Override
    public void run() {
    	RepeidSession session = sessionFactory.create();
        try {
            runTask(session);
        } catch (Throwable t) {
            logger.failedToRunScheduledTask(t, task.getClass().getSimpleName());

            session.getTransaction().rollback();
        } finally {
            try {
                session.close();
            } catch (Throwable t) {
                logger.failedToCloseProviderSession(t);
            }
        }
    }

    protected void runTask(RepeidSession session) {
        session.getTransaction().begin();
        task.run(session);
        session.getTransaction().commit();

        logger.debug("Executed scheduled task " + task.getClass().getSimpleName());
    }

}
