package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingDetailsServiceSpecification implements Serializable {
    private static final long serialVersionUID = 1L;

    private Survey survey;
    private Address address;
}
