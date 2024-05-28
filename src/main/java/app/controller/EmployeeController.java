package app.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import app.entity.Employee;
import app.repository.impl.EmployeeRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeController extends HttpServlet {

    private final EmployeeRepository repository = new EmployeeRepository();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/new" -> showNewForm(request, response);
                case "/insert" -> create(request, response);
                case "/delete" -> delete(request, response);
                case "/edit" -> showEditForm(request, response);
                case "/update" -> update(request, response);
                default -> read(request, response);
            }
        } catch (SQLException exception) {
            throw new ServletException(exception);
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String position = request.getParameter("position");
        String phone = request.getParameter("phone");
        Employee newEmployee = new Employee(name, position, phone);
        repository.create(newEmployee);
        response.sendRedirect("list");
    }

    private void read(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Employee> listEmployee = repository.read();
        request.setAttribute("listEmployee", listEmployee);
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("pages/employee_list.jsp");
        dispatcher.forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String position = request.getParameter("position");
        String phone = request.getParameter("phone");
        Employee employee = new Employee(id, name, position, phone);
        repository.update(employee);
        response.sendRedirect("list");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        repository.delete(id);
        response.sendRedirect("list");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("pages/employee_form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Employee existingEmployee = repository.readById(id);
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("pages/employee_form.jsp");
        request.setAttribute("employee", existingEmployee);
        dispatcher.forward(request, response);
    }
}
