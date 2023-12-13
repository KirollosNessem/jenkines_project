package ie.vodafone.dxl.checkservicefeasibility.dto;

import ie.vodafone.dxl.checkservicefeasibility.dto.parts.Location;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.LineItem;
import ie.vodafone.dxl.utils.common.CollectionUtils;
import ie.vodafone.dxl.utils.common.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckServiceAbilityRequest {

    private List<LineItem> lineItem;
    private Location location;
    private String status;

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(lineItem)
                && (location == null || location.isEmpty())
                && StringUtils.isEmpty(status);
    }
}
