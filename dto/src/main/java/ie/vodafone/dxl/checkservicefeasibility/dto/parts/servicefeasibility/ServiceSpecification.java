package ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceSpecification implements Serializable {
    private static final long serialVersionUID = 1L;

    private String asDetails;
    private String active;
    private String available;
    private String service;
    private String ontActive;
    private String reserved;
    private String lineId;
    private String installationType;
    private SubSpecification specification;
}
