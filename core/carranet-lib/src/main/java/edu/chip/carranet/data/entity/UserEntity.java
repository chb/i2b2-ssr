/*
 *
 *  * ${file_name}
 *  *
 *  * Copyright (c) ${year}, YOUR_NAME. All rights reserved.
 *  *
 *  * This library is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU Lesser General Public
 *  * License as published by the Free Software Foundation; either
 *  * version 2.1 of the License, or (at your option) any later version.
 *  *
 *  * This library is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  * Lesser General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU Lesser General Public
 *  * License along with this library; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *  * MA 02110-1301  USA
 *
 * ${package_declaration}
 *
 * ${typecomment}
 * ${type_declaration}
 */

package edu.chip.carranet.data.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "USER")
public class UserEntity {

    private Long id;
    private String userName;
    private String domain;
    private String passwordHash;

    Set<AssertionEntity> assertions = new HashSet<AssertionEntity>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }



    @OneToMany(cascade = {CascadeType.ALL} )
    @JoinColumn(name = "user_fk")

    public Set<AssertionEntity> getAssertions() {
        return assertions;
    }

    public void setAssertions(Set<AssertionEntity> assertions) {
        this.assertions = assertions;
    }

    @Basic
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
