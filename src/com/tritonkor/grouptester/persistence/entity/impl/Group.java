package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Group extends Entity {

    private String name;
    private final LocalDate createdAt;
    private List<User> users;

    public Group(UUID id, String name, LocalDate createdAt, List<User> users) {
        super(id);
        this.name = name;
        this.createdAt = createdAt;
        this.users = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
