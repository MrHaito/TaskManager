package ru.taskmanager.util;

import ru.taskmanager.model.Task;
import ru.taskmanager.util.exception.NotFoundException;

public class ValidationUtil {
    public static Task checkNotFoundWidthId(Task task, int id) {
        checkNotFoundWidthId(task != null, id);
        return task;
    }

    public static void checkNotFoundWidthId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static Task checkNotFound(Task task, String msg) {
        checkNotFound(task != null, msg);
        return task;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found task width " + msg);
        }
    }

    public static void checkNew(Task task) {
        if (!task.isNew()) {
            throw new IllegalArgumentException(task + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(Task task, int id) {
        if (task.isNew()) {
            task.setId(id);
        } else if (task.getId() != id) {
            throw new IllegalArgumentException(task + " must be with id=" + id);
        }
    }
}
