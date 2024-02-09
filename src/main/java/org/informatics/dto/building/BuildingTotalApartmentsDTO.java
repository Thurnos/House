package org.informatics.dto.building;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.informatics.dto.BaseEntityDTO;
import org.informatics.dto.apartment.BaseApartmentDTO;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class BuildingTotalApartmentsDTO extends BaseEntityDTO {

    private Long buildingId;
    private Long totalApartments;

    public BuildingTotalApartmentsDTO(long id){
        this.id =  id;
    }

    private List<BaseApartmentDTO> apartmentDTOList;
}

