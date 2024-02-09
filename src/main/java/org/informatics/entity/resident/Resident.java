package org.informatics.entity.resident;

import jakarta.persistence.*;
import org.informatics.entity.base_entity.BaseEntity;
import org.informatics.entity.building.Apartment;

@Entity
@Table(name = "resident")
public class Resident extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ResidentID")
    private int residentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ApartmentID", referencedColumnName = "ApartmentID", insertable = false, updatable = false)
    private Apartment apartment;

    @Column(name = "ResidentName", length = 255)
    private String residentName;

    @Column(name = "Age")
    private int age;

    @Column(name = "UsesElevator")
    private boolean usesElevator;

    public Resident() {
    }

    public Resident(int residentId, String residentName, int age, boolean usesElevator) {
        setResidentId(residentId);
        setResidentName(residentName);
        setAge(age);
        setUsesElevator(usesElevator);
    }

    public Resident(int residentId, Apartment apartmentID, String residentName, int age, boolean usesElevator) {
        setResidentId(residentId);
        setApartment(apartment);
        setResidentName(residentName);
        setAge(age);
        setUsesElevator(usesElevator);
    }

    public Resident(int residentID, int apartmentID, String residentName, int age, boolean usesElevator) {
        setResidentId(residentId);
        setId(apartmentID);
        setResidentName(residentName);
        setAge(age);
        setUsesElevator(usesElevator);
    }


    public int getResidentId() {
        return residentId;
    }

    public void setResidentId(int residentId) {
        if (residentId >= 0) {
            this.residentId = residentId;
        } else {
            throw new IllegalArgumentException("Invalid residentId. Must be a non-negative value.");
        }
    }

    public int getApartmentId() {
        return apartment != null ? apartment.getId() : -1;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        if (residentName != null && !residentName.trim().isEmpty()) {
            this.residentName = residentName;
        } else {
            throw new IllegalArgumentException("ResidentName cannot be null or empty.");
        }
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Invalid age. Must be a non-negative value.");
        }
    }

    public boolean isUsesElevator() {
        return usesElevator;
    }

    public void setUsesElevator(boolean usesElevator) {
        this.usesElevator = usesElevator;
    }

    @Override
    public String toString() {
        return "Resident{" +
                "residentId=" + residentId +
                ", apartment=" + apartment +
                ", residentName='" + residentName + '\'' +
                ", age=" + age +
                ", usesElevator=" + usesElevator +
                '}';
    }
}

