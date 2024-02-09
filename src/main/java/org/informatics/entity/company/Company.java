package org.informatics.entity.company;

import jakarta.persistence.*;
import org.informatics.entity.base_entity.BaseEntity;

import java.util.List;

@Entity
@Table(name = "company")
public class Company extends BaseEntity {


    @Column(name = "CompanyName", length = 255)
    private String companyName;

    @Column(name = "CollectedFees")
    private double collectedFees;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employees;

    public Company() {
    }

    public Company(String companyName, double collectedFees) {
        this.companyName = companyName;
        this.collectedFees = collectedFees;
    }



    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        if (companyName != null && !companyName.trim().isEmpty()) {
            this.companyName = companyName;
        } else {
            throw new IllegalArgumentException("Invalid companyName. Cannot be null or empty.");
        }
    }

    public double getCollectedFees() {
        return collectedFees;
    }

    public void setCollectedFees(double collectedFees) {
        if (collectedFees >= 0) {
            this.collectedFees = collectedFees;
        } else {
            throw new IllegalArgumentException("Invalid collectedFees. Must be a non-negative value.");
        }
    }


    @Override
    public String toString() {
        return "Company{" +
                "id=" + getId() +
                ", companyName='" + companyName + '\'' +
                ", collectedFees=" + collectedFees +
                '}';
    }


}

