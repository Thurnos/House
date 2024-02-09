package org.informatics.entity.building;

import jakarta.persistence.*;
import org.informatics.entity.base_entity.BaseEntity;
import org.informatics.entity.resident.Resident;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "apartment")
public class Apartment extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private int apartmentId;


    @Column(name = "OwnerID", insertable = false, updatable = false)
    private int ownerId;

    @Column(name = "ApartmentNumber", length = 50)
    private String apartmentNumber;

    @Column(name = "SquareFootage", precision = 10, scale = 2)
    private BigDecimal squareFootage;

    @Column(name = "HasPet")
    private boolean hasPet;

    @Column(name = "LastPaymentDate")
    @Temporal(TemporalType.DATE)
    private Date lastPaymentDate;
    @ManyToOne
    @JoinColumn(name = "BuildingID")
    private Building building;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OwnerID", referencedColumnName = "OwnerID")
    private Owner owner;



    @OneToMany(mappedBy = "apartment", fetch = FetchType.LAZY)
    private List<Resident> residents;


    public Apartment() {

    }

    public Apartment(int buildingId, int ownerId, String apartmentNumber, BigDecimal squareFootage, boolean hasPet) {
        this.ownerId = ownerId;
        this.apartmentNumber = apartmentNumber;
        this.squareFootage = squareFootage;
        this.hasPet = hasPet;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }


    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public BigDecimal getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(BigDecimal squareFootage) {
        this.squareFootage = squareFootage;
    }


    public boolean isHasPet() {
        return hasPet;
    }

    public void setHasPet(boolean hasPet) {
        this.hasPet = hasPet;
    }

    public Date getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(Date lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public Building getBuilding() {
        return building;
    }


    public void setBuilding(Building building) {
        this.building = building;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

    public int getBuildingId() {
        return (building != null) ? building.getId() : -1;
    }

    public Apartment(int apartmentId, int ownerId, String apartmentNumber, BigDecimal squareFootage, boolean hasPet, Date lastPaymentDate) {
        this.apartmentId = apartmentId;
        this.ownerId = ownerId;
        this.apartmentNumber = apartmentNumber;
        this.squareFootage = squareFootage;
        this.hasPet = hasPet;
        this.lastPaymentDate = lastPaymentDate;
    }

    public void setBuildingID(int buildingID) {
        if (this.building == null) {
            this.building = new Building();
        }
        this.building.setId(buildingID);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "apartmentId=" + apartmentId +
                ", ownerId=" + ownerId +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                ", squareFootage=" + squareFootage +
                ", hasPet=" + hasPet +
                ", lastPaymentDate=" + lastPaymentDate +
                ", building=" + building +
                ", owner=" + owner +
                ", residents=" + residents +
                "} " + super.toString();
    }
}
