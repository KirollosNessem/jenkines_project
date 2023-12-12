package ie.vodafone.dxl.checkservicefeasibility.dto;


import ie.vodafone.dxl.checkservicefeasibility.dto.parts.Location;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.LineItemRequest;
import ie.vodafone.dxl.utils.common.CollectionUtils;
import ie.vodafone.dxl.utils.common.StringUtils;
import lombok.Data;

import java.util.List;

@Data
public class CheckServiceFeasibilityRequest {
    private String status;
    private List<LineItemRequest> lineItem;
    private String uan;
    private String productOrderID;
    private String basketId;
    private String orderNumber;
    private Location location;


    public boolean isEmpty() {
        return StringUtils.isBlank(status)
                && CollectionUtils.isEmpty(lineItem)
                && StringUtils.isBlank(uan)
                && StringUtils.isBlank(productOrderID)
                && StringUtils.isBlank(basketId)
                && StringUtils.isBlank(orderNumber)
                && (location == null || location.isEmpty());
    }
}
