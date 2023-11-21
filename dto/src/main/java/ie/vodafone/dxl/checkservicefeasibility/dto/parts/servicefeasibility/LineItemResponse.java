package ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility;

import lombok.Data;

import java.util.List;

@Data
public class LineItemResponse {
    private String reasonCode;
    private String toDate;
    private String cli;
    private String status;
    private String reason;
    private String eirCode;
    private String addressId;
    private String orderId;
    private String valid;
    private List<LineItemCategory> category;
    private SpecificationResponse specification;
    private List<ServiceSpecification> serviceSpecification;
}
