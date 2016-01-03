package org.repeid.models.security;

import java.util.Date;

import org.repeid.models.Model;

public interface UserModel extends Model {

    String getUsername();

    String getFullName();

    void setFullName(String fullName);

    String getEmail();

    void setEmail(String email);

    Date getJoinedOn();

    boolean isAdmin();

}