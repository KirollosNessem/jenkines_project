package ie.vodafone.dxl.checkservicefeasibility.dto;

import ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices.LineItem;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices.Specification;
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
    private Specification specification;
    private List<LineItem> lineItem;
}
