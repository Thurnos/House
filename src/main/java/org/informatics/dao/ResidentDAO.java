package org.informatics.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.dto.resident.BaseResidentDTO;
import org.informatics.entity.resident.Resident;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ResidentDAO {
    static final String url = "jdbc:mysql://localhost:3306/building_management";
    static final String user = "root";
    static final String password = "root";

    public static void addResident(Resident resident) {
        final String INSERT_RESIDENT_SQL = "INSERT INTO resident (ApartmentID, ResidentName, Age, UsesElevator) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RESIDENT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, resident.getApartmentId());
            preparedStatement.setString(2, resident.getResidentName());
            preparedStatement.setInt(3, resident.getAge());
            preparedStatement.setBoolean(4, resident.isUsesElevator());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Resident added successfully!");
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        resident.setResidentId(generatedKeys.getInt(1));
                    }
                }
            } else {
                System.out.println("Failed to add the resident.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateResident(Resident resident) {
        final String UPDATE_RESIDENT_SQL = "UPDATE resident SET ApartmentID = ?, ResidentName = ?, Age = ?, UsesElevator = ? WHERE ResidentID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RESIDENT_SQL)) {

            preparedStatement.setInt(1, resident.getApartmentId());
            preparedStatement.setString(2, resident.getResidentName());
            preparedStatement.setInt(3, resident.getAge());
            preparedStatement.setBoolean(4, resident.isUsesElevator());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Resident updated successfully!");
            } else {
                System.out.println("No resident found with ID: ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteResident(int residentId) {
        final String DELETE_RESIDENT_SQL = "DELETE FROM resident WHERE ResidentID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESIDENT_SQL)) {

            preparedStatement.setInt(1, residentId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Resident deleted successfully!");
            } else {
                System.out.println("No resident found with ID: " + residentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<BaseResidentDTO> getResidentsSortedByName() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Resident> cq = cb.createQuery(Resident.class);
            Root<Resident> root = cq.from(Resident.class);
            cq.select(root).orderBy(cb.asc(root.get("residentName")));

            Query<Resident> query = session.createQuery(cq);
            List<Resident> residents = query.getResultList();

            return residents.stream()
                    .map(BaseResidentDTO::new)
                    .collect(Collectors.toList());
        }
    }
    public long getTotalResidentCount() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Resident> root = cq.from(Resident.class);
            cq.select(cb.count(root));
            Query<Long> query = session.createQuery(cq);
            return query.getSingleResult();
        }
    }

    public List<BaseResidentDTO> getAllResidents() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Resident> cq = cb.createQuery(Resident.class);
            Root<Resident> root = cq.from(Resident.class);
            cq.select(root);
            Query<Resident> query = session.createQuery(cq);
            List<Resident> residents = query.getResultList();
            return residents.stream().map(BaseResidentDTO::new).collect(Collectors.toList());
        }
    }

    public static long getTotalResidentCountJDBC() {
        final String COUNT_SQL = "SELECT COUNT(*) FROM resident";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<BaseResidentDTO> getAllResidentsJDBC() {
        final String SELECT_ALL_SQL = "SELECT * FROM resident";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BaseResidentDTO> residents = new ArrayList<>();
            while (resultSet.next()) {
                Resident resident = new Resident(
                        resultSet.getInt("ResidentID"),
                        resultSet.getInt("ApartmentID"),
                        resultSet.getString("ResidentName"),
                        resultSet.getInt("Age"),
                        resultSet.getBoolean("UsesElevator")
                );
                residents.add(new BaseResidentDTO(resident));
            }
            return residents;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    public static List<Integer> getApartmentsInBuilding(int buildingId) {
        List<Integer> apartments = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/building_management", "root", "root")) {
            String query = "SELECT ApartmentID FROM apartment WHERE BuildingID = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, buildingId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        apartments.add(resultSet.getInt("ApartmentID"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return apartments;
    }

    public List<String> getResidentsSortedByNameAndAgeJDBC() {
        String sql = "SELECT ResidentName, Age FROM Resident ORDER BY ResidentName ASC, Age ASC";
        List<String> residents = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String name = resultSet.getString("ResidentName");
                int age = resultSet.getInt("Age");
                residents.add("Name: " + name + ", Age: " + age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return residents;
    }
    public List<String> getResidentsSortedByNameAndAgeCriteria() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Resident> cq = cb.createQuery(Resident.class);
            Root<Resident> root = cq.from(Resident.class);

            cq.select(root).orderBy(cb.asc(root.get("residentName")), cb.asc(root.get("age")));

            Query<Resident> query = session.createQuery(cq);
            List<Resident> resultList = query.getResultList();

            return resultList.stream()
                    .map(resident -> "Name: " + resident.getResidentName() + ", Age: " + resident.getAge())
                    .collect(Collectors.toList());
        }
    }

}

