package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String interventionArea;
    private String buildingAddress;
    private BuildingDetailsSpecification specification;
    private List<BuildingDetailsServiceSpecification> serviceSpecifications;
    private LocationBuildingDetails location;
}
