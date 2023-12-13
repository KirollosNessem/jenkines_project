package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String description;
    private String eircode;
    private AddressSpecification specification;
}
