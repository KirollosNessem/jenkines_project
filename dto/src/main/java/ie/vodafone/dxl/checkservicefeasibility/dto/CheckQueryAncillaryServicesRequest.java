package ie.vodafone.dxl.checkservicefeasibility.dto;

import lombok.Data;

@Data
public class CheckQueryAncillaryServicesRequest {
    private String status;
    private String uan;
}
