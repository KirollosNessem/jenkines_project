package ie.vodafone.dxl.checkservicefeasibility.soapclient;

import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMResponseType;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.ResultStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckServiceFeasibilityOsbResponse {
    private CheckServiceFeasibilityVBMResponseType osbResponse;
    private ResultStatus resultStatus;

    public CheckServiceFeasibilityOsbResponse(CheckServiceFeasibilityVBMResponseType osbResponse) {
        this.osbResponse = osbResponse;
    }
}
