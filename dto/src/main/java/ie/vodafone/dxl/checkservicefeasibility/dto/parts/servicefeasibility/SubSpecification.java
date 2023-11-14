package ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility;

import lombok.Data;

import java.util.List;

@Data
public class SubSpecification {
    private String asCode;
    private String teleNo;
    private List<SubSpecificationCategory> category;
    private String service;
    private String ontType;
    private String installationType;
    private String tag;
}
