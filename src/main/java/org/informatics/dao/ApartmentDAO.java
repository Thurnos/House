package org.informatics.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.Query;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.building.Apartment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Calendar;
import java.util.List;

public class ApartmentDAO {

    static final String url = "jdbc:mysql://localhost:3306/building_management";
    static final String user = "root";
    static final String password = "root";
    public static void addApartment(Apartment apartment) {
        final String INSERT_APARTMENT_SQL = "INSERT INTO apartment (BuildingID, OwnerID, ApartmentNumber, SquareFootage, HasPet, LastPaymentDate) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_APARTMENT_SQL)) {

            preparedStatement.setInt(1, apartment.getBuildingId()); // Assuming getBuildingID() is a method in Apartment
            preparedStatement.setInt(2, apartment.getOwnerId());
            preparedStatement.setString(3, apartment.getApartmentNumber());
            preparedStatement.setBigDecimal(4, apartment.getSquareFootage());
            preparedStatement.setBoolean(5, apartment.isHasPet());
            preparedStatement.setDate(6, new java.sql.Date(apartment.getLastPaymentDate().getTime()));

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Apartment added successfully!");
            } else {
                System.out.println("Failed to add the apartment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isTaxPaid(int apartmentId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
            Root<Apartment> root = criteria.from(Apartment.class);

            criteria.select(builder.array(root.get("lastPaymentDate")))
                    .where(builder.equal(root.get("apartmentId"), apartmentId));

            Query<Object[]> query = session.createQuery(criteria);
            Object[] result = query.getSingleResult();

            if (result[0] != null) {
                Date lastPaymentDate = (Date) result[0];
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(Calendar.getInstance().getTime());
                cal2.setTime(lastPaymentDate);

                return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                        && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void printTaxStatus(int apartmentId) {
        boolean taxPaid = isTaxPaid(apartmentId);
        if (taxPaid) {
            System.out.println("TaxPaid");
        } else {
            System.out.println("TaxWasntPaid");
        }
    }


    public static void updateApartment(Apartment apartment) {
        final String UPDATE_APARTMENT_SQL = "UPDATE apartment SET BuildingID = ?, OwnerID = ?, ApartmentNumber = ?, SquareFootage = ?, HasPet = ?, LastPaymentDate = ? WHERE ApartmentID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_APARTMENT_SQL)) {

            preparedStatement.setInt(1, apartment.getBuildingId());
            preparedStatement.setInt(2, apartment.getOwnerId());
            preparedStatement.setString(3, apartment.getApartmentNumber());
            preparedStatement.setBigDecimal(4, apartment.getSquareFootage());
            preparedStatement.setBoolean(5, apartment.isHasPet());
            preparedStatement.setDate(6, new java.sql.Date(apartment.getLastPaymentDate().getTime()));
            preparedStatement.setInt(7, apartment.getApartmentId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Apartment updated successfully!");
            } else {
                System.out.println("No apartment found with ID: " + apartment.getApartmentId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteApartment(int apartmentId) {
        final String DELETE_APARTMENT_SQL = "DELETE FROM apartment WHERE ApartmentID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_APARTMENT_SQL)) {

            preparedStatement.setInt(1, apartmentId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Apartment deleted successfully!");
            } else {
                System.out.println("No apartment found with ID: " + apartmentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Apartment getApartmentById(int apartmentId) {
        final String SELECT_APARTMENT_BY_ID = "SELECT * FROM apartment WHERE ApartmentID = ?";
        Apartment apartment = null;

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_APARTMENT_BY_ID)) {

            preparedStatement.setInt(1, apartmentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                apartment = new Apartment();
                apartment.setApartmentId(resultSet.getInt("ApartmentID"));
                apartment.setBuildingID(resultSet.getInt("BuildingID"));
                apartment.setOwnerId(resultSet.getInt("OwnerID"));
                apartment.setApartmentNumber(resultSet.getString("ApartmentNumber"));
                apartment.setSquareFootage(resultSet.getBigDecimal("SquareFootage"));
                apartment.setHasPet(resultSet.getBoolean("HasPet"));
                apartment.setLastPaymentDate(resultSet.getDate("LastPaymentDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartment;
    }



    public static List<Apartment> getApartmentsWithInhabitants() {
        List<Apartment> apartments;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            apartments = session.createQuery("SELECT a FROM Apartment a " +
                            "LEFT JOIN FETCH a.residents", Apartment.class)
                    .getResultList();

            transaction.commit();
        }
        return apartments;
    }
}

