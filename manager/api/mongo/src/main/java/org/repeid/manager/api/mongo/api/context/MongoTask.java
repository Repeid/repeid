package org.repeid.manager.api.mongo.api.context;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public interface MongoTask {

    void execute();

    boolean isFullUpdate();
}
