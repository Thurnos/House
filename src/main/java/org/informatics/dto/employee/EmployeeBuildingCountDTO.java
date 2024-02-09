package org.informatics.dto.employee;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.informatics.dto.building.BaseBuildingDTO;
import org.informatics.dto.company.BaseCompanyDTO;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeBuildingCountDTO {
    private String employeeName;
    private long buildingCount;

    public EmployeeBuildingCountDTO(String employeeName, long buildingCount) {
        this.employeeName = employeeName;
        this.buildingCount = buildingCount;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public long getBuildingCount() {
        return buildingCount;
    }

    public void setBuildingCount(long buildingCount) {
        this.buildingCount = buildingCount;
    }

    @Override
    public String toString() {
        return "EmployeeBuildingCountDTO{" +
                "employeeName='" + employeeName + '\'' +
                ", buildingCount=" + buildingCount +
                '}';
    }
}


