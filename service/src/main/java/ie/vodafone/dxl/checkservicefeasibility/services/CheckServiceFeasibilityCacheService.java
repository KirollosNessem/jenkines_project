package ie.vodafone.dxl.checkservicefeasibility.services;


import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceFeasibilityRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceFeasibilityResponse;
import ie.vodafone.dxl.checkservicefeasibility.utils.Constants;
import ie.vodafone.dxl.utils.cache.CacheManager;
import ie.vodafone.dxl.utils.cache.CacheService;
import ie.vodafone.dxl.utils.cache.config.CacheConfiguration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@CacheManager(repository = "CheckServiceFeasibilityCSM", ttlConfigPath = "cache.checkServiceFeasibility.ttl", indexes = {Constants.IndexKeys.BASKET_ID, Constants.IndexKeys.ORDER_NUMBER})
public class CheckServiceFeasibilityCacheService extends CacheService<CheckServiceFeasibilityRequest, CheckServiceFeasibilityResponse> {

    @Inject
    public CheckServiceFeasibilityCacheService(CacheConfiguration cacheConfiguration) {
        this.cacheConfiguration = cacheConfiguration;
        this.initialize();
    }
}
