package org.informatics.dto.company;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.informatics.entity.company.Company;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CompanyTaxDTO extends BaseCompanyDTO {

    private BigDecimal taxesAmount;

    public CompanyTaxDTO(long companyId, String companyName, BigDecimal taxesAmount) {
        super(companyId, companyName);
        this.taxesAmount = taxesAmount;
    }


}

