package ie.vodafone.dxl.checkservicefeasibility.dto;

import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.Category;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.LineItemResponse;
import lombok.Data;

import java.util.List;

@Data
public class CheckServiceFeasibilityResponse {
    private List<Category> category;
    private List<LineItemResponse> lineItem;
//    //TODO how to be mapped
//    private List<Failure> failure;
    private Boolean indicator;
    private String eligibilityCheck;
    private String name;
    private String qaOrderId;
    private String leOrderId;
    private String qa;
}
