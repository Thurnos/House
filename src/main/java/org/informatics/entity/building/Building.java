package org.informatics.entity.building;

import org.informatics.entity.base_entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.informatics.entity.company.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "building")
public class Building extends BaseEntity {

    @NotNull
    @Column(name = "Address", length = 255)
    private String address;

    @Positive
    @Column(name = "TotalApartments")
    private int totalApartments;

    @Positive
    @Column(name = "Floors")
    private int floors;

    @Positive
    @Column(name = "BuiltUpArea")
    private double builtUpArea;

    @PositiveOrZero
    @Column(name = "CommonAreas")
    private double commonAreas;


    @ManyToOne
    @JoinColumn(name = "AssignedEmployeeID", referencedColumnName = "EmployeeID")
    private Employee assignedEmployee;

    @OneToMany(mappedBy = "building")
    private List<Apartment> apartments = new ArrayList<>();



    public Building(String address, int totalApartments, int floors, double builtUpArea, double commonAreas) {
        this.address = address;
        this.totalApartments = totalApartments;
        this.floors = floors;
        this.builtUpArea = builtUpArea;
        this.commonAreas = commonAreas;
    }

    public Building(String sampleAddress) {
        this.address = sampleAddress;
    }


    @OneToMany(mappedBy = "AssignedEmployeeID")
    private Set<Building> buildings;

    public Building(String address, int totalApartments, int floors, double builtUpArea, double commonAreas,Employee AssignedEmployeeID){
        this.address = address;
        this.totalApartments = totalApartments;
        this.floors = floors;
        this.builtUpArea = builtUpArea;
        this.commonAreas = commonAreas;
        this.assignedEmployee = AssignedEmployeeID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalApartments() {
        return totalApartments;
    }

    public void setTotalApartments(int totalApartments) {
        this.totalApartments = totalApartments;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public double getBuiltUpArea() {
        return builtUpArea;
    }

    public void setBuiltUpArea(double builtUpArea) {
        this.builtUpArea = builtUpArea;
    }

    public double getCommonAreas() {
        return commonAreas;
    }

    public void setCommonAreas(double commonAreas) {
        this.commonAreas = commonAreas;
    }

    public Building() {

    }

    public void setId(long buildingID) {
    }

    @Override
    public String toString() {
        return "Building{" +
                "address='" + address + '\'' +
                ", totalApartments=" + totalApartments +
                ", floors=" + floors +
                ", builtUpArea=" + builtUpArea +
                ", commonAreas=" + commonAreas +
                ", apartments=" + apartments +
                "} " + super.toString();
    }
}
