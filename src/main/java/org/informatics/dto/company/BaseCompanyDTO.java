package org.informatics.dto.company;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.informatics.dto.BaseEntityDTO;
import org.informatics.entity.company.Company;
import org.informatics.entity.company.Employee;

@Getter
@Setter
@NoArgsConstructor
public class BaseCompanyDTO extends BaseEntityDTO {

    private String companyName;
    private double collectedFees;

    public BaseCompanyDTO(long id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    public BaseCompanyDTO(String companyName) {
        this.companyName = companyName;
    }

    public BaseCompanyDTO(Employee employee) {
    }

    public BaseCompanyDTO(Company company) {
        this.companyName = company.getCompanyName();
        this.collectedFees = company.getCollectedFees();
    }

    public BaseCompanyDTO(String companyName, double collectedFees) {
        this.companyName = companyName;
        this.collectedFees = collectedFees;
    }

}

