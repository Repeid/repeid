package org.repeid.services.resources;

import org.repeid.services.resources.RobotsResource;

public class RobotsResourceImpl implements RobotsResource {

    private static final String robots = "User-agent: *\n" + "Disallow: /";

    public String getRobots() {
        return robots;
    }

}
