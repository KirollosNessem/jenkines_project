package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.Data;

import java.util.List;

@Data
public class LineItem {

    private List<Specification> specification;
    private String type;
    private String eircode;
    private String cli;

}
