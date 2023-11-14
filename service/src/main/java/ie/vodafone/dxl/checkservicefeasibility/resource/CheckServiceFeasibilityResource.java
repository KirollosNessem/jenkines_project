package ie.vodafone.dxl.checkservicefeasibility.resource;

import com.vodafone.core.logging.customLogging.ULFFCustomData;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceAbilityRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceAbilityResponse;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceFeasibilityRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceFeasibilityResponse;
import ie.vodafone.dxl.checkservicefeasibility.services.CheckServiceFeasibilityService;
import ie.vodafone.dxl.utils.common.StringUtils;
import ie.vodafone.dxl.utils.common.retriever.ULFFRecordRetriever;
import ie.vodafone.dxl.utils.exceptions.ExceptionUtil;
import ie.vodafone.dxl.utils.exceptions.constant.ExceptionConstants;
import io.smallrye.mutiny.Uni;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/csm-api/checkServiceFeasibility/v4")
@ULFFCustomData(retriever = ULFFRecordRetriever.class)
public class CheckServiceFeasibilityResource {

    private static final Logger logger = LoggerFactory.getLogger(CheckServiceFeasibilityResource.class);

    @Inject
    CheckServiceFeasibilityService checkServiceFeasibilityService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path(value = "/checkServiceAbility")
    public Uni<CheckServiceAbilityResponse> checkServiceAbility(CheckServiceAbilityRequest request) {
        try {
            if (request == null || request.isEmpty()) {
                return Uni.createFrom().failure(() -> ExceptionUtil.buildTMFError(HttpStatus.SC_BAD_REQUEST, ExceptionConstants.EMPTY_REQUEST));
            }
            return checkServiceFeasibilityService.checkServiceAbility(request);
        } catch (Exception exception) {
            logger.error("An error occurred during process getCustomerPartyList operation", exception);
            return Uni.createFrom().failure(() -> ExceptionUtil.buildTMFError(HttpStatus.SC_INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path(value = "/checkServiceFeasibility")
    public Uni<CheckServiceFeasibilityResponse> checkServiceFeasibility(CheckServiceFeasibilityRequest request) {
        try {
            if (request == null || request.isEmpty()) {
                return Uni.createFrom().failure(() -> ExceptionUtil.buildTMFError(HttpStatus.SC_BAD_REQUEST, ExceptionConstants.EMPTY_REQUEST));
            }
            return checkServiceFeasibilityService.checkServiceFeasibility(request);
        } catch (Exception exception) {
            logger.error("An error occurred during process getCustomerPartyList operation", exception);
            return Uni.createFrom().failure(() -> ExceptionUtil.buildTMFError(HttpStatus.SC_INTERNAL_SERVER_ERROR, exception.getMessage()));
        }
    }


}
