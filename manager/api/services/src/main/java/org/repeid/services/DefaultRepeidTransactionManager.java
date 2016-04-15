package org.repeid.services;

import java.util.LinkedList;
import java.util.List;

import org.repeid.models.RepeidTransaction;
import org.repeid.models.RepeidTransactionManager;

public class DefaultRepeidTransactionManager implements RepeidTransactionManager {

    public static final ServicesLogger logger = ServicesLogger.ROOT_LOGGER;

    private List<RepeidTransaction> prepare = new LinkedList<RepeidTransaction>();
    private List<RepeidTransaction> transactions = new LinkedList<RepeidTransaction>();
    private List<RepeidTransaction> afterCompletion = new LinkedList<RepeidTransaction>();
    private boolean active;
    private boolean rollback;

    @Override
    public void enlist(RepeidTransaction transaction) {
        if (active && !transaction.isActive()) {
            transaction.begin();
        }

        transactions.add(transaction);
    }

    @Override
    public void enlistAfterCompletion(RepeidTransaction transaction) {
        if (active && !transaction.isActive()) {
            transaction.begin();
        }

        afterCompletion.add(transaction);
    }

    @Override
    public void enlistPrepare(RepeidTransaction transaction) {
        if (active && !transaction.isActive()) {
            transaction.begin();
        }

        prepare.add(transaction);
    }

    @Override
    public void begin() {
        if (active) {
            throw new IllegalStateException("Transaction already active");
        }

        for (RepeidTransaction tx : transactions) {
            tx.begin();
        }

        active = true;
    }

    @Override
    public void commit() {
        RuntimeException exception = null;
        for (RepeidTransaction tx : prepare) {
            try {
                tx.commit();
            } catch (RuntimeException e) {
                exception = exception == null ? e : exception;
            }
        }
        if (exception != null) {
            rollback(exception);
            return;
        }
        for (RepeidTransaction tx : transactions) {
            try {
                tx.commit();
            } catch (RuntimeException e) {
                exception = exception == null ? e : exception;
            }
        }

        // Don't commit "afterCompletion" if commit of some main transaction
        // failed
        if (exception == null) {
            for (RepeidTransaction tx : afterCompletion) {
                try {
                    tx.commit();
                } catch (RuntimeException e) {
                    exception = exception == null ? e : exception;
                }
            }
        } else {
            for (RepeidTransaction tx : afterCompletion) {
                try {
                    tx.rollback();
                } catch (RuntimeException e) {
                    logger.exceptionDuringRollback(e);
                }
            }
        }

        active = false;
        if (exception != null) {
            throw exception;
        }
    }

    @Override
    public void rollback() {
        RuntimeException exception = null;
        rollback(exception);
    }

    protected void rollback(RuntimeException exception) {
        for (RepeidTransaction tx : transactions) {
            try {
                tx.rollback();
            } catch (RuntimeException e) {
                exception = exception != null ? e : exception;
            }
        }
        for (RepeidTransaction tx : afterCompletion) {
            try {
                tx.rollback();
            } catch (RuntimeException e) {
                exception = exception != null ? e : exception;
            }
        }
        active = false;
        if (exception != null) {
            throw exception;
        }
    }

    @Override
    public void setRollbackOnly() {
        rollback = true;
    }

    @Override
    public boolean getRollbackOnly() {
        if (rollback) {
            return true;
        }

        for (RepeidTransaction tx : transactions) {
            if (tx.getRollbackOnly()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

}
