package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.Data;

import java.util.List;

@Data
public class LineItemResponse {
    LineItemSpecification specification;
    List<LineItemServiceSpecification> serviceSpecification;
    //TODO change this $.lineItem[*].status.failed
    private String failedStatus;
    private String status;
    private String orderId;
    private BuildingDetails buildingDetails;
    private EligibilityDetails eligibilityDetails;
}
