package ie.vodafone.dxl.checkservicefeasibility.dto;

import ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices.LineItem;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices.Specification;
import lombok.Data;

import java.util.List;

@Data
public class QueryAncillaryServicesResponse {
    private String qaOrderId;
    private String name;
    private Specification specification;
    private List<LineItem> lineItem;
}
