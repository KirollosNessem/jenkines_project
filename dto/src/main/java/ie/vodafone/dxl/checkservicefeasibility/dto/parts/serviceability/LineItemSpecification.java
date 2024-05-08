package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineItemSpecification implements Serializable {
    private static final long serialVersionUID = 1L;

    private String eirFithApplicable;
    private String eircodeExists;
    private String prequalificationSpeed;
    private String prequalificationResult;
    private Boolean isBbsaSupported;
    private String serviceProvider;
}
