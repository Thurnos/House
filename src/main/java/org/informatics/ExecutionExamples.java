package org.informatics;

import org.informatics.dao.*;
import org.informatics.dto.employee.EmployeeBuildingReportDTO;
import org.informatics.dto.resident.BaseResidentDTO;
import org.informatics.entity.*;
import org.informatics.dao.BuildingDAO;
import org.informatics.entity.building.Building;
import org.informatics.entity.company.Company;
import org.informatics.entity.company.Employee;

import java.util.List;
import java.util.Map;

import static org.informatics.TaxPayment.updateLastPaymentDate;
import static org.informatics.dao.BuildingDAO.getAllBuildings;

public class ExecutionExamples {


    //OUTPUTS ALL THE BUILDINGS WITH THEIR INFO
    public static void displayAllBuildings() {
        List<Building> buildings = getAllBuildings();
        if (buildings.isEmpty()) {
            System.out.println("No buildings found.");
        } else {
            for (Building building : buildings) {
                System.out.println("Building ID: " + building.getId());
                System.out.println("Address: " + building.getAddress());
                System.out.println("TotalApartments: " + building.getTotalApartments());
                System.out.println("Floors: " + building.getFloors());
                System.out.println("BuiltUpArea: " + building.getBuiltUpArea());
                System.out.println("CommonAreas: " + building.getCommonAreas());
            }
        }
    }
    public static void displayCollectedFeesByCompany(int buildingId){
        System.out.println("Collected Fees by Company:");
        PaymentReportDAO paymentReportDAO = new PaymentReportDAO();
        List<String> companyFees = paymentReportDAO.getCollectedFeesByCompany();
        for (String fee : companyFees) {
            System.out.println(fee);
        }
    }



    public static void dmlBuilding(){
        Employee employee = new Employee();
        employee.setId(22);
        Building building1 = new Building("Sessame str.",7,28,8700,349,employee);
        BuildingDAO.addBuilding(building1);
        //        BuildingDAO.deleteBuilding(14);
    }

        public static void dmlCompany(){
            CompanyDAO companyDAO = new CompanyDAO();
            //CompanyDAO.deleteCompany(6);

            Company company1 = new Company("Housing Services", 1500.0);

            try {
                companyDAO.addCompany(company1);;
            } catch (Exception e) {
                System.err.println("Error adding company: " + e.getMessage());
                e.printStackTrace();
            }

            try {
                company1.setCollectedFees(1600.0);
                companyDAO.updateCompany(company1);
                System.out.println("Company updated successfully.");
            } catch (Exception e) {
                System.err.println("Error updating company: " + e.getMessage());
                e.printStackTrace();
            }
        }


        public static void getResidentsSortedByNameAndAge(){
            ResidentDAO residentDAO = new ResidentDAO();
            List<String> residentsSortedJDBC = residentDAO.getResidentsSortedByNameAndAgeJDBC();
            System.out.println("Residents sorted by name and age:");
            residentsSortedJDBC.forEach(System.out::println);
        }

        public static void getEmployeesSortedByCollectedTax(int companyId){
            EmployeeDAO employeeDAO = new EmployeeDAO();

        List<EmployeeBuildingReportDTO> report = employeeDAO.getBuildingServiceReportByCompanyJDBC(companyId);

        for (EmployeeBuildingReportDTO dto : report) {
            System.out.println("Employee Name: " + dto.getEmployeeName());
            System.out.println("Number of Serviced Buildings: " + dto.getBuildingCount());
            System.out.println("-------------------------------------");
            }
        }


        public static void getAllResidents(){
            List<BaseResidentDTO> allResidents = ResidentDAO.getAllResidentsJDBC();
        System.out.println("List of all residents:");
        for (BaseResidentDTO resident : allResidents) {
            System.out.println("Name: " + resident.getResidentName() +
                    ", Age: " + resident.getAge() + ", Uses Elevator: " + resident.isUsesElevator());

    }
        }
     public static void getresidentsbybuilding(int buildingId){
         List<Integer> apartmentsInBuilding = ResidentDAO.getApartmentsInBuilding(buildingId);
         System.out.println("\nApartments in Building:");
         System.out.println(apartmentsInBuilding);
     }

        public static void generateBuildingReport() {
            BuildingDAO buildingReportDAO = new BuildingDAO();

            try {
                Map<Integer, Integer> totalApartmentsMap = buildingReportDAO.getTotalApartmentsInEachBuildingJDBC();
                System.out.println("Total Apartments in Each Building:");
                for (Map.Entry<Integer, Integer> entry : totalApartmentsMap.entrySet()) {
                    System.out.println("Building ID: " + entry.getKey() + ", Total Apartments: " + entry.getValue());
                }

                Map<Integer, List<String>> apartmentDetailsMap = buildingReportDAO.getApartmentDetailsForEachBuildingJDBC();
                System.out.println("\nDetailed Apartment Information for Each Building:");
                for (Map.Entry<Integer, List<String>> entry : apartmentDetailsMap.entrySet()) {
                    System.out.println("Building ID: " + entry.getKey());
                    for (String detail : entry.getValue()) {
                        System.out.println(detail);
                    }
                }

            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
        public static void processPayment(int apartmentId){
            TaxPayment taxPayment = new TaxPayment();
            double totalTax = taxPayment.calculateTotalTax(apartmentId);

            if (totalTax > 0) {
                System.out.println("Processing payment for Apartment " + apartmentId);
                updateLastPaymentDate(apartmentId);
                System.out.println("Payment processed successfully.");
            } else {
                System.out.println("No payment required.");
            }
        }
        public static void dmlBuildings() {
            try {
                Building newBuilding = new Building("New Address", 10, 5, 200.0, 50.0);
                BuildingDAO.createBuildingWithDetails(newBuilding.getAddress(), newBuilding.getTotalApartments(),
                        newBuilding.getFloors(), newBuilding.getBuiltUpArea(), newBuilding.getCommonAreas());
                System.out.println("New Building created.");

                Building buildingToUpdate = BuildingDAO.getBuildingById(1);
                if (buildingToUpdate != null) {
                    buildingToUpdate.setFloors(15);
                    BuildingDAO.updateBuilding(buildingToUpdate);
                    System.out.println("Building updated.");
                } else {
                    System.out.println("Building not found for update.");
                }

                Building buildingToDelete = BuildingDAO.getBuildingById(1); // Replace 2 with the actual ID
                if (buildingToDelete != null) {
                    System.out.println("Building deleted.");
                } else {
                    System.out.println("Building not found for deletion.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

