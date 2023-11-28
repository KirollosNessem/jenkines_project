package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.Data;

import java.util.List;

@Data
public class Address {
    private String id;
    private String description;
    private List<AddressCategory> category;
    private AddressSpecification specification;
}
