package ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility;


import lombok.Data;

import java.util.List;

@Data
public class LineItemRequest {
    private SpecificationRequest specification;
    private String assignedProductId;
    private String status;
    private String type;
    private String orderId;
    private String valid;
}
