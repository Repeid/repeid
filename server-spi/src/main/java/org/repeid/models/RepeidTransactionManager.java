package org.repeid.models;

public interface RepeidTransactionManager extends RepeidTransaction {

    void enlist(RepeidTransaction transaction);

    void enlistAfterCompletion(RepeidTransaction transaction);

    void enlistPrepare(RepeidTransaction transaction);

}
