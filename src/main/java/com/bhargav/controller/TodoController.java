package com.bhargav.controller;

import com.bhargav.dao.TodoDao;
import com.bhargav.dao.UserDao;
import com.bhargav.model.Todo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TodoController extends HttpServlet {
    private TodoDao todoDao;
    private UserDao userDao;

    public TodoController() {
        todoDao = new TodoDao();
        userDao =new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            todoDao.deleteTodo(Integer.parseInt(id));
        }
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("todo.jsp");
        int userId = Integer.parseInt(req.getSession().getAttribute("id").toString());

        String item = req.getParameter("todo");

        if (item != null && !item.trim().isEmpty()) {
            todoDao.addTodo(userId,item);
        }
        List<Todo> todos = todoDao.selectAllTodos(userId);
        req.setAttribute("todos", todos);
        dispatcher.forward(req, resp);

    }
}
