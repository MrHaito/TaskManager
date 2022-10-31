package ru.taskmanager;

import ru.taskmanager.model.Task;
import ru.taskmanager.web.task.MatcherFactory;

import java.util.Arrays;
import java.util.List;

public class TaskTestData {
    public static final MatcherFactory.Matcher<Task> TASK_MATCHER = MatcherFactory.usingIgnoringFieldsComparator("isWeekly", "isMonthly");

    public static final int NOT_FOUND = Task.START_SEQ + 4;

    public static final Task task1 = new Task(Task.START_SEQ, "Гран-при России", "Фигурное катание", "2-й этап", false);
    public static final Task task2 = new Task(Task.START_SEQ + 1, "Собрать тренеров без фото", "Баскетбол", "", false);
    public static final Task task3 = new Task(Task.START_SEQ + 2, "Теги", "Гимнастика", "Массовая проработка", false);

    public static final List<Task> tasks = Arrays.asList(task1, task2, task3);

    public static Task getNew() {
        return new Task(null, "Созданная задача", "Созданное описание", "Созданное", true);
    }

    public static Task getUpdated() {
        return new Task(Task.START_SEQ, "Обновленная задача", "Обновленное описание", "Обновленное", true);
    }
}
