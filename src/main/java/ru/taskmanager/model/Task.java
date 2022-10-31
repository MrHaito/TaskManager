package ru.taskmanager.model;

public class Task {
    public static final int START_SEQ = 100000;

    private Integer id;
    private String name;
    private String section;
    private String description;
    boolean isWeekly;
    boolean isMonthly = true;

    public Task() {

    }

    public Task(Task t) {
        this(t.id, t.name, t.section, t.description, t.isWeekly);
    }

    public Task(String name, String section, String description) {
        this(null, name, section, description, false);
        this.isMonthly = true;
    }

    public Task(String name, String section, String description, boolean isWeekly) {
        this(null, name, section, description, isWeekly);
        this.isMonthly = true;
    }

    public Task(Integer id, String name, String section, String description, boolean isWeekly) {
        this.id = id;
        this.name = name;
        this.section = section;
        this.description = description;
        this.isWeekly = isWeekly;
        this.isMonthly = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public boolean isWeekly() {
        return isWeekly;
    }

    public void setWeekly(boolean weekly) {
        isWeekly = weekly;
    }

    public boolean isMonthly() {
        return isMonthly;
    }

    public void setMonthly(boolean monthly) {
        isMonthly = monthly;
    }

    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public String toString() {
        return "Task{" + "name='" + name + '\'' + ", description='" + description + '\'' + '}';
    }
}
