package ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility;

import lombok.Data;

@Data
public class SpecificationResponse {
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
