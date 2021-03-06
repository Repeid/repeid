package org.repeid.migration;

import org.jboss.logging.Logger;
import org.repeid.migration.migrators.MigrateTo1_0_0;
import org.repeid.migration.migrators.MigrateTo2_0_0;
import org.repeid.models.RepeidSession;

public class MigrationModelManager {

    private static Logger logger = Logger.getLogger(MigrationModelManager.class);

    public static void migrate(RepeidSession session) {
        MigrationModel model = session.organizations().getMigrationModel();
        String storedVersion = model.getStoredVersion();
        if (MigrationModel.LATEST_VERSION.equals(storedVersion))
            return;
        ModelVersion stored = null;
        if (storedVersion != null) {
            stored = new ModelVersion(storedVersion);
        }

        if (stored == null || stored.lessThan(MigrateTo1_0_0.VERSION)) {
            if (stored != null) {
                logger.debug("Migrating older model to 1.0.0 updates");
            }
            new MigrateTo1_0_0().migrate(session);
        }
        if (stored == null || stored.lessThan(MigrateTo2_0_0.VERSION)) {
            if (stored != null) {
                logger.debug("Migrating older model to 2.0.0 updates");
            }
            new MigrateTo2_0_0().migrate(session);
        }

        model.setStoredVersion(MigrationModel.LATEST_VERSION);
    }

}
