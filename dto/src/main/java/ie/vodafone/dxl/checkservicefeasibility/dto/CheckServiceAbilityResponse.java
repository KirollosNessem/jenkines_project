package ie.vodafone.dxl.checkservicefeasibility.dto;

import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.Failure;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.LineItemResponse;
import lombok.Data;

import java.util.List;

@Data
public class CheckServiceAbilityResponse {
    private List<LineItemResponse> lineItem;
    private List<Failure> failure;
    //TODO should be in lineItem $.buildingDetails
    private String buildingDetails;
    private String orderId;
}
