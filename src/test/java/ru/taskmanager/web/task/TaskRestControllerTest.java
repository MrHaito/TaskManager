package ru.taskmanager.web.task;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.taskmanager.model.Task;
import ru.taskmanager.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThrows;
import static ru.taskmanager.TaskTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class TaskRestControllerTest {
    private static final Logger log = LoggerFactory.getLogger(TaskRestController.class);
    private static final StringBuilder results = new StringBuilder();

    @ClassRule
    public static ExternalResource summary = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            results.setLength(0);
        }

        @Override
        protected void after() {
            log.info("\n\nTests name and time\n\n" + results + "\n\n");
        }
    };

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("%-80s %d ms", description.getDisplayName(),
                                          TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result).append('\n');
            log.info("Test time: " + TimeUnit.NANOSECONDS.toMillis(nanos) + " ms\n");
        }
    };

    @Autowired
    private TaskRestController controller;

    @Test
    public void clear() {
        controller.clear();
        List<Task> tasks = new ArrayList<>();
        TASK_MATCHER.assertMatch(tasks, controller.getAll());
    }

    @Test
    public void create() {
        Task created = controller.create(getNew());
        int newId = created.getId();
        Task newTask = getNew();
        newTask.setId(newId);
        TASK_MATCHER.assertMatch(created, newTask);
        TASK_MATCHER.assertMatch(controller.get(newId), newTask);
    }

    @Test
    public void duplicateNameCreate() {
        Assert.assertThrows(DataAccessException.class, () ->
                controller.create(new Task(null, task1.getName(), "duplicate", "duplicate", true)));
    }

    @Test
    public void update() {
        Task updated = getUpdated();
        controller.update(updated);
        TASK_MATCHER.assertMatch(controller.get(Task.START_SEQ), getUpdated());
    }

    @Test
    public void get() {
        Task actual = controller.get(Task.START_SEQ);
        TASK_MATCHER.assertMatch(actual, task1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> controller.get(NOT_FOUND));
    }

    @Test
    public void getAll() {
        TASK_MATCHER.assertMatch(controller.getAll(), tasks);
    }

    @Test
    public void delete() {
        controller.delete(task1.getId());
        assertThrows(NotFoundException.class, () -> controller.get(task1.getId()));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
    }
}