package org.informatics.dao;


import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.dto.building.BaseBuildingDTO;
import org.informatics.dto.employee.BaseEmployeeDTO;
import org.informatics.dto.employee.EmployeeBuildingCountDTO;
import org.informatics.dto.employee.EmployeeBuildingReportDTO;
import org.informatics.entity.building.Building;
import org.informatics.entity.company.Employee;
import org.informatics.configuration.SessionFactoryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeDAO {
    static final String url = "jdbc:mysql://localhost:3306/building_management";
    static final String user = "root";
    static final String password = "root";

    // Save Employee
    public void saveEmployee(Employee employee) {
        Transaction transaction = null;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Update Employee
    public static void updateEmployee(Employee employee) {
        Transaction transaction = null;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Delete Employee
    public static void deleteEmployee(int id) {
        Transaction transaction = null;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                session.delete(employee);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Get Employee By ID
    public Employee getEmployeeById(int id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.get(Employee.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all Employees
    public List<Employee> getAllEmployees() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Employee", Employee.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


//    public Map<String, List<BaseBuildingDTO>> getBuildingsServicedByEmployeesJDBC() {
//        String sql = "SELECT e.EmployeeName, b.Address, b.BuildingID, b.Floors FROM employee e " +
//                "JOIN building b ON e.employeeId = b.AssignedEmployeeID";
//        Map<String, List<BaseBuildingDTO>> result = new HashMap<>();
//
//        try (Connection connection = DriverManager.getConnection(url, user, password);
//             PreparedStatement preparedStatement = connection.prepareStatement(sql);
//             ResultSet resultSet = preparedStatement.executeQuery()) {
//
//            while (resultSet.next()) {
//                String employeeName = resultSet.getString("EmployeeName");
//                String buildingAddress = resultSet.getString("Address");
//                int buildingId = resultSet.getInt("BuildingID");
//                int totalFloors = resultSet.getInt("Floors");
//
//                BaseBuildingDTO buildingDTO = new BaseBuildingDTO(buildingAddress, buildingId, totalFloors);
//                result.computeIfAbsent(employeeName, k -> new ArrayList<>()).add(buildingDTO);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
    public List<BaseEmployeeDTO> getEmployeesSortedByName() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> root = cq.from(Employee.class);
            cq.select(root).orderBy(cb.asc(root.get("employeeName")));

            Query<Employee> query = session.createQuery(cq);
            List<Employee> employees = query.getResultList();

            return employees.stream()
                    .map(BaseEmployeeDTO::new)
                    .collect(Collectors.toList());
        }
    }
    public Map<Employee, List<Building>> getBuildingsServicedByEmployees() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<Employee> employeeRoot = cq.from(Employee.class);
            Join<Employee, Building> buildingJoin = employeeRoot.join("buildings", JoinType.LEFT);

            cq.multiselect(employeeRoot, buildingJoin);

            Query<Object[]> query = session.createQuery(cq);
            List<Object[]> results = query.getResultList();

            return results.stream().collect(Collectors.groupingBy(
                    row -> (Employee) row[0],
                    Collectors.mapping(row -> (Building) row[1], Collectors.toList())
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static void addEmployee(Employee employee) {
        final String INSERT_EMPLOYEE_SQL = "INSERT INTO Employee (EmployeeName, CompanyID, ServicedBuildings) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, employee.getEmployeeName());
            preparedStatement.setInt(2, employee.getId());
            preparedStatement.setInt(3, employee.getServicedBuildings());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Employee added successfully!");
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setId(generatedKeys.getInt(1));
                    }
                }
            } else {
                System.out.println("Failed to add the employee.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<EmployeeBuildingReportDTO> getBuildingServiceReportByCompanyJDBC(long companyId) {
        List<EmployeeBuildingReportDTO> result = new ArrayList<>();
        String sql = "SELECT e.EmployeeName, COUNT(b.BuildingID) AS BuildingCount " +
                "FROM Employee e " +
                "LEFT JOIN Building b ON e.EmployeeID = b.AssignedEmployeeID " +
                "WHERE e.CompanyID = ? " +
                "GROUP BY e.EmployeeName";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, companyId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String employeeName = resultSet.getString("EmployeeName");
                    long buildingCount = resultSet.getLong("BuildingCount");
                    result.add(new EmployeeBuildingReportDTO(employeeName, buildingCount));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
