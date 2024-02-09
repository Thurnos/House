package org.informatics.dto.company;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.informatics.entity.company.Company;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CompanyRevenueDTO {

    private BaseCompanyDTO baseCompanyDTO;

    private BigDecimal revenue;

    public CompanyRevenueDTO(long companyId, String companyName, BigDecimal revenue) {
        this.baseCompanyDTO = new BaseCompanyDTO(companyId, companyName);
        this.revenue = revenue;
    }


}

