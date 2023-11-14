package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.Data;

@Data
public class EligibilityDetailsSpecification {
    private String poh;
    private String targetLaunchPhase;
    private String certaintyLevel;
    private String preorderDate;
    private String installationType;
    private Boolean preOrderFlag;
    private Boolean wayLeaveRequired;
    private String connectionStandard;
    private String connectionComplexity;
    private String dropType;
    private String readyForServiceDate;
    private String eligibleProducts;
    private String inHomeServices;
    private String CPE;

}
