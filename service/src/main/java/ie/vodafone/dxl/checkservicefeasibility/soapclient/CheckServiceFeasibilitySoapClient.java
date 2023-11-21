package ie.vodafone.dxl.checkservicefeasibility.soapclient;

import com.vodafone.core.exception.DXLException;
import com.vodafone.group.contract.vfo.fault.v1.FaultType;
import com.vodafone.group.schema.common.v1.FaultCategoryCodeType;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMRequestType;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMResponseType;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.ResultStatus;
import ie.vodafone.dxl.checkservicefeasibility.utils.Constants;
import ie.vodafone.dxl.checkservicefeasibility.utils.ErrorMessageEnum;
import ie.vodafone.dxl.checkservicefeasibility.utils.SoapUtils;
import ie.vodafone.dxl.utils.common.CollectionUtils;
import ie.vodafone.dxl.utils.exceptions.ExceptionUtil;
import ie.vodafone.dxl.utils.exceptions.constant.ExceptionConstants;
import ie.vodafone.dxl.utils.exceptions.model.DxlCsmException;
import ie.vodafone.dxl.utils.soap.handler.ConnectionHandlerImpl;
import ie.vodafone.dxl.utils.soap.util.ExtLayerUtil;
import io.quarkus.runtime.StartupEvent;
import org.apache.cxf.headers.Header;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.xml.bind.JAXBException;
import javax.xml.ws.BindingProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import static ie.vodafone.dxl.checkservicefeasibility.utils.ExceptionUtil.buildDxlCsmException;
import static ie.vodafone.dxl.checkservicefeasibility.utils.ExceptionUtil.checkTimeoutAndBuildTMFException;
import static ie.vodafone.dxl.utils.exceptions.ExceptionUtil.buildTMFErrorWithCode;
import static ie.vodafone.dxl.utils.exceptions.ExceptionUtil.buildTMFExceptionWithGenerics;
import static ie.vodafone.dxl.utils.exceptions.constant.ExceptionConstants.BUSINESS_CATEGORY_SOAP_FAULT_CODE;
import static ie.vodafone.dxl.utils.exceptions.constant.ExceptionConstants.EMPTY_SOAP_BODY_IN_RESPONSE;
import static ie.vodafone.dxl.utils.exceptions.constant.ExceptionConstants.GENERIC_OSB_ERROR_NO_TECH_OR_BUSINESS;
import static ie.vodafone.dxl.utils.exceptions.constant.GenericErrorMessageEnum.CIRCUIT_BREAKER_IS_OPEN;
import static ie.vodafone.dxl.utils.exceptions.constant.GenericErrorMessageEnum.EMPTY_SOAP_BODY;

/**
 * Soap client that extends External Access Layer
 */
@ApplicationScoped
public class CheckServiceFeasibilitySoapClient extends ConnectionHandlerImpl<CheckServiceFeasibilityInterface> {

    private static final Logger logger = LoggerFactory.getLogger(CheckServiceFeasibilitySoapClient.class);

    private static final String BUSINESS = "BUSINESS";
    private static final String TECHNICAL = "TECHNICAL";

    @ConfigProperty(name = "checkservicefeasibility.backend.action")
    String actionFromProp;

    @ConfigProperty(name = "checkservicefeasibility.backend.to")
    String actionToProp;

    /**
     * To call methods on startup
     */
    void onStart(@Observes StartupEvent ev) {
        logger.info("Initializing soapClient on startup");
        createClient(null);
    }

    /**
     * Overriding createPort by setting parameters
     *
     * @return
     */
    @Override
    public CheckServiceFeasibilityInterface createClient(ConcurrentMap<String, Header> headersList) {
        setServiceClass(CheckServiceFeasibilityInterface.class);
        logger.info("Service class is set as {} to create soap client by external layer", CheckServiceFeasibilityInterface.class);
        try {
            setHeaders(ExtLayerUtil.createHeaders(actionFromProp, actionToProp));
        } catch (JAXBException e) {
            logger.error("Error creating client headers in soap request");
            throw ExceptionUtil.buildTMFError(HttpStatus.SC_INTERNAL_SERVER_ERROR, ExceptionConstants.GENERIC_MESSAGE_ERROR);
        }
        Map<String, Object> clientProperties = new HashMap<>();
        clientProperties.put(Constants.PROP_JAXB_VALIDATION_EVENT_HANDLER, Boolean.FALSE);
        return super.createClient(headersList, clientProperties);
    }

    @Override
    @CircuitBreaker(requestVolumeThreshold = 60, failureRatio = 0.75, delay = 1000L, successThreshold = 1, skipOn = DxlCsmException.class)
    @Fallback(fallbackMethod = "callServiceFallback", applyOn = CircuitBreakerOpenException.class)
    @Retry(abortOn = DxlCsmException.class)
    @Asynchronous
    public CompletionStage<Object> callService(Object request, ConcurrentMap<String, Header> concurrentMap) {
        CompletableFuture<Object> future = new CompletableFuture<>();
        BindingProvider bindingProvider = (BindingProvider) soapClient;
        if (request.getClass() == CheckServiceFeasibilityVBMRequestType.class) {
            logger.info("Calling CheckServiceFeasibility soap operation ");
            CheckServiceFeasibilityVBMRequestType requestType = (CheckServiceFeasibilityVBMRequestType) request;
            checkServiceFeasibilityAsync(future, requestType, bindingProvider);
        }
        return future;
    }

    private void checkServiceFeasibilityAsync(CompletableFuture<Object> future, CheckServiceFeasibilityVBMRequestType requestType, BindingProvider bindingProvider) {
        soapClient.checkServiceFeasibilityAsync(requestType, asyncRes -> {
            try {
                CheckServiceFeasibilityVBMResponseType responseType = asyncRes.get();
                ResultStatus resultStatus = getResultStatus(bindingProvider);
                CheckServiceFeasibilityOsbResponse osbResponse = new CheckServiceFeasibilityOsbResponse(responseType, resultStatus);
                future.complete(osbResponse);
            } catch (DXLException e) {
                logger.error("OSB Failure - Failed to call getSubscription soap operation");
                handleResultStatusErrors(future, e);
            } catch (InterruptedException | ExecutionException e) {
                if (e.getMessage().contains(EMPTY_SOAP_BODY_IN_RESPONSE)) {
                    logger.error("empty Soap body in checkServiceFeasibility");
                    future.completeExceptionally(buildTMFExceptionWithGenerics(HttpStatus.SC_NOT_FOUND, EMPTY_SOAP_BODY));
                } else {
                    logger.error("OSB Failure - Failed to call checkServiceFeasibility soap operation");
                    boolean faultHandled = handleFault(future, VodafoneFault.class, VodafoneFault::getFaultInfo, e);
                    if (!faultHandled) {
                        future.completeExceptionally(checkTimeoutAndBuildTMFException(e));
                    }
                }
                Thread.currentThread().interrupt();
            }
        });
    }

    private void handleResultStatusErrors(CompletableFuture<Object> future, DXLException exception) {
        logger.error(exception.getMessage());
        if (exception.getMessage().contains(Constants.ErrorMessages.MISSING_MANDATORY) || exception.getMessage().contains(Constants.ErrorMessages.PREMISES_ID_NOT_SPECIFIED)) {
            future.completeExceptionally(buildDxlCsmException(HttpStatus.SC_BAD_REQUEST, ErrorMessageEnum.MISSING_OR_INVALID_VALUE));
        } else if (exception.getMessage().contains(Constants.ErrorMessages.INVALID_UAN) ) {
            future.completeExceptionally(buildDxlCsmException(HttpStatus.SC_BAD_REQUEST, ErrorMessageEnum.INVALID_UAN));
        }
        future.completeExceptionally(ie.vodafone.dxl.utils.exceptions.ExceptionUtil.buildDxlCsmException(HttpStatus.SC_BAD_REQUEST, exception.getMessage(), ExceptionConstants.BUSINESS_CATEGORY_SOAP_FAULT_CODE));
    }

    private ResultStatus getResultStatus(BindingProvider bindingProvider) throws DXLException {
        ResultStatus resultStatus = SoapUtils.getResultStatusFromSoapHeader(bindingProvider);
        if (resultStatus != null && CollectionUtils.isNotEmpty(resultStatus.getFailures())) {
            throw new DXLException("OSB failed with " + resultStatus.getFailures());
        }
        return resultStatus;
    }

    public static <T> boolean handleFault(CompletableFuture<?> future, Class<T> vodafoneFault, Function<T, FaultType> getFaultInfo, Exception e) {
        Optional<FaultType> faultType = Optional.ofNullable(e.getCause()).filter(vodafoneFault::isInstance).map(vodafoneFault::cast).map(getFaultInfo);
        if (faultType.isEmpty()) {
            return false;
        }

        String category = faultType
                .map(FaultType::getCategory)
                .map(FaultCategoryCodeType::value)
                .orElse(null);

        if (BUSINESS.equalsIgnoreCase(category)) {
            future.completeExceptionally(ie.vodafone.dxl.utils.exceptions.ExceptionUtil.buildDxlCsmException(HttpStatus.SC_BAD_REQUEST, e.getCause().getMessage(), BUSINESS_CATEGORY_SOAP_FAULT_CODE));
        } else if (TECHNICAL.equalsIgnoreCase(category)) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains(Constants.ErrorMessages.CLIENT_UNMARSHALLING) || errorMessage.contains(Constants.ErrorMessages.INVALID_REQUEST) || errorMessage.contains(Constants.ErrorMessages.MISSING_OR_INVALID_VALUE)) {
                future.completeExceptionally(buildDxlCsmException(HttpStatus.SC_BAD_REQUEST, ErrorMessageEnum.MISSING_OR_INVALID_VALUE));
            } else if (e.getMessage().contains(Constants.ErrorMessages.ORDER_ALREADY_EXIST)) {
                future.completeExceptionally(buildDxlCsmException(HttpStatus.SC_BAD_REQUEST, ErrorMessageEnum.ORDER_ALREADY_EXIST));
            } else {
                future.completeExceptionally(buildTMFErrorWithCode(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage(), GENERIC_OSB_ERROR_NO_TECH_OR_BUSINESS));
            }
        }
        return true;
    }

    private CompletionStage<Object> callServiceFallback(Object request, ConcurrentMap<String, Header> customHeaders) {
        return CompletableFuture.failedFuture(buildTMFExceptionWithGenerics(HttpStatus.SC_SERVICE_UNAVAILABLE, CIRCUIT_BREAKER_IS_OPEN));
    }
}
