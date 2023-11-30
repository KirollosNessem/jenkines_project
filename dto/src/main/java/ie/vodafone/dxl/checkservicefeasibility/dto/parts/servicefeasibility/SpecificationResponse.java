package ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean portAvailableFlag;
    private Boolean doesPendingOrderExist;
    private Boolean feasibilityCheck;
    private Boolean openOrderExists;
    private Boolean activeAssetExists;
    private String additionalBuildIndicator;
    private String type;
    private String broadBandService;
    private String insituFlag;
    private String serviceProvider;

}
