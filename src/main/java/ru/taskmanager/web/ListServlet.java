package ru.taskmanager.web;

import ru.taskmanager.Config;
import ru.taskmanager.model.Task;
import ru.taskmanager.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            req.setAttribute("tasks", storage.getAll());
            req.getRequestDispatcher("views/list.jsp").forward(req, resp);
            return;
        }
        switch (action) {
            case "add" -> {
                Task task = new Task("", "");
                req.setAttribute("task", task);
            }
        }
        req.getRequestDispatcher("/views/add.jsp").forward(req, resp);
    }
}
