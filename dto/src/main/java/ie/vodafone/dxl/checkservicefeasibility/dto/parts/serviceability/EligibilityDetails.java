package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<EligibilityDetailsServiceSpecification> serviceSpecifications;
}
