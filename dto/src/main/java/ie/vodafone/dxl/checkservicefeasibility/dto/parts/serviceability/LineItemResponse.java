package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineItemResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private LineItemSpecification specification;
    private List<LineItemServiceSpecification> serviceSpecification;
    private String failedStatus;
    private String status;
    private BuildingDetails buildingDetails;
    private EligibilityDetails eligibilityDetails;
}
