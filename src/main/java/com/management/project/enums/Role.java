package com.management.project.enums;

public enum Role {
    USER("user", "user"),
    ADMIN("admin", "admin");
    private final String name;
    private final String value;

    Role(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
