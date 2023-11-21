package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.Data;

import java.util.List;

@Data
public class LineItemResponse {
    LineItemSpecification specification;
    List<LineItemServiceSpecification> serviceSpecification;
    private String failedStatus;
    private String status;
    private BuildingDetails buildingDetails;
    private EligibilityDetails eligibilityDetails;
}
