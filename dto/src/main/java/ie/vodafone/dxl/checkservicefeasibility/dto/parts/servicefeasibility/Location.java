package ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility;

import ie.vodafone.dxl.utils.common.CollectionUtils;
import ie.vodafone.dxl.utils.common.StringUtils;
import lombok.Data;

@Data
public class Location {
    private String premisseId;
    private String ardkey;

    public boolean isEmpty() {
        return StringUtils.isBlank(premisseId)
                && StringUtils.isBlank(ardkey);
    }
}
