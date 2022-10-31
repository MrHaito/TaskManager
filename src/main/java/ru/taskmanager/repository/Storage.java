package ru.taskmanager.repository;

import ru.taskmanager.model.Task;

import java.util.List;

public interface Storage {
    boolean clear();

    Task save(Task task);

    Task get(int id);

    List<Task> getAll();

    boolean delete(int id);
}

