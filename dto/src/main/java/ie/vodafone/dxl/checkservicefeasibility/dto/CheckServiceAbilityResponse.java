package ie.vodafone.dxl.checkservicefeasibility.dto;

import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.LineItemResponse;
import lombok.Data;

import java.util.List;

@Data
public class CheckServiceAbilityResponse {
    private List<LineItemResponse> lineItem;
    private String buildingDetails;
    private String orderId;
    private String name;
}
