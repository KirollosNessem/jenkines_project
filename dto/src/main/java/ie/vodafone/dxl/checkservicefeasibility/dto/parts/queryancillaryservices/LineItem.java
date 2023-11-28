package ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices;

import lombok.Data;

import java.util.List;

@Data
public class LineItem {
    private String status;
    private String reason;
    private String cli;
    private LineItemSpecification specification;
    private List<ServiceSpecification> serviceSpecification;
    private List<Category> category;
}
