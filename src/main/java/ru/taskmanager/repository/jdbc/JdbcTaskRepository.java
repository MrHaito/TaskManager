package ru.taskmanager.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.taskmanager.model.Task;
import ru.taskmanager.repository.Storage;

import java.util.List;

@Repository
public class JdbcTaskRepository implements Storage {

    private static final BeanPropertyRowMapper<Task> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Task.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcTaskRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tasks")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean clear() {
        return jdbcTemplate.update("DELETE FROM tasks") != 0;
    }

    @Override
    public Task save(Task task) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("task_id", task.getId())
                .addValue("name", task.getName())
                .addValue("section", task.getSection())
                .addValue("description", task.getDescription())
                .addValue("isWeekly", task.isWeekly())
                .addValue("isMonthly", task.isMonthly());

        if (task.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(map);
            task.setId(newKey.intValue());
        } else {
            if (namedParameterJdbcTemplate.update("" +
                      "UPDATE tasks " +
                      "SET name=:name, section=:section, description=:description, is_weekly=:isWeekly, is_monthly=:isMonthly " +
                      "WHERE id=:task_id", map) == 0) {
                return null;
            }
        }
        return task;
    }

    @Override
    public Task get(int id) {
        List<Task> tasks = jdbcTemplate.query("SELECT * FROM tasks WHERE id=?", ROW_MAPPER, id);
        return DataAccessUtils.singleResult(tasks);
    }

    @Override
    public List<Task> getAll() {
        return jdbcTemplate.query("SELECT * FROM tasks", ROW_MAPPER);
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM tasks WHERE id=?", id) != 0;
    }
}
