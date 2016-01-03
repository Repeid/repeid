package org.repeid.representations.idm.system;

public class SystemStatusRepresentation {

    private String id;
    private String name;
    private String description;
    private String moreInfo;
    private String version;
    private String builtOn;
    private boolean up;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBuiltOn() {
        return builtOn;
    }

    public void setBuiltOn(String builtOn) {
        this.builtOn = builtOn;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

}
