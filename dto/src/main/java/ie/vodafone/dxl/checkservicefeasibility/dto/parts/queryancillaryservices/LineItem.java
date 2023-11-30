package ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private String status;
    private String reason;
    private String cli;
    private LineItemSpecification specification;
    private List<ServiceSpecification> serviceSpecification;
    private List<Category> category;
}
