package org.repeid.manager.api.model.security;

import java.util.Date;

import org.repeid.manager.api.model.Model;

public interface UserModel extends Model {

	String getId();

	String getUsername();

	String getFullName();

	void setFullName(String fullName);

	String getEmail();

	void setEmail(String email);

	Date getJoinedOn();

	boolean isAdmin();

}