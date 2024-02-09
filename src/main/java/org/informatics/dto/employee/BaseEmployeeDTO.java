package org.informatics.dto.employee;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.informatics.dto.BaseEntityDTO;
import org.informatics.entity.company.Employee;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseEmployeeDTO extends BaseEntityDTO {

    private int employeeId;
    private String employeeName;
    private int companyId;
    private int servicedBuildings;

    public BaseEmployeeDTO(Employee employee) {
        this.employeeId = employee.getId();
        this.employeeName = employee.getEmployeeName();
        this.companyId = employee.getId();
        this.servicedBuildings = employee.getServicedBuildings();
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public int getServicedBuildings() {
        return servicedBuildings;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setServicedBuildings(int servicedBuildings) {
        this.servicedBuildings = servicedBuildings;
    }

    @Override
    public String toString() {
        return "BaseEmployeeDTO{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", companyId=" + companyId +
                ", servicedBuildings=" + servicedBuildings +
                '}';
    }
}




