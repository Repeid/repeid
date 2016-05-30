package org.repeid.timer;

import org.repeid.models.RepeidSession;

public interface ScheduledTask {

    public void run(RepeidSession session);

}
