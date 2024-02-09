package org.informatics.dao;

import jakarta.persistence.criteria.*;
import org.hibernate.query.Query;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.dto.building.BaseBuildingDTO;
import org.informatics.dto.company.BaseCompanyDTO;
import org.informatics.dto.company.CompanyRevenueDTO;
import org.informatics.dto.company.CompanyTaxDTO;
import org.informatics.dto.employee.EmployeeBuildingReportDTO;
import org.informatics.entity.building.Building;
import org.informatics.entity.company.Company;
import org.informatics.entity.company.Employee;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyDAO {
    static final String url = "jdbc:mysql://localhost:3306/building_management";
    static final String user = "root";
    static final String password = "root";
    public static void addCompany(Company company) {
        final String INSERT_COMPANY_SQL = "INSERT INTO company (CompanyName, CollectedFees) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMPANY_SQL)) {

            connection.setAutoCommit(false);

            preparedStatement.setString(1, company.getCompanyName());
            preparedStatement.setDouble(2, company.getCollectedFees());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Company added successfully!");
            } else {
                System.out.println("Failed to add the company.");
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateCompany(Company company) {
        final String UPDATE_COMPANY_SQL = "UPDATE company SET CompanyName = ?, CollectedFees = ? WHERE CompanyID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMPANY_SQL)) {

            connection.setAutoCommit(false);

            preparedStatement.setString(1, company.getCompanyName());
            preparedStatement.setDouble(2, company.getCollectedFees());
            preparedStatement.setInt(3, company.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Company updated successfully!");
            } else {

            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCompany(int companyId) {
        final String DELETE_COMPANY_SQL = "DELETE FROM company WHERE CompanyID = ?";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMPANY_SQL)) {

            connection.setAutoCommit(false);

            preparedStatement.setInt(1, companyId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Company deleted successfully!");
            } else {
                System.out.println("No company found with ID: " + companyId);
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Company getCompanyById(long companyId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.get(Company.class, companyId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Company> getCompanies() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT c FROM Company c", Company.class).getResultList();
        }
    }

    public static Company getCompanyWithEmployees(long id) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT c FROM Company c " +
                            "LEFT JOIN FETCH c.employees emp " +
                            "WHERE c.id = :identifier", Company.class)
                    .setParameter("identifier", id)
                    .uniqueResult();
        }
    }

    public static List<CompanyRevenueDTO> getCompaniesRevenueBetweenAsc(BigDecimal lower, BigDecimal upper) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT new org.informatics.dto.company.CompanyRevenueDTO(c.companyName, 0) FROM Company c JOIN c.employees emp " +
                            "GROUP BY c.companyName HAVING 0 BETWEEN :lower AND :upper ORDER BY 0 DESC", CompanyRevenueDTO.class)
                    .setParameter("lower", lower)
                    .setParameter("upper", upper)
                    .getResultList();
        }
    }

    public static List<CompanyTaxDTO> getCompaniesWithTaxesForPay() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT new org.informatics.dto.company.CompanyTaxDTO(c.id, c.companyName, 0) FROM Company c JOIN c.employees emp " +
                            "GROUP BY c.id, c.companyName", CompanyTaxDTO.class)
                    .getResultList();
        }
    }

    public static List<CompanyTaxDTO> getCompaniesWithPaidTaxes() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT new org.informatics.dto.company.CompanyTaxDTO(c.id, c.companyName, 0) FROM Company c JOIN c.employees emp " +
                            "GROUP BY c.id, c.companyName", CompanyTaxDTO.class)
                    .getResultList();
        }
    }
    public List<BaseCompanyDTO> getCompaniesSortedByRevenueNative() {
        List<BaseCompanyDTO> result = new ArrayList<>();
        final String sql = "SELECT companyName, collectedFees FROM company ORDER BY collectedFees DESC";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String companyName = resultSet.getString("companyName");
                double collectedFees = resultSet.getDouble("collectedFees");
                result.add(new BaseCompanyDTO(companyName, collectedFees));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public List<BaseCompanyDTO> getCompaniesSortedByName() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Company> cq = cb.createQuery(Company.class);
            Root<Company> root = cq.from(Company.class);
            cq.select(root).orderBy(cb.asc(root.get("companyName")));

            Query<Company> query = session.createQuery(cq);
            List<Company> companies = query.getResultList();

            return companies.stream()
                    .map(BaseCompanyDTO::new)
                    .collect(Collectors.toList());
        }
    }
    public List<EmployeeBuildingReportDTO> getBuildingServiceReportByCompanyCriteria(long companyId) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<EmployeeBuildingReportDTO> cq = cb.createQuery(EmployeeBuildingReportDTO.class);

            Root<Employee> employee = cq.from(Employee.class);
            Join<Employee, Company> company = employee.join("company");
            Join<Employee, Building> building = employee.join("buildings", JoinType.LEFT);

            cq.select(cb.construct(
                            EmployeeBuildingReportDTO.class,
                            employee.get("employeeName"),
                            company.get("companyName"),
                            cb.count(building.get("id"))
                    )).where(cb.equal(company.get("id"), companyId))
                    .groupBy(employee.get("employeeName"), company.get("companyName"), company.get("id"));

            return session.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}


