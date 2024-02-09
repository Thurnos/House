package org.informatics.entity.building;

import jakarta.persistence.*;
import org.informatics.entity.base_entity.BaseEntity;

import java.util.Objects;

@Entity
@Table(name = "owner")
public class Owner extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private int ownerId;

    @Column(name = "OwnerName", length = 255)
    private String ownerName;

    @Column(name = "ContactInfo", length = 255)
    private String contactInfo;

    @Column(name = "ApartmentNumber")
    private String apartmentNumber;

    public Owner() {
    }

    public Owner(int ownerId, String apartmentNumber) {
        this.ownerId = ownerId;
        this.apartmentNumber = apartmentNumber;
    }

    public Owner(int ownerId, String apartmentNumber, String ownerName, String contactInfo) {
        this.ownerId = ownerId;
        this.apartmentNumber = apartmentNumber;
        this.ownerName = ownerName;
        this.contactInfo = contactInfo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, apartmentNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Owner owner = (Owner) obj;
        return ownerId == owner.ownerId && Objects.equals(apartmentNumber, owner.apartmentNumber);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "ownerId=" + ownerId +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                '}';
    }
}
