package ru.taskmanager.storage;

import ru.taskmanager.model.Task;

import java.util.List;

public interface Storage {
    void clear();

    void save(Task task);

    void update(Task task);

    Task get(String uuid);

    List<Task> getAll();

    void delete(String uuid);

    int size();
}

