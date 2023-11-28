package ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility;

import lombok.Data;

@Data
public class Category {
    private String systemsToCall;
    private String pendingOrders;
    private String tosFlag;
}
