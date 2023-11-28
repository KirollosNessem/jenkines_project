package ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices;

import lombok.Data;

@Data
public class ServiceSpecification {
    private String type;
    private LineItemSubSpecification specification;
}
