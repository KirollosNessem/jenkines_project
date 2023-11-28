package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.Data;

import java.util.List;

@Data
public class EligibilityDetails {
    private List<EligibilityDetailsServiceSpecification> serviceSpecifications;
}
