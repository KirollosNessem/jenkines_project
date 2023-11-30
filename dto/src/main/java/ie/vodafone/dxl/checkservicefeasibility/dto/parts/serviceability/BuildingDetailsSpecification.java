package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingDetailsSpecification implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean multiplePremises;
    private Boolean surveyRequired;
    private Boolean interventionArea;

}
