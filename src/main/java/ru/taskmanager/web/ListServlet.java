package ru.taskmanager.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import ru.taskmanager.model.Task;
import ru.taskmanager.web.task.TaskRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class ListServlet extends HttpServlet {
    private ConfigurableApplicationContext springContext;
    private TaskRestController taskController;

    @Override
    public void init() {
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml");
        taskController = springContext.getBean(TaskRestController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete" -> {
                int id = getId(req);
                taskController.delete(id);
                resp.sendRedirect("list");
            }
            case "add", "edit" -> {
                final Task task = "add".equals(action) ? new Task("", "", "") : taskController.get(getId(req));
                req.setAttribute("task", task);
                req.getRequestDispatcher("/WEB-INF/views/add.jsp").forward(req, resp);
            }
            default -> {
                req.setAttribute("tasks", taskController.getAll());
                req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Task task = new Task(
                req.getParameter("name"),
                req.getParameter("section"),
                req.getParameter("description"),
                isWeekly(req));

        if (StringUtils.hasLength(req.getParameter("id"))) {
            task.setId(getId(req));
            taskController.update(task);
        } else {
            taskController.create(task);
        }
        resp.sendRedirect("list");
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private boolean isWeekly(HttpServletRequest request) {
        if (request.getParameter("isWeekly") == null) {
            return false;
        }
        return request.getParameter("isWeekly").equals("true");
    }

    private boolean isMonthly(HttpServletRequest request) {
        if (request.getParameter("isMonthly") == null) {
            return false;
        }
        return request.getParameter("isMonthly").equals("true");
    }
}
