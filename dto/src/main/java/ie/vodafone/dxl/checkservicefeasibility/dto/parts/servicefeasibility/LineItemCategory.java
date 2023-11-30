package ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineItemCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    private String actionFlag;
    private String eligibilityStatus;
}
