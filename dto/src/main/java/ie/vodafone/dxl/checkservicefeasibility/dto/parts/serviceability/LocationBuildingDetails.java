package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationBuildingDetails {
    private BigDecimal latitude;
    private BigDecimal longitude;
}
