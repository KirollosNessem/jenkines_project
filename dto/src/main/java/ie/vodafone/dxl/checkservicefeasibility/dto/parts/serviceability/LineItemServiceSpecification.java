package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.Data;

import java.util.List;

@Data
public class LineItemServiceSpecification {
    private String name;
    private String maxUploadSpeed;
    private String maxDownloadSpeed;
    private Boolean isHighestPriority;
    private Boolean canShowTVOffers;
    private List<String> serviceCode;


}
