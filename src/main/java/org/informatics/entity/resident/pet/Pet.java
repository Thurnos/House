package org.informatics.entity.resident.pet;
import jakarta.persistence.*;
import org.informatics.entity.base_entity.BaseEntity;
import org.informatics.entity.building.Apartment;
import org.informatics.entity.resident.Resident;

@Entity
@Table(name = "pet")
public class Pet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PetID")
    private int petId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ResidentID", referencedColumnName = "ResidentID", insertable = false, updatable = false)
    private Resident resident;

    @Enumerated(EnumType.STRING)
    @Column(name = "PetType", length = 10)
    private PetType petType;

    @Column(name = "AdditionalFee")
    private boolean additionalFee;

    @Column(name = "PetName")
    private String petName;


    public Pet() {
    }

    public Pet(int petId, Resident resident, PetType petType, boolean additionalFee, String petName) {
        setPetId(petId);
        setResident(resident);
        setPetType(petType);
        setAdditionalFee(additionalFee);
        setPetName(petName);
    }


    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        if (petId >= 0) {
            this.petId = petId;
        } else {
            throw new IllegalArgumentException("Invalid petId. Must be a non-negative value.");
        }
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public boolean isAdditionalFee() {
        return additionalFee;
    }

    public void setAdditionalFee(boolean additionalFee) {
        this.additionalFee = additionalFee;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "petId=" + petId +
                ", resident=" + resident +
                ", petType=" + petType +
                ", additionalFee=" + additionalFee +
                ", petName='" + petName + '\'' +
                '}';
    }
}
