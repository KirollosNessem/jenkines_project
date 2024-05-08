package ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility;

import lombok.Data;

@Data
public class SpecificationRequest {
    private String type;
    private String oldNetworkTechnology;
    private String newNetworkTechnology;
    private String serviceProvider;
    private String transferRequest;
}
