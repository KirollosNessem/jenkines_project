package ie.vodafone.dxl.checkservicefeasibility.services;


import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMRequestType;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceFeasibilityRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.QueryAncillaryServicesRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.QueryAncillaryServicesResponse;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceAbilityRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceAbilityResponse;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceFeasibilityResponse;
import ie.vodafone.dxl.checkservicefeasibility.mappers.CheckServiceAbilityMapper;
import ie.vodafone.dxl.checkservicefeasibility.mappers.CheckServiceFeasibilityMapper;
import ie.vodafone.dxl.checkservicefeasibility.mappers.QueryAncillaryServicesMapper;
import ie.vodafone.dxl.checkservicefeasibility.soapclient.CheckServiceFeasibilityOsbResponse;
import ie.vodafone.dxl.checkservicefeasibility.soapclient.CheckServiceFeasibilitySoapClient;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CheckServiceFeasibilityService {

    private final CheckServiceFeasibilitySoapClient soapClient;

    private final QueryAncillarysCacheService queryAncillarysCacheService;

    private final CheckServiceAbilityCacheService checkServiceAbilityCacheService;

    private final CheckServiceFeasibilityCacheService checkServiceFeasibilityCacheService;

    @Inject
    public CheckServiceFeasibilityService(CheckServiceFeasibilitySoapClient soapClient, QueryAncillarysCacheService queryAncillarysCacheService, CheckServiceAbilityCacheService checkServiceAbilityCacheService, CheckServiceFeasibilityCacheService checkServiceFeasibilityCacheService) {
        this.soapClient = soapClient;
        this.queryAncillarysCacheService = queryAncillarysCacheService;
        this.checkServiceAbilityCacheService = checkServiceAbilityCacheService;
        this.checkServiceFeasibilityCacheService = checkServiceFeasibilityCacheService;
    }

    public Uni<CheckServiceAbilityResponse> checkServiceAbility(CheckServiceAbilityRequest request) {
        CheckServiceAbilityResponse mappedResponse = this.checkServiceAbilityCacheService.getCache(request);
        if (mappedResponse == null) {
            CheckServiceFeasibilityVBMRequestType serviceRequest = CheckServiceAbilityMapper.mapCheckServiceAbilityRequest(request);
            Uni<CheckServiceFeasibilityOsbResponse> serviceResponse = Uni.createFrom()
                    .completionStage(soapClient.callService(serviceRequest, null))
                    .map(CheckServiceFeasibilityOsbResponse.class::cast);

            return serviceResponse.map(response -> CheckServiceAbilityMapper.mapCheckServiceAbilityResponse(response.getOsbResponse(), response.getResultStatus()))
                    .invoke(response -> checkServiceAbilityCacheService.saveCache(response, request));
        }
        return Uni.createFrom().item(mappedResponse);
    }

    public Uni<CheckServiceFeasibilityResponse> checkServiceFeasibility(CheckServiceFeasibilityRequest request) {
        CheckServiceFeasibilityResponse mappedResponse = this.checkServiceFeasibilityCacheService.getCache(request);
        if (mappedResponse == null) {
            CheckServiceFeasibilityVBMRequestType serviceRequest = CheckServiceFeasibilityMapper.mapCheckServiceFeasibilityRequest(request);

            Uni<CheckServiceFeasibilityOsbResponse> serviceResponse = Uni.createFrom()
                    .completionStage(soapClient.callService(serviceRequest, null))
                    .map(CheckServiceFeasibilityOsbResponse.class::cast);

            return serviceResponse.map(response -> CheckServiceFeasibilityMapper.mapCheckServiceFeasibilityResponse(response.getOsbResponse(), response.getResultStatus()))
                    .invoke(response -> checkServiceFeasibilityCacheService.saveCache(response, request));
        }
        return Uni.createFrom().item(mappedResponse);
    }

    public Uni<QueryAncillaryServicesResponse> queryAncillaryServices(QueryAncillaryServicesRequest request) {
        QueryAncillaryServicesResponse mappedResponse = this.queryAncillarysCacheService.getCache(request);
        if (mappedResponse == null) {
            CheckServiceFeasibilityVBMRequestType serviceRequest = QueryAncillaryServicesMapper.mapCheckServiceFeasibilityRequest(request);

            Uni<CheckServiceFeasibilityOsbResponse> serviceResponse = Uni.createFrom()
                    .completionStage(soapClient.callService(serviceRequest, null))
                    .map(CheckServiceFeasibilityOsbResponse.class::cast);

            return serviceResponse.map(response -> QueryAncillaryServicesMapper.mapCheckServiceFeasibilityResponse(response.getOsbResponse(), response.getResultStatus()))
                    .invoke(response -> queryAncillarysCacheService.saveCache(response, request));
        }
        return Uni.createFrom().item(mappedResponse);
    }
}
