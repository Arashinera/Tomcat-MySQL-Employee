package app.repository.impl;

import app.database.DBConn;
import app.entity.Employee;
import app.repository.AppRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeRepository implements AppRepository<Employee> {

    private static final Logger LOGGER =
            Logger.getLogger(EmployeeRepository.class.getName());

    public void create(Employee employee) {
        String sql = "INSERT INTO employers (name, position, phone) VALUES (?, ?, ?)";
        try (Connection conn = DBConn.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getPosition());
            ps.setString(3, employee.getPhone());
            ps.executeUpdate();
        } catch (SQLException exception) {
            LOGGER.log(Level.WARNING, exception.getMessage(), exception);
        }
    }

    public List<Employee> read() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employers";
        try (Connection conn = DBConn.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Employee(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("position"),
                                rs.getString("phone"))
                );
            }

            return list;
        } catch (SQLException exception) {
            LOGGER.log(Level.WARNING, exception.getMessage(), exception);

            return Collections.emptyList();
        }
    }

    public Employee readById(Long id) {
        Employee employee = null;
        String sql = "SELECT * FROM employers WHERE id = ?";
        try (Connection conn = DBConn.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                employee = new Employee(id,
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getString("phone")
                );
            }
            ps.executeQuery();
        } catch (SQLException exception) {
            LOGGER.log(Level.WARNING, exception.getMessage(), exception);
        }
        return employee;
    }

    public void update(Employee employee) {
        String sql = "UPDATE employers SET name = ?, position = ?, phone = ? WHERE id = ?";
        try (Connection conn = DBConn.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getPosition());
            ps.setString(3, employee.getPhone());
            ps.setLong(4, employee.getId());
            ps.executeUpdate();
        } catch (SQLException exception) {
            LOGGER.log(Level.WARNING, exception.getMessage(), exception);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM employers where id = ?";
        try (Connection conn = DBConn.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException exception) {
            LOGGER.log(Level.WARNING, exception.getMessage(), exception);
        }
    }
}
