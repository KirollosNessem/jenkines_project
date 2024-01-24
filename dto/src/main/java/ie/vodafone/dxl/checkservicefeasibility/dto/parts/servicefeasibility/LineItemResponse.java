package ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility;

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

    private String reasonCode;
    private String toDate;
    private String cli;
    private String status;
    private String reason;
    private String eirCode;
    private String addressId;
    private String orderId;
    private String valid;
    private String actionFlag;
    private String eligibilityStatus;
    private String ineligibilityDescription;
    private SpecificationResponse specification;
    private List<ServiceSpecification> serviceSpecification;
}
