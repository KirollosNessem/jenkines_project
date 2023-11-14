package ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility;

import lombok.Data;

@Data
public class ServiceSpecification {
    private String asDetails;
    private String active;
    private String available;
    private String service;
    private String ontActive;
    private String reserved;
    private String lineId;
    private String installationType;
    private SubSpecification specification;
}
