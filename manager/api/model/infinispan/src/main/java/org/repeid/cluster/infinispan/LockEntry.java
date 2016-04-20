package org.repeid.cluster.infinispan;

import java.io.Serializable;

public class LockEntry implements Serializable {

    private String node;
    private int timestamp;

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
