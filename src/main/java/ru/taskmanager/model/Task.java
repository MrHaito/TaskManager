package ru.taskmanager.model;

import java.util.Objects;
import java.util.UUID;

public class Task {
    private final String uuid;
    private final String name;
    private final String section;
    private final String description;

    public Task(String name, String section) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.section = Objects.requireNonNull(section, "Необходимо выбрать секцию");
        this.description = "";
    }

    public Task(String name, String section, String description) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.section = Objects.requireNonNull(section, "Необходимо выбрать секцию");
        this.description = description;
    }

    public Task(String uuid, String name, String section, String description) {
        this.uuid = uuid;
        this.name = name;
        this.section = Objects.requireNonNull(section, "Необходимо выбрать секцию");
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSection() {
        return section;
    }

    @Override
    public String toString() {
        return "Task{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return getName().equals(task.getName()) && Objects.equals(getDescription(), task.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription());
    }
}
