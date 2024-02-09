package org.informatics.dto.apartment;

import org.informatics.dto.BaseEntityDTO;

public class ApartmentDetailDTO extends BaseEntityDTO {
    private Long buildingId;
    private Long apartmentId;
    private String residentName;

    public String getName() {
        return residentName;
    }
}
