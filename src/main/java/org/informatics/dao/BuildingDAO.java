package org.informatics.dao;

import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.dto.apartment.ApartmentDetailDTO;
import org.informatics.dto.building.BuildingTotalApartmentsDTO;
import org.informatics.entity.building.Apartment;
import org.informatics.entity.building.Building;
import org.informatics.entity.resident.Resident;

import java.sql.*;
import java.util.*;

public class BuildingDAO {

    static final String url = "jdbc:mysql://localhost:3306/building_management";
    static final String user = "root";
    static final String password = "root";
    private static final String CREATE_BUILDING_SQL =
            "INSERT INTO building (Address, TotalApartments, Floors, BuiltUpArea, CommonAreas) VALUES (?, ?, ?, ?, ?)";


    public static void createBuildingWithDetails(String address, int totalApartments, int floors, double builtUpArea, double commonAreas) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Building building = new Building();
            building.setAddress(address);
            building.setTotalApartments(totalApartments);
            building.setFloors(floors);
            building.setBuiltUpArea(builtUpArea);
            building.setCommonAreas(commonAreas);

            session.save(building);

            transaction.commit();
            System.out.println("Building added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to add the building.");
        }
    }


    private static final String UPDATE_BUILDING_SQL =
            "UPDATE building SET Address = ?, TotalApartments = ?, Floors = ?, " +
                    "BuiltUpArea = ?, CommonAreas = ? WHERE BuildingID = ?";

    public static void addBuilding(Building building) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        final String INSERT_BUILDING_SQL = "INSERT INTO building (Address, TotalApartments, Floors, BuiltUpArea, CommonAreas) VALUES (?, ?, ?, ?, ?)";

        try {
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(INSERT_BUILDING_SQL);
            preparedStatement.setString(1, building.getAddress());
            preparedStatement.setInt(2, building.getTotalApartments());
            preparedStatement.setInt(3, building.getFloors());
            preparedStatement.setDouble(4, building.getBuiltUpArea());
            preparedStatement.setDouble(5, building.getCommonAreas());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Building added successfully!");
            } else {
                System.out.println("Building addition failed.");
            }

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Transaction rolled back due to an error.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void updateBuilding(Building building) {


        if (validateBuilding(building)) {
            try (Connection connection = DriverManager.getConnection(url, user, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BUILDING_SQL)) {

                preparedStatement.setString(1, building.getAddress());
                preparedStatement.setInt(2, building.getTotalApartments());
                preparedStatement.setInt(3, building.getFloors());
                preparedStatement.setDouble(4, building.getBuiltUpArea());
                preparedStatement.setDouble(5, building.getCommonAreas());

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Building updated successfully!");
                } else {
                    System.out.println("Failed to update the building.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid Building data. Update aborted.");
        }
    }


    private static boolean validateBuilding(Building building) {
        if (building.getAddress() == null || building.getAddress().isEmpty()) {
            System.out.println("Address cannot be empty.");
            return false;
        }

        return true;
    }

    private static final String DELETE_BUILDING_SQL =
            "DELETE FROM building WHERE BuildingID = ?";

    public static void deleteBuilding(int buildingId) {
        if (buildingId <= 0) {
            System.out.println("Invalid building ID provided for deletion.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BUILDING_SQL)) {

            preparedStatement.setInt(1, buildingId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Building deleted successfully!");
            } else {
                System.out.println("Building not found with ID: " + buildingId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<Building> getAllBuildings() {
        List<Building> buildings = new ArrayList<>();

        String sql = "SELECT * FROM building";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Building building = new Building();
                building.setAddress(resultSet.getString("Address"));
                building.setTotalApartments(resultSet.getInt("TotalApartments"));
                building.setFloors(resultSet.getInt("Floors"));
                building.setBuiltUpArea(resultSet.getDouble("BuiltUpArea"));
                building.setCommonAreas(resultSet.getDouble("CommonAreas"));

                buildings.add(building);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return buildings;
    }

    public static Building getBuildingById(int buildingId) {
        String sql = "SELECT * FROM building WHERE BuildingID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, buildingId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Building building = new Building();
                    building.setId(resultSet.getLong("BuildingID"));
                    building.setAddress(resultSet.getString("Address"));
                    building.setTotalApartments(resultSet.getInt("TotalApartments"));
                    building.setFloors(resultSet.getInt("Floors"));
                    building.setBuiltUpArea(resultSet.getDouble("BuiltUpArea"));
                    building.setCommonAreas(resultSet.getDouble("CommonAreas"));

                    return building;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public List<BuildingTotalApartmentsDTO> getTotalApartmentsInEachBuilding() {
        Session session = SessionFactoryUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<BuildingTotalApartmentsDTO> cq = cb.createQuery(BuildingTotalApartmentsDTO.class);
        Root<Apartment> root = cq.from(Apartment.class);

        cq.select(cb.construct(
                BuildingTotalApartmentsDTO.class,
                root.get("building").get("id"),
                cb.count(root)
        )).groupBy(root.get("building").get("id"));

        List<BuildingTotalApartmentsDTO> results = session.createQuery(cq).getResultList();
        session.close();
        return results;
    }
    public List<ApartmentDetailDTO> getApartmentDetailsForEachBuilding() {
        Session session = SessionFactoryUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ApartmentDetailDTO> cq = cb.createQuery(ApartmentDetailDTO.class);
        Root<Apartment> apartmentRoot = cq.from(Apartment.class);
        Join<Apartment, Resident> residentJoin = apartmentRoot.join("resident", JoinType.LEFT);

        cq.select(cb.construct(
                ApartmentDetailDTO.class,
                apartmentRoot.get("building").get("id"),
                apartmentRoot.get("id"),
                residentJoin.get("name")
        )).orderBy(cb.asc(apartmentRoot.get("building").get("id")), cb.asc(apartmentRoot.get("id")));

        List<ApartmentDetailDTO> results = session.createQuery(cq).getResultList();
        session.close();
        return results;
    }

    public Map<Integer, List<String>> getApartmentDetailsForEachBuildingJDBC() {
        String sql = "SELECT a.BuildingID, a.ApartmentID, r.ResidentName " +
                "FROM Apartment a " +
                "JOIN Resident r ON a.ApartmentID = r.ApartmentID " +
                "ORDER BY a.BuildingID, a.ApartmentID";
        Map<Integer, List<String>> apartmentDetailsPerBuilding = new HashMap<>();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/building_management", "root", "root");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int buildingId = resultSet.getInt("BuildingID");
                int apartmentId = resultSet.getInt("ApartmentID"); // Now unambiguous
                String residentName = resultSet.getString("ResidentName");

                String details = "Apartment ID: " + apartmentId + ", Resident: " + residentName;
                apartmentDetailsPerBuilding.computeIfAbsent(buildingId, k -> new ArrayList<>()).add(details);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return apartmentDetailsPerBuilding;
    }

    public Map<Integer, Integer> getTotalApartmentsInEachBuildingJDBC() {
        String sql = "SELECT BuildingID, COUNT(*) AS TotalApartments FROM Apartment GROUP BY BuildingID";
        Map<Integer, Integer> totalApartmentsPerBuilding = new HashMap<>();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/building_management", "root", "root");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int buildingId = resultSet.getInt("BuildingID");
                int totalApartments = resultSet.getInt("TotalApartments");
                totalApartmentsPerBuilding.put(buildingId, totalApartments);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalApartmentsPerBuilding;
    }

}