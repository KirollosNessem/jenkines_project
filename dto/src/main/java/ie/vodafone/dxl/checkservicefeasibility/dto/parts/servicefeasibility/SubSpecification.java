package ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubSpecification implements Serializable {
    private static final long serialVersionUID = 1L;

    private String asCode;
    private String teleNo;
    private List<SubSpecificationCategory> category;
    private String service;
    private String ontType;
    private String installationType;
    private String tag;
}
