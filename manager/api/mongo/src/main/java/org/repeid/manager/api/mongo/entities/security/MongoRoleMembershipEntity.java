/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.repeid.manager.api.mongo.entities.security;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

/**
 * A single, qualified, role granted to the user. Roles in the system might
 * include things like "Service Owner", "Application Developer", etc. A role is
 * qualified by an Organization ID. The purpose of a role is to grant
 * permissions to a user. A role might grant CREATE-APPLICATION and VIEW-SERVICE
 * permissions for a particular Organization.
 *
 * @author eric.wittmann@redhat.com
 */
@Entity
@Table(name = "memberships", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "role_id" }) })
@NamedQueries(value = {
        @NamedQuery(name = "MongoRoleMembershipEntity.findAll", query = "SELECT r FROM RoleMembershipEntity r"),
        @NamedQuery(name = "MongoRoleMembershipEntity.findByUserId", query = "SELECT r FROM RoleMembershipEntity r INNER JOIN r.user u WHERE u.id = :userId"),
        @NamedQuery(name = "MongoRoleMembershipEntity.findByRoleId", query = "SELECT r FROM RoleMembershipEntity r INNER JOIN r.role rr WHERE rr.id = :roleId")})
public class MongoRoleMembershipEntity implements Serializable {

    private static final long serialVersionUID = 7798709783947356888L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey , name = "user_id")
    private MongoUserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey , name = "role_id")
    private MongoRoleEntity role;

    @Column(name = "created_on")
    private Date createdOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MongoUserEntity getUser() {
        return user;
    }

    public void setUser(MongoUserEntity user) {
        this.user = user;
    }

    public MongoRoleEntity getRole() {
        return role;
    }

    public void setRole(MongoRoleEntity role) {
        this.role = role;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "RoleMembershipEntity [id=" + id + ", userId=" + user.getId() + ", roleId=" + role.getId()
                + ", createdOn=" + createdOn + "]";
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MongoRoleMembershipEntity other = (MongoRoleMembershipEntity) obj;
        if (getId() == null) {
            if (other.getId() != null)
                return false;
        } else if (!getId().equals(other.getId()))
            return false;
        return true;
    }
}
