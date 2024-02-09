package org.informatics;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.dao.CompanyDAO;
import org.informatics.dao.EmployeeDAO;
import org.informatics.dto.company.BaseCompanyDTO;
import org.informatics.dto.employee.EmployeeBuildingReportDTO;
import org.informatics.entity.building.Apartment;
import org.informatics.entity.resident.Resident;
import org.informatics.entity.resident.pet.Pet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaxPayment {

    // Constants
    private static final double PET_FEE = 3.00;
    private static final double SQUARE_FOOTAGE_RATE = 0.2;
    private static final double ELEVATOR_USAGE_RATE = 2;

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/building_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";




    public double calculatePetFee(int residentId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT COUNT(*) AS count FROM Pet WHERE ResidentID = ? AND PetType = 'dog'";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, residentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int dogCount = resultSet.getInt("count");
                        return dogCount * PET_FEE;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }




    public double calculateSquareFootageFee(int apartmentId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT SquareFootage FROM Apartment WHERE ApartmentID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, apartmentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double squareFootage = resultSet.getDouble("SquareFootage");
                        return squareFootage * SQUARE_FOOTAGE_RATE;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public double calculateElevatorUsageFee(int apartmentId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT COUNT(*) AS count FROM Resident WHERE ApartmentID = ? AND Age > 7 AND UsesElevator = true";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, apartmentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int residentCount = resultSet.getInt("count");
                        return residentCount * ELEVATOR_USAGE_RATE;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    static void updateLastPaymentDate(int apartmentId) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/house_manager", "root", "root")) {
            String query = "UPDATE Apartment SET LastPaymentDate = ? WHERE ApartmentID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDate(1, new java.sql.Date(new Date().getTime()));
                preparedStatement.setInt(2, apartmentId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void registerPaidFee(int apartmentId, double amountPaid) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);

            String insertQuery = "INSERT INTO paidfee (OwnerID, PaymentDate, AmountPaid) " +
                    "VALUES ((SELECT OwnerID FROM Apartment WHERE ApartmentID = ?), CURDATE(), ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, apartmentId);
                insertStatement.setDouble(2, amountPaid);
                insertStatement.executeUpdate();
            }

            String updateQuery = "UPDATE Apartment SET LastPaymentDate = CURDATE() WHERE ApartmentID = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setInt(1, apartmentId);
                updateStatement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public double calculateTotalTax(int apartmentId) {
        int unpaidMonths = calculateUnpaidMonths(apartmentId);
        if (unpaidMonths == 0) {
            System.out.println("Tax is already paid for the current month.");
            return 0; // If tax is already paid, return 0
        }

        double petFee = calculatePetFee(apartmentId);
        double squareFootageFee = calculateSquareFootageFee(apartmentId);
        double elevatorUsageFee = calculateElevatorUsageFee(apartmentId);

        double totalTax = (petFee + squareFootageFee + elevatorUsageFee) * unpaidMonths;

        // Print the details
        System.out.println("Pet Fee: " + petFee);
        System.out.println("Square Footage Fee: " + squareFootageFee);
        System.out.println("Elevator Usage Fee: " + elevatorUsageFee);
        System.out.println("Total Tax for Apartment " + apartmentId + " (Unpaid Months: " + unpaidMonths + "): " + totalTax);

        registerPaidFee(apartmentId, totalTax);
        writePaymentToFile(apartmentId, totalTax);

        return totalTax;
    }

    public static void getServicedEmployeeBuildings(int companyId){
        EmployeeDAO employeeDAO = new EmployeeDAO();

        List<EmployeeBuildingReportDTO> report = employeeDAO.getBuildingServiceReportByCompanyJDBC(companyId);

        for (EmployeeBuildingReportDTO dto : report) {
            System.out.println("Employee Name: " + dto.getEmployeeName());
            System.out.println("Number of Serviced Buildings: " + dto.getBuildingCount());
            System.out.println("-------------------------------------");
        }
    }

    public static void getCompaniesSortedByRevenue(){
        CompanyDAO companyDAO = new CompanyDAO();

        List<BaseCompanyDTO> companiesSortedByRevenue = companyDAO.getCompaniesSortedByRevenueNative();

        System.out.println("Companies Sorted by Revenue (Native Query):");
        for (BaseCompanyDTO companyDTO : companiesSortedByRevenue) {
            System.out.println("Company Name: " + companyDTO.getCompanyName());
            System.out.println("Collected Fees: " + companyDTO.getCollectedFees());
            System.out.println();
        }
    }

    public int calculateUnpaidMonths(int apartmentId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT TIMESTAMPDIFF(MONTH, LastPaymentDate, CURDATE()) AS UnpaidMonths " +
                    "FROM Apartment " +
                    "WHERE ApartmentID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, apartmentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("UnpaidMonths");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getBuildingAddressForApartment(int apartmentId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT b.Address " +
                    "FROM Apartment a " +
                    "JOIN Building b ON a.BuildingID = b.BuildingID " +
                    "WHERE a.ApartmentID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, apartmentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("Address");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void printBuildingAddressForApartment(int apartmentId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT b.Address " +
                    "FROM Apartment a " +
                    "JOIN Building b ON a.BuildingID = b.BuildingID " +
                    "WHERE a.ApartmentID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, apartmentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String address = resultSet.getString("Address");
                        System.out.println("Building Address: " + address);
                    } else {
                        System.out.println("Address not found for Apartment ID: " + apartmentId);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public String getEmployeeNameForApartment(int apartmentId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT e.EmployeeName " +
                    "FROM Apartment a " +
                    "JOIN Building b ON a.BuildingID = b.BuildingID " +
                    "JOIN Employee e ON b.EmployeeID = e.EmployeeID " +
                    "WHERE a.ApartmentID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, apartmentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("EmployeeName");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getCompanyNameForApartment(int apartmentId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT c.CompanyName " +
                    "FROM Building b " +
                    "JOIN Employee e ON b.EmployeeID = e.EmployeeID " +
                    "JOIN Company c ON e.CompanyID = c.CompanyID " +
                    "WHERE b.BuildingID = (SELECT BuildingID FROM Apartment WHERE ApartmentID = ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, apartmentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("CompanyName");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String buildPaymentInfo(int apartmentId, double amountPaid) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String company = getCompanyNameForApartment(apartmentId);
        String employee = getEmployeeNameForApartment(apartmentId);
        String building = getBuildingAddressForApartment(apartmentId);

        return String.format(
                "Company: %s, Employee: %s, Building: %s, ApartmentID: %d, AmountPaid: %.2f, PaymentDate: %s",
                company, employee, building, apartmentId, amountPaid, timeStamp);
    }

    public void writePaymentToFile(int apartmentId, double amountPaid) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("paid-.txt", true))) {
            String paymentInfo = buildPaymentInfo(apartmentId, amountPaid);
            writer.write(paymentInfo);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

