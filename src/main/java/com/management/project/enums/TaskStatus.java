package com.management.project.enums;

public enum TaskStatus {
    NOT_STARTED("NOT_STARTED", "NOT STARTED"),
    IN_PROGRESS("IN_PROGRESS", "IN ROGRESS"),
    COMPLETED("COMPLETED", "COMPLETED"),
    ON_HOLD("ON_HOLD", "ON HOLD"),
    CANCELLED("CANCELLED", "CANCELLED");
    private final String name;
    private final String value;

    TaskStatus(String name, String value) {
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
