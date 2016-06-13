package org.repeid.services.scheduled;

import java.util.concurrent.Callable;

import org.repeid.cluster.ClusterProvider;
import org.repeid.cluster.ExecutionResult;
import org.repeid.models.RepeidSession;
import org.repeid.models.RepeidSessionFactory;
import org.repeid.timer.ScheduledTask;

/**
 * Ensures that there are not concurrent executions of same task (either on this host or any other cluster host)
 */
public class ClusterAwareScheduledTaskRunner extends ScheduledTaskRunner {

    private final int intervalSecs;

    public ClusterAwareScheduledTaskRunner(RepeidSessionFactory sessionFactory, ScheduledTask task, long intervalMillis) {
        super(sessionFactory, task);
        this.intervalSecs = (int) (intervalMillis / 1000);
    }

    @Override
    protected void runTask(final RepeidSession session) {
        session.getTransaction().begin();

        ClusterProvider clusterProvider = session.getProvider(ClusterProvider.class);
        String taskKey = task.getClass().getSimpleName();

        ExecutionResult<Void> result = clusterProvider.executeIfNotExecuted(taskKey, intervalSecs, new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                task.run(session);
                return null;
            }

        });

        session.getTransaction().commit();

        if (result.isExecuted()) {
            logger.debugf("Executed scheduled task %s", taskKey);
        } else {
            logger.debugf("Skipped execution of task %s as other cluster node is executing it", taskKey);
        }
    }


}
