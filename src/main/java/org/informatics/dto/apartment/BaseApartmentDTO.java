package org.informatics.dto.apartment;

import lombok.Getter;
import lombok.Setter;
import org.informatics.dto.BaseEntityDTO;


@Getter
@Setter
public class BaseApartmentDTO extends BaseEntityDTO {
    private BaseApartmentDTO building;

    public BaseApartmentDTO(long id,long buildingId) {
        this.id = id;
        this.building = new BaseApartmentDTO(id,buildingId);
    }
}

