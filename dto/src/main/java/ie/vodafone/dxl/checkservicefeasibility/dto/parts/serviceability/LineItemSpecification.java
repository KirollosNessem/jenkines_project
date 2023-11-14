package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.Data;

@Data
public class LineItemSpecification {
    private String eirFithApplicable;
    private String eircodeExists;
    private String prequalificationSpeed;
    private String prequalificationResult;
    private Boolean isBbsaSupported;
    private String serviceProvider;
}
