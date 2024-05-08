package ie.vodafone.dxl.checkservicefeasibility.dto;

import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.LineItemResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckServiceFeasibilityResponse implements Serializable {
    private static final long serialVersionUID = 1L;


    private String systemsToCall;
    private String pendingOrders;
    private String tosFlag;
    private List<LineItemResponse> lineItem;
    private Boolean indicator;
    private String desc;
    private String eligibilityCheck;
    private String name;
    private String qaOrderId;
    private String leOrderId;
    private String qa;
}
