/*
 * Repeid for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.repeid.common.auth;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

/**
 * An implementation of a principal, but also includes roles.
 *
 * @author eric.wittmann@redhat.com
 */
public class AuthPrincipal implements Principal {

	private String username;
	private Set<String> roles = new HashSet<>();

	/**
	 * Constructor.
	 * 
	 * @param username
	 *            the username
	 */
	public AuthPrincipal(String username) {
		this.username = username;
	}

	/**
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName() {
		return username;
	}

	/**
	 * Adds a role.
	 * 
	 * @param role
	 *            the role
	 */
	public void addRole(String role) {
		roles.add(role);
	}

	/**
	 * Adds multiple roles.
	 * 
	 * @param roles
	 *            the roles
	 */
	public void addRoles(Set<String> roles) {
		this.roles.addAll(roles);
	}

	/**
	 * @return the roles
	 */
	public Set<String> getRoles() {
		return roles;
	}
}
