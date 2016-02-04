package org.repeid.manager.api.model.utils;

import java.util.UUID;

/**
 * Set of helper methods, which are useful in various model implementations.
 *
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public final class RepeidModelUtils {

	private RepeidModelUtils() {
	}

	public static String generateId() {
		return UUID.randomUUID().toString();
	}

	public static String generateCodeSecret() {
		return UUID.randomUUID().toString();
	}

}
