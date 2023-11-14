//package ie.vodafone.dxl.checkservicefeasibility.services;
//
//
//import ie.vodafone.dxl.checkservicefeasibility.utils.Constants;
//import ie.vodafone.dxl.utils.cache.CacheManager;
//import ie.vodafone.dxl.utils.cache.CacheService;
//import ie.vodafone.dxl.utils.cache.config.CacheConfiguration;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//import java.util.List;
//
//@ApplicationScoped
//@CacheManager(repository = "CheckServiceFeasibilityCSM", ttlConfigPath = "cache.checkServiceFeasibility.ttl", indexes = {Constants.IndexKeys.INDIVIDUAL, Constants.IndexKeys.ORGANIZATION})
//public class CheckServiceFeasibilityCacheService extends CacheService<GetCustomerPartyRequest, List<GetCustomerPartyResponse>> {
//
//    @Inject
//    public CheckServiceFeasibilityCacheService(CacheConfiguration cacheConfiguration) {
//        this.cacheConfiguration = cacheConfiguration;
//        this.initialize();
//    }
//}
