package ie.vodafone.dxl.checkservicefeasibility.dto.parts;

import ie.vodafone.dxl.utils.common.StringUtils;
import lombok.Data;

@Data
public class Location {
    private String premisesId;
    private String ardkey;
    private String vmLocationId;
    private String nbiEircode;

    public boolean isEmpty() {
        return StringUtils.isBlank(premisesId)
                && StringUtils.isBlank(ardkey)
                && StringUtils.isBlank(vmLocationId)
                && StringUtils.isEmpty(nbiEircode);
    }
}
