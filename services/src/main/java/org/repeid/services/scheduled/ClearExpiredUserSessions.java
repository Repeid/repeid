package org.repeid.services.scheduled;

import org.repeid.models.RepeidSession;
import org.repeid.timer.ScheduledTask;

public class ClearExpiredUserSessions implements ScheduledTask {

    @Override
    public void run(RepeidSession session) {
        /*UserSessionProvider sessions = session.sessions();
        for (RealmModel realm : session.realms().getRealms()) {
            sessions.removeExpired(realm);
        }*/
    }

}
