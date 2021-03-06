package org.repeid.timer;

import org.repeid.provider.Provider;

public interface TimerProvider extends Provider {

    public void schedule(Runnable runnable, long intervalMillis, String taskName);

    public void scheduleTask(ScheduledTask scheduledTask, long intervalMillis, String taskName);

    public void cancelTask(String taskName);

}
