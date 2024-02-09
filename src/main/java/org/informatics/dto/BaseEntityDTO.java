package org.informatics.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public abstract class BaseEntityDTO {
    @EqualsAndHashCode.Include
    protected long id;
}
