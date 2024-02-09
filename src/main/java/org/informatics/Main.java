package org.informatics;

import org.hibernate.cfg.Configuration;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.dao.*;
import org.informatics.dto.apartment.ApartmentDetailDTO;
import org.informatics.dto.building.BaseBuildingDTO;
import org.informatics.dto.building.BuildingTotalApartmentsDTO;
import org.informatics.dto.company.BaseCompanyDTO;
import org.informatics.dto.employee.EmployeeBuildingCountDTO;
import org.informatics.dto.employee.EmployeeBuildingReportDTO;
import org.informatics.dto.resident.BaseResidentDTO;
import org.informatics.entity.building.Apartment;
import org.informatics.entity.building.Building;
import org.informatics.entity.building.Owner;
import org.informatics.entity.company.Company;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.informatics.TaxPayment;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.company.Company;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Map;

import static org.informatics.ExecutionExamples.*;

import org.informatics.TaxPayment;

import static org.informatics.TaxPayment.*;
import static org.informatics.dao.BuildingDAO.*;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.informatics.entity.company.Employee;
import org.informatics.entity.resident.Resident;


public class Main {
    public static void main(String[] args) {

        //9a ServicedBuildingsByEmployee
        //
        // getServicedEmployeeBuildings(1);

        // 9c  GetResidentByBuildingId
        //
         //getresidentsbybuilding(1);

        // 9b GenerateBuildingReport -> // Get total apartments in each building
        //                              // Get detailed list of apartments in each building
        // generateBuildingReport();

        //9e TotalFeesPaidByBuilding -> How much taxes  each building has paid in total
//        PaymentReportDAO paymentReportDAO = new PaymentReportDAO();
//        try {
//            // Call the method and store the result
//            Map<Integer, Double> totalFeesByBuilding = paymentReportDAO.getTotalFeesPaidByBuilding();
//
//            // Display the results
//            System.out.println("Total Fees Paid by Building:");
//            for (Map.Entry<Integer, Double> entry : totalFeesByBuilding.entrySet()) {
//                System.out.println("Building ID: " + entry.getKey() + ", Total Fees: " + entry.getValue());
//            }
//        } catch (Exception e) {
//            System.err.println("An error occurred: " + e.getMessage());
//            e.printStackTrace();
//        }


//        8a  SortingCompaniesByRevenue
//
       // getCompaniesSortedByRevenue();

//        8b  SortingEmployeesByCollectedTax
//
   //     getEmployeesSortedByCollectedTax(1);

//        8c SortingResidentsByNameAndAge
//
      //  getResidentsSortedByNameAndAge();


        //7 Paying Taxes for Apartment with ID
        // processPayment(2);


        //DISPLAY___
        //1.AllBuildings
        //ExecutionExamples.displayAllBuildings();


        //--1.Creating , editing and deleting companies--

//        Company company1 = new Company("Housing Services-1",1500.0);
//        CompanyDAO.addCompany(company1);
//        company1.setCollectedFees(1776);
//        CompanyDAO.updateCompany(company1);
//    //    CompanyDAO.deleteCompany(9);


        //--2.Creating , editing and deleting buildings --

//        Building building1 = new Building("Sessame str.",7,28,8700,349);
//        BuildingDAO.addBuilding(building1);
//        building1.setFloors(29);
//        BuildingDAO.updateBuilding(building1);
//        BuildingDAO.deleteBuilding(15);

        // --3.Creating , editing and residents and owners from apartments --
        //BigDecimal squareFootDecimal = BigDecimal.valueOf(84.35);
       // Apartment apartment1 = new Apartment(1,1,"A110B",squareFootDecimal,true, Date.valueOf("2024-01-01"));
        //Owner owner1 = new Owner(32,"Apt006");


//        Resident resident1 = new Resident(29,11,"Sophie Anderson",25,true);
//        Resident resident2 = new Resident(28,12,"May Jackson",35,false);
//        ResidentDAO.addResident(resident1);
//        ResidentDAO.addResident(resident2);
        //OwnerDAO.deleteOwner(32);
        // ResidentDAO.deleteResident(29);
        //ResidentDAO.deleteResident(28);



//      --4.Creating , editing and deleting employees from companies --
//        Employee employee = new Employee(4,"Jason K.",2,3);
//        EmployeeDAO.addEmployee(employee);








//        BuildingDAO buildingReportDAO = new BuildingDAO();
//
//        try {
//            // Get total apartments in each building
//            List<BuildingTotalApartmentsDTO> totalApartmentsList = buildingReportDAO.getTotalApartmentsInEachBuilding();
//            System.out.println("Total Apartments in Each Building:");
//            for (BuildingTotalApartmentsDTO dto : totalApartmentsList) {
//                System.out.println("Building ID: " + dto.getBuildingId() + ", Total Apartments: " + dto.getTotalApartments());
//            }
//
//            // Get detailed list of apartments in each building
//            List<ApartmentDetailDTO> apartmentDetailsList = buildingReportDAO.getApartmentDetailsForEachBuilding();
//            System.out.println("\nApartment Details for Each Building:");
//            for (ApartmentDetailDTO dto : apartmentDetailsList) {
//                System.out.println("Building ID: " + dto.getId() + ", Apartment ID: " + dto.getId() + ", Resident: " + dto.getName());
//            }
//
//        } catch (Exception e) {
//            System.err.println("An error occurred: " + e.getMessage());
//            e.printStackTrace();
//        }



//       EmployeeDAO employeeDAO = new EmployeeDAO();
//
//        // Specify the company ID for which you want the report
//        long companyId = 1; // Replace with the actual company ID
//
//        // Call the method and get the result
//        List<EmployeeBuildingReportDTO> report = employeeDAO.getBuildingServiceReportByCompanyJDBC(companyId);
//
//        // Print the results
//        for (EmployeeBuildingReportDTO dto : report) {
//            System.out.println("Employee Name: " + dto.getEmployeeName());
//            System.out.println("Number of Serviced Buildings: " + dto.getBuildingCount());
//            System.out.println("-------------------------------------");
//        }


//        EmployeeDAO employeeDAO = new EmployeeDAO();
//
//        // Fetch employees sorted by the number of buildings they service
//        List<EmployeeBuildingCountDTO> employees = employeeDAO.getEmployeesSortedByBuildingCount();
//
//        System.out.println("Employees and the Number of Buildings They Service:");
//        for (EmployeeBuildingCountDTO dto : employees) {
//            System.out.println("Employee Name: " + dto.getEmployeeName());
//            System.out.println("Number of Serviced Buildings: " + dto.getBuildingCount());
//            System.out.println();
//        }


//        CompanyDAO companyDAO = new CompanyDAO();
//
//        // Fetch companies using the native query method
//        List<BaseCompanyDTO> companiesSortedByRevenue = companyDAO.getCompaniesSortedByRevenueNative();
//
//        System.out.println("Companies Sorted by Revenue (Native Query):");
//        for (BaseCompanyDTO companyDTO : companiesSortedByRevenue) {
//            System.out.println("Company Name: " + companyDTO.getCompanyName());
//            System.out.println("Collected Fees: " + companyDTO.getCollectedFees());
//            System.out.println();
//        }


//        EmployeeDAO employeeDAO = new EmployeeDAO();
//        Map<String, List<BaseBuildingDTO>> buildingsServicedByEmployees = employeeDAO.getBuildingsServicedByEmployeesJDBC();
//
//        buildingsServicedByEmployees.forEach((employeeName, buildings) -> {
//            System.out.println("Employee: " + employeeName);
//            buildings.forEach(buildingDTO -> {
//                System.out.println("Building Address: " + buildingDTO.getAddress());
//                System.out.println("Building ID: " + buildingDTO.getBuildingId());
//                System.out.println();
//            });
//            System.out.println();
//        });




        //  Example of how to use the TaxPayment class
        //WORKS!!

//        TaxPayment taxPayment = new TaxPayment();
//
//        int apartmentId = 3;
//
//        double totalTax = taxPayment.calculateTotalTax(apartmentId);
//
//        if (totalTax > 0) {
//            System.out.println("Processing payment for Apartment " + apartmentId);
//            updateLastPaymentDate(apartmentId);
//            System.out.println("Payment processed successfully.");
//        } else {
//            System.out.println("No payment required.");
//        }




        // Calculate and print the total tax for the apartment


//        Building building = new Building("Adress",33,12,3456,345);
////        BuildingDAO.addBuilding(building);
//        Apartment apartment = new Apartment(1,1,"A101", new BigDecimal(88.7),true);
//
//       Resident resident = new Resident(1,apartment,"Ivan Ivanov",23,true);
//       ResidentDAO.addResident(resident);

//        ResidentDAO.addResident(resident);
//        Company company = new Company("imane",1565);
//        CompanyDAO.addCompany(company);
//          CompanyDAO.deleteCompany(1);





        // CompanyDAO.deleteCompany(1);

//        Building building = BuildingDAO.getBuildingById(1); // Example: Fetch building with ID 1
//        if (building != null) {
//            System.out.println("Building found: " + building.toString());
//        } else {
//            System.out.println("No building found with the given ID.");
//          Building newBuilding = new Building("123 Main St", 10, 5, 200.0, 50.0);
//          BuildingDAO.addBuilding(newBuilding);
//          BuildingDAO.deleteBuilding(11);
//
//        // Add the building to the database
//        BuildingDAO.addBuilding(newBuilding);




//        //OUTPUTS ALL THE BUILDINGS WITH THEIR INFO
//        List<Building> buildings = getAllBuildings();
//        if (buildings.isEmpty()) {
//            System.out.println("No buildings found.");
//        } else {
//            for (Building building : buildings) {
//                System.out.println("Building ID: " + building.getId());
//                System.out.println("Address: " + building.getAddress());
//                System.out.println("TotalApartments: " + building.getTotalApartments());
//                System.out.println("Floors: " + building.getFloors());
//                System.out.println("BuiltUpArea: " + building.getBuiltUpArea());
//                System.out.println("CommonAreas: " + building.getCommonAreas());
////
//            }
//        }
    }
}



