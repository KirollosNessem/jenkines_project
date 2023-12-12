package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import ie.vodafone.dxl.utils.common.CollectionUtils;
import ie.vodafone.dxl.utils.common.StringUtils;
import lombok.Data;

@Data
public class Location {
    private String premisesId;
    private String nbiEircode;
    private String vmLocationId;
    private String ardkey;

    public boolean isEmpty() {
        return StringUtils.isEmpty(premisesId)
                && StringUtils.isEmpty(nbiEircode)
                && StringUtils.isEmpty(vmLocationId)
                && StringUtils.isEmpty(ardkey);
    }
}
