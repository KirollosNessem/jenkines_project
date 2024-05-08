package ie.vodafone.dxl.checkservicefeasibility.services;

import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceAbilityRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceAbilityResponse;
import ie.vodafone.dxl.checkservicefeasibility.utils.Constants;
import ie.vodafone.dxl.utils.cache.CacheManager;
import ie.vodafone.dxl.utils.cache.CacheService;
import ie.vodafone.dxl.utils.cache.config.CacheConfiguration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@CacheManager(repository = "CheckServiceFeasibilityCSM", ttlConfigPath = "cache.checkServiceAbility.ttl", indexes = {Constants.IndexKeys.LINEITEM_TYPE})
public class CheckServiceAbilityCacheService extends CacheService<CheckServiceAbilityRequest, CheckServiceAbilityResponse> {

    @Inject
    public CheckServiceAbilityCacheService(CacheConfiguration cacheConfiguration) {
        this.cacheConfiguration = cacheConfiguration;
        this.initialize();
    }
}
