package org.informatics.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentReportDAO {

    static final String url = "jdbc:mysql://localhost:3306/building_management";
    static final String user = "root";
    static final String password = "root";

    public List<String> getCollectedFeesByCompany() {
        String query = "SELECT c.CompanyID, c.CompanyName, SUM(pf.CollectedFees) AS TotalFees " +
                "FROM paidfee pf " +
                "JOIN company c ON pf.CompanyID = c.CompanyID " +
                "GROUP BY c.CompanyID, c.CompanyName";

        List<String> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String result = "Company ID: " + resultSet.getInt("CompanyID") +
                        ", Name: " + resultSet.getString("CompanyName") +
                        ", Total Collected Fees: " + resultSet.getDouble("TotalFees");
                results.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static Map<Integer, Double> getTotalFeesPaidByBuilding() {
        String query = "SELECT b.BuildingID, SUM(pf.AmountPaid) AS TotalFees " +
                "FROM paidfee pf " +
                "JOIN owner o ON pf.OwnerID = o.OwnerID " +
                "JOIN apartment a ON o.OwnerID = a.OwnerID " +
                "JOIN building b ON a.BuildingID = b.BuildingID " +
                "GROUP BY b.BuildingID";

        Map<Integer, Double> totalFeesByBuilding = new HashMap<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int buildingId = resultSet.getInt("BuildingID");
                double totalFees = resultSet.getDouble("TotalFees");
                totalFeesByBuilding.put(buildingId, totalFees);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalFeesByBuilding;
    }
}

