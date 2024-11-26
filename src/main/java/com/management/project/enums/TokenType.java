package com.management.project.enums;

public enum TokenType {
    BEARER("bearer", "bearer");
    private final String name;
    private final String value;

    // Constructor
    TokenType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for value
    public String getValue() {
        return value;
    }

    // Optionally override toString() to return a specific value
    @Override
    public String toString() {
        return this.name; // Or return value, depending on your need
    }
}
