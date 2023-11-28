package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.Data;

@Data
public class EligibilityDetailsServiceSpecification {
    private String description;
    private EligibilityDetailsSpecification specification;

}
