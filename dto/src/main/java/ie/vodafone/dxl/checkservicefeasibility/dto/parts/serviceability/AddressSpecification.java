package ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability;

import lombok.Data;

@Data
public class AddressSpecification {
    private Boolean reserved;
    private Boolean pendingInstallation;
    private Boolean previousInstallation;
    private String reason;
    private String reservedDate;
    private String reservationOwner;
}

