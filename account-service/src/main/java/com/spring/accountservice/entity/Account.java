package com.spring.accountservice.entity;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Table(value = "accounts")
public class Account implements Serializable {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    @Column(value = "uname")
    private String username;

    @Column(value = "email")
    private String email;

    @Column(value = "pwd")
    private String passwd;

    @Column(value = "created_at")
    private Date createdAt;

    @Column(value = "is_active")
    private Boolean active;

    public Account() {
    }

    public Account(String id) {
        this.id = id;
    }

    public Account(String id, String username, String email, String passwd, Date createdAt, Boolean active) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwd = passwd;
        this.createdAt = createdAt;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Boolean getActive() {
        return active;
    }
}
