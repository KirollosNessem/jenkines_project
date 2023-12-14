package ie.vodafone.dxl.checkservicefeasibility.services;

import ie.vodafone.dxl.checkservicefeasibility.dto.QueryAncillaryServicesRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.QueryAncillaryServicesResponse;
import ie.vodafone.dxl.checkservicefeasibility.utils.Constants;
import ie.vodafone.dxl.utils.cache.CacheManager;
import ie.vodafone.dxl.utils.cache.CacheService;
import ie.vodafone.dxl.utils.cache.config.CacheConfiguration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@CacheManager(repository = "CheckServiceFeasibilityCSM", ttlConfigPath = "cache.QueryAncillarys.ttl", indexes = {Constants.IndexKeys.UAN})
public class QueryAncillarysCacheService extends CacheService<QueryAncillaryServicesRequest, QueryAncillaryServicesResponse> {

    @Inject
    public QueryAncillarysCacheService(CacheConfiguration cacheConfiguration) {
        this.cacheConfiguration = cacheConfiguration;
        this.initialize();
    }
}
