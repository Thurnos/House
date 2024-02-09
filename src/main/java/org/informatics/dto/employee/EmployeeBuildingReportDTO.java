package org.informatics.dto.employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeBuildingReportDTO extends BaseEmployeeDTO {

        private String employeeName;
        private String companyName;
        private long buildingCount;

        public EmployeeBuildingReportDTO(String employeeName, String companyName, long buildingCount) {
            this.employeeName = employeeName;
            this.companyName = companyName;
            this.buildingCount = buildingCount;
        }
    public EmployeeBuildingReportDTO(String employeeName, long buildingCount) {
        this.employeeName = employeeName;
        this.buildingCount = buildingCount;
    }
}
