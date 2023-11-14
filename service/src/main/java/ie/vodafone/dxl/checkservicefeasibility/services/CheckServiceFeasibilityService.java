package ie.vodafone.dxl.checkservicefeasibility.services;


import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMRequestType;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMResponseType;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceAbilityRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceAbilityResponse;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceFeasibilityRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceFeasibilityResponse;
import ie.vodafone.dxl.checkservicefeasibility.mappers.CheckServiceAbilityMapper;
import ie.vodafone.dxl.checkservicefeasibility.mappers.CheckServiceFeasibilityMapper;
import ie.vodafone.dxl.checkservicefeasibility.soapclient.CheckServiceFeasibilitySoapClient;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CheckServiceFeasibilityService {

//    private final CustomerPartyCacheService customerPartyCacheService;

    private final CheckServiceFeasibilitySoapClient soapClient;

    @Inject
    public CheckServiceFeasibilityService(CheckServiceFeasibilitySoapClient soapClient) {
        this.soapClient = soapClient;
    }


    public Uni<CheckServiceAbilityResponse> checkServiceAbility(CheckServiceAbilityRequest request) {
        //TODO cache that!
        CheckServiceFeasibilityVBMRequestType serviceRequest = CheckServiceAbilityMapper.mapCheckServiceAbilityRequest(request);

        Uni<CheckServiceFeasibilityVBMResponseType> serviceResponse = Uni.createFrom().completionStage(soapClient.callService(serviceRequest, null))
                .map(CheckServiceFeasibilityVBMResponseType.class::cast);

        return serviceResponse
                .map(CheckServiceFeasibilityVBMResponseType::getServiceFeasibilityVBO)
                .map(CheckServiceAbilityMapper::mapCheckServiceAbilityResponse);
                //.invoke(getCustomerPartyResponse -> customerPartyCacheService.saveCache(getCustomerPartyResponse, request));
    }

    public Uni<CheckServiceFeasibilityResponse> checkServiceFeasibility(CheckServiceFeasibilityRequest request) {
        //TODO cache that!
        CheckServiceFeasibilityVBMRequestType serviceRequest = CheckServiceFeasibilityMapper.mapCheckServiceFeasibilityRequest(request);

        Uni<CheckServiceFeasibilityVBMResponseType> serviceResponse = Uni.createFrom().completionStage(soapClient.callService(serviceRequest, null))
                .map(CheckServiceFeasibilityVBMResponseType.class::cast);

        return serviceResponse
                .map(CheckServiceFeasibilityVBMResponseType::getServiceFeasibilityVBO)
                .map(CheckServiceFeasibilityMapper::mapCheckServiceFeasibilityResponse);
        //.invoke(getCustomerPartyResponse -> customerPartyCacheService.saveCache(getCustomerPartyResponse, request));
    }
}
