package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityDetailsSpecification implements Serializable {
    private static final long serialVersionUID = 1L;

    private String poh;
    private String targetLaunchPhase;
    private String certaintyLevel;
    private String preorderDate;
    private String installationType;
    private Boolean preOrderFlag;
    private String wayLeaveRequired;
    private String connectionStandard;
    private String connectionComplexity;
    private String dropType;
    private String readyForServiceDate;
    private List<String> eligibleProducts;
    private List<String> inHomeServices;
    private String cpe;

}
