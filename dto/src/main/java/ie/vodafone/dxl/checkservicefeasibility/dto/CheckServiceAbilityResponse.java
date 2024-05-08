package ie.vodafone.dxl.checkservicefeasibility.dto;

import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.LineItemResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckServiceAbilityResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<LineItemResponse> lineItem;
    private String buildingDetails;
    private String orderId;
    private String name;
}
