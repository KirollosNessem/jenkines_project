package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.Data;

import java.util.List;

@Data
public class BuildingDetails {
    private String id;
    private String interventionArea;
    private List<Category> category;
    private BuildingDetailsSpecification specification;
    private List<BuildingDetailsServiceSpecification> serviceSpecifications;
    private LocationBuildingDetails location;
}
