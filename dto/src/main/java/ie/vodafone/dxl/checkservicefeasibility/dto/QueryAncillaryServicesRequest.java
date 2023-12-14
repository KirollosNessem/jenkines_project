package ie.vodafone.dxl.checkservicefeasibility.dto;

import ie.vodafone.dxl.utils.common.StringUtils;
import lombok.Data;

@Data
public class QueryAncillaryServicesRequest {
    private String status;
    private String uan;

    public boolean isEmpty() {
        return StringUtils.isBlank(status)
                && StringUtils.isBlank(uan);
    }
}
