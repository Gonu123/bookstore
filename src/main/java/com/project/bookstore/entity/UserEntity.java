package com.project.bookstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name="users")
public class UserEntity {

    @Id
    @Column(name="id")
    public String userid;

    public String username,  name,  password;

    @Column(name="ph_no")
    public String phNo;

    public UserEntity(String userid, String username, String name, String phNo, String password) {
        this.userid = userid;
        this.username = username;
        this.name = name;
        this.phNo = phNo;
        this.password = password;
    }

    public UserEntity() {
    }

    public String getUserid() {
        return userid;
    }
}
