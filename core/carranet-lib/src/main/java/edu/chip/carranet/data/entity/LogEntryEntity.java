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


import edu.chip.carranet.data.EventType;

import javax.persistence.*;

/**
 * @author Dave Ortiz
 * @date 1/6/12
 * @link http://cbmi.med.harvard.edu
 * @link http://chip.org
 * <p/>
 * NOTICE: This software comes with NO guarantees whatsoever and is
 * licensed as Lgpl Open Source
 * @link http://www.gnu.org/licenses/lgpl.html
 */


@Entity
@Table(name = "LOG_EVENT")
public class LogEntryEntity {

    private Long id;
    private EventType type;
    private String user;
    private String messageText;
    

    public LogEntryEntity(){}
    
    public LogEntryEntity(EventType type, String user, String messageText){
        this.type = type;
        this.messageText = messageText;
        this.user = user;
    }
    
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }


    @Enumerated(EnumType.STRING)
    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @Basic
    @Column(length = 10000)
    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    
    @Basic
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
