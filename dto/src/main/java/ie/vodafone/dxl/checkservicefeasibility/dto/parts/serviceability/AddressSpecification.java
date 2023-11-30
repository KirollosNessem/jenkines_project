package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressSpecification implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean reserved;
    private Boolean pendingInstallation;
    private Boolean previousInstallation;
    private String reason;
    private String reservedDate;
    private String reservationOwner;
}

