package ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices;

import lombok.Data;

@Data
public class LineItemSpecification {
    private String broadbandService;
    private String multicastService;
    private String type;
    private String insituFlag;
}
