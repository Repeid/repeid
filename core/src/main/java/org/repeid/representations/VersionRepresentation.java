package org.repeid.representations;

import org.repeid.common.Version;

public class VersionRepresentation {
	public static final VersionRepresentation SINGLETON;

	private final String version = Version.VERSION;
	private final String buildTime = Version.BUILD_TIME;

	static {
		SINGLETON = new VersionRepresentation();
	}

	public String getVersion() {
		return version;
	}

	public String getBuildTime() {
		return buildTime;
	}
}
