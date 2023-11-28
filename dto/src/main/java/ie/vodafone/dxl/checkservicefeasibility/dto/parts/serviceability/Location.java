package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import ie.vodafone.dxl.utils.common.CollectionUtils;
import ie.vodafone.dxl.utils.common.StringUtils;
import lombok.Data;

@Data
public class Location {
    private String premissedId;
    private String nbiEircode;
    private String vmLocationId;
    private String ardkey;

    public boolean isEmpty() {
        return StringUtils.isEmpty(premissedId)
                && StringUtils.isEmpty(nbiEircode)
                && StringUtils.isEmpty(vmLocationId)
                && StringUtils.isEmpty(ardkey);
    }
}
