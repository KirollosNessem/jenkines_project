package ie.vodafone.dxl.checkservicefeasibility.dto;

import ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices.LineItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryAncillaryServicesResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String qaOrderId;
    private String name;
    private String pendingOrders;
    private String tosFlag;
    private List<LineItem> lineItem;
}
