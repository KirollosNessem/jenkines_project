package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineItemServiceSpecification implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String maxUploadSpeed;
    private String maxDownloadSpeed;
    private Boolean isHighestPriority;
    private Boolean canShowTVOffers;
    private List<String> serviceCode;


}
