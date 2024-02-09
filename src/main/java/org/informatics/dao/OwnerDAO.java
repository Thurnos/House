package org.informatics.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OwnerDAO {
    static final String url = "jdbc:mysql://localhost:3306/building_management";
    static final String user = "root";
    static final String password = "root";

    public static void deleteOwner(int ownerId) {
        final String DELETE_OWNER_SQL = "DELETE FROM Owner WHERE OwnerID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_OWNER_SQL)) {

            preparedStatement.setInt(1, ownerId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Owner with ID " + ownerId + " was deleted successfully.");
            } else {
                System.out.println("No owner found with ID: " + ownerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
