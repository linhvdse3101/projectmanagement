package com.management.project.enums;

public enum TaskStatus {
    NOT_STARTED("NOT_STARTED", "NOT_STARTED"),
    IN_PROGRESS("IN_PROGRESS", "IN_PROGRESS"),
    COMPLETED("COMPLETED", "COMPLETED"),
    ON_HOLD("ON_HOLD", "ON_HOLD"),
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

    public static TaskStatus getNameByKey(String value) {
        // Iterate through all the TaskStatus enum values
        for (TaskStatus status : TaskStatus.values()) {
            // If the value matches, return the name
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        // Optionally, throw an exception or return null if no match is found
        throw new IllegalArgumentException("No TaskStatus found for value: " + value);
    }
}
