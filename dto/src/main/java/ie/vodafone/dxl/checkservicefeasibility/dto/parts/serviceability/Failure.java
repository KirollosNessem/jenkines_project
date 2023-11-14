package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.Data;

@Data
public class Failure {
    private String serviceProvider;
    private String pathValueText;
    private String code;
    private String text;
}
