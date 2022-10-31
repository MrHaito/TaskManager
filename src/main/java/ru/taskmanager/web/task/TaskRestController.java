package ru.taskmanager.web.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.taskmanager.model.Task;
import ru.taskmanager.repository.jdbc.JdbcTaskRepository;

import java.util.List;

import static ru.taskmanager.util.ValidationUtil.*;

@Controller
public class TaskRestController {
    private static final Logger log = LoggerFactory.getLogger(TaskRestController.class);

    private final JdbcTaskRepository repository;

    public TaskRestController(JdbcTaskRepository repository) {
        this.repository = repository;
    }

    public void clear() {
        log.info("delete all tasks");
        repository.clear();
    }

    public Task create(Task task) {
        checkNew(task);
        log.info("create {}", task);
        return repository.save(task);
    }

    public Task update(Task task) {
        assureIdConsistent(task, task.getId());
        log.info("update {}", task);
        return repository.save(task);
    }

    public Task get(int id) {
        log.info("get task {}", id);
        return checkNotFoundWidthId(repository.get(id), id);
    }

    public List<Task> getAll() {
        log.info("getAll");
        return repository.getAll();
    }

    public void delete(int id) {
        log.info("delete task {}", id);
        checkNotFoundWidthId(repository.delete(id), id);
    }
}
