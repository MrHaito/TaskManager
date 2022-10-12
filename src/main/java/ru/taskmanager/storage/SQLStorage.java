package ru.taskmanager.storage;

import ru.taskmanager.exception.NotExistStorageException;
import ru.taskmanager.model.Task;
import ru.taskmanager.sql.SQLHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SQLStorage implements Storage {
    public final SQLHelper sqlHelper;

    private static final Logger LOG = Logger.getLogger(SQLStorage.class.getName());

    public SQLStorage(String dbURL, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SQLHelper(() -> DriverManager.getConnection(dbURL, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        sqlHelper.execute("DELETE FROM tasks");
    }

    @Override
    public void save(Task task) {
        LOG.info("Save");
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("insert into tasks (uuid, name, description, " +
                    "section_type) " + "values (?, ?, ?, ?)")) {
                ps.setString(1, task.getUuid());
                ps.setString(2, task.getName());
                ps.setString(3, task.getDescription());
                ps.setString(4, task.getSection());
                ps.execute();
            }
            return null;
        });
    }

    @Override
    public void update(Task task) {
        LOG.info("Update " + task);
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE tasks SET name = ?, description = ?, " +
                    "section_type = ? where" + " uuid = ?")) {
                ps.setString(1, task.getName());
                ps.setString(2, task.getDescription());
                ps.setString(3, task.getSection());
                ps.setString(4, task.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(task.getUuid());
                }
            }
            return null;
        });
    }

    @Override
    public Task get(String uuid) {
        LOG.info("Get " + uuid);
        return sqlHelper.transactionalExecute(connection -> {
            Task task;
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM tasks WHERE uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                task = new Task(uuid, rs.getString("name"), rs.getString("section_type"),
                        rs.getString("description"));
            }
            return task;
        });
    }

    @Override
    public List<Task> getAll() {
        LOG.info("Get All");
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Task> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM tasks ORDER BY name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Task(uuid, rs.getString("name"), rs.getString("section_type"),
                            rs.getString("description")));
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("DELETE FROM tasks WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM tasks", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}
