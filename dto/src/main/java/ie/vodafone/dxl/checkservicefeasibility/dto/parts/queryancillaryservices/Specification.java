package ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Specification implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pendingOrders;
    private String tosFlag;
}
