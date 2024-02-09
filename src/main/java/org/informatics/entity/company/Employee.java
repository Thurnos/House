package org.informatics.entity.company;

import jakarta.persistence.*;
import org.informatics.entity.base_entity.BaseEntity;
import org.informatics.entity.building.Building;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private int employeeId;

    @Column(name = "EmployeeName", length = 255)
    private String employeeName;

    @ManyToOne
    @JoinColumn(name = "CompanyID", referencedColumnName = "id")
    private Company company;
    @Column(name = "AssignedEmployeeID")
    private Integer assignedEmployeeId;

    @OneToMany(mappedBy = "assignedEmployee")
    private Set<Building> buildings;
    @ManyToOne
    @JoinColumn(name = "AssignedEmployeeID", referencedColumnName = "employeeId", insertable = false, updatable = false)
    private Employee employee;

    @Column(name = "ServicedBuildings")
    private int servicedBuildings;

    public Employee() {

    }



    public String getEmployeeName() {
        return this.employeeName;
    }
//    @OneToMany(mappedBy = "employee")
//    private List<Building> buildings;

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", company=" + company +
                ", servicedBuildings=" + servicedBuildings +
                "} " + super.toString();
    }

    public Employee(int employeeId, String employeeName, Company company, int servicedBuildings) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.company = company;
        this.servicedBuildings = servicedBuildings;
    }
    public Employee(int employeeId, String employeeName,int companyId, int servicedBuildings) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.setId(companyId);
        this.servicedBuildings = servicedBuildings;
    }

    public void setServicedBuildings(int servicedBuildings) {
        this.servicedBuildings = servicedBuildings;
    }
    public int getServicedBuildings() {
        return buildings != null ? buildings.size() : 0;
    }

}