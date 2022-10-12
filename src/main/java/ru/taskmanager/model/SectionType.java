package ru.taskmanager.model;

public enum SectionType {
    GENERAL("Общее"),
    FIGURE_SKATING("Фигурное катание"),
    GYMNASTIC("Гимнастика"),
    HEALTH("Здоровье"),
    BASKETBALL("Баскетболл");

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
