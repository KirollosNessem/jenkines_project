package ie.vodafone.dxl.checkservicefeasibility.utils;

import com.vodafone.core.exception.TMFException;
import ie.vodafone.dxl.utils.exceptions.model.DxlCsmException;
import org.apache.http.HttpStatus;

import java.net.SocketTimeoutException;
import java.util.Optional;

import static ie.vodafone.dxl.utils.exceptions.ExceptionUtil.buildTMFErrorWithCode;
import static ie.vodafone.dxl.utils.exceptions.ExceptionUtil.buildTMFExceptionWithGenerics;
import static ie.vodafone.dxl.utils.exceptions.constant.ExceptionConstants.GENERIC_CSM_ERROR_CODE;
import static ie.vodafone.dxl.utils.exceptions.constant.ExceptionConstants.GENERIC_MESSAGE_ERROR;
import static ie.vodafone.dxl.utils.exceptions.constant.GenericErrorMessageEnum.PROXY_SOCKET_TIMEOUT;

public class ExceptionUtil {

    private ExceptionUtil() {
        throw new IllegalStateException("Utility Class");
    }

    public static TMFException checkTimeoutAndBuildTMFException(Throwable throwable) {
        return Optional.ofNullable(throwable)
                .map(Throwable::getCause)
                .filter(SocketTimeoutException.class::isInstance)
                .map(c -> buildTMFExceptionWithGenerics(HttpStatus.SC_INTERNAL_SERVER_ERROR, PROXY_SOCKET_TIMEOUT))
                .orElseGet(() -> {
                    if (throwable != null) {
                        return buildTMFErrorWithCode(HttpStatus.SC_INTERNAL_SERVER_ERROR, throwable.getMessage(), GENERIC_CSM_ERROR_CODE);
                    }
                    return buildTMFErrorWithCode(HttpStatus.SC_INTERNAL_SERVER_ERROR, GENERIC_MESSAGE_ERROR, GENERIC_CSM_ERROR_CODE);
                });
    }

    public static DxlCsmException buildDxlCsmException(int httpStatus, ErrorMessageEnum errorMessageEnum) {
        return ie.vodafone.dxl.utils.exceptions.ExceptionUtil.buildDxlCsmException(httpStatus,
                Optional.ofNullable(errorMessageEnum).map(ErrorMessageEnum::getMessage).orElse(null),
                Optional.ofNullable(errorMessageEnum).map(ErrorMessageEnum::getErrorCode).orElse(null));
    }
}
