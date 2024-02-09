package org.informatics.dto.resident;

import org.informatics.entity.resident.Resident;

public class BaseResidentDTO {


        private int residentId;
        private int apartmentId;
        private String residentName;
        private int age;
        private boolean usesElevator;

        public BaseResidentDTO(Resident resident) {
            this.residentId = resident.getResidentId();
            this.apartmentId = resident.getApartmentId();
            this.residentName = resident.getResidentName();
            this.age = resident.getAge();
            this.usesElevator = resident.isUsesElevator();
        }

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isUsesElevator() {
        return usesElevator;
    }

    public void setUsesElevator(boolean usesElevator) {
        this.usesElevator = usesElevator;
    }


}
