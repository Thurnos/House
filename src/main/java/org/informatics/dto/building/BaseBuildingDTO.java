package org.informatics.dto.building;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.informatics.dto.BaseEntityDTO;
import org.informatics.entity.building.Building;
import org.informatics.entity.company.Company;

@Getter
@Setter
@NoArgsConstructor
public class BaseBuildingDTO extends BaseEntityDTO {
    private int buildingId;
    private String address;
    private int totalApartments;
    private int floors;
    private double builtUpArea;
    private double commonAreas;
    private String assignedEmployeeId;
    private String companyName;
    private double collectedFees;


    public BaseBuildingDTO(Building building) {
        this.buildingId = building.getId();
        this.address = building.getAddress();
        this.totalApartments = building.getTotalApartments();
        this.floors = building.getFloors();
        this.builtUpArea = building.getBuiltUpArea();
        this.commonAreas = building.getCommonAreas();
    }

    public BaseBuildingDTO(int buildingId,String address ,int totalApartments) {
        this.buildingId = buildingId;
        this.address = address;
        this.totalApartments = totalApartments;
    }

    public BaseBuildingDTO(String buildingAddress, int buildingId, int totalFloors) {
       this.address = buildingAddress;
       this.buildingId = buildingId;
       this.floors = totalFloors;
    }
    public BaseBuildingDTO(Company company) {
        this.companyName = company.getCompanyName();
        this.collectedFees = company.getCollectedFees();
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getCollectedFees() {
        return collectedFees;
    }
}
