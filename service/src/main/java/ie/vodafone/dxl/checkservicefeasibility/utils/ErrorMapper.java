package ie.vodafone.dxl.checkservicefeasibility.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ErrorMapper {

    public static Map<List<String>, ErrorMessageEnum> createErrorMappings() {
        Map<List<String>, ErrorMessageEnum> errorMappings = new LinkedHashMap<>();
        errorMappings.put(List.of(Constants.ErrorMessage.INVALID_PRODUCT_TYPE), ErrorMessageEnum.INVALID_PRODUCT_TYPE);
        errorMappings.put(List.of(Constants.ErrorMessage.LOCATION_ID_IS_REQUIRED), ErrorMessageEnum.LOCATION_ID_IS_REQUIRED);
        errorMappings.put(List.of(Constants.ErrorMessage.ORDER_ALREADY_EXIST), ErrorMessageEnum.ORDER_ALREADY_EXIST);
        errorMappings.put(List.of(Constants.ErrorMessage.INVALID_CONTENT), ErrorMessageEnum.INVALID_CONTENT);
        errorMappings.put(List.of(Constants.ErrorMessage.ENUMERATION_ERROR), ErrorMessageEnum.ENUMERATION_ERROR);
        errorMappings.put(List.of(Constants.ErrorMessage.MISSING_OR_INVALID_VALUE), ErrorMessageEnum.MISSING_OR_INVALID_VALUE);
        errorMappings.put(List.of(Constants.ErrorMessage.SERVICE_PROVIDER_NOT_FOUND), ErrorMessageEnum.SERVICE_PROVIDER_NOT_FOUND);
        errorMappings.put(List.of(Constants.ErrorMessage.VM_FAILURE), ErrorMessageEnum.VM_FAILURE);
        errorMappings.put(List.of(Constants.ErrorMessage.PREMISES_ID_NOT_SPECIFIED), ErrorMessageEnum.PREMISES_ID_NOT_SPECIFIED);
        errorMappings.put(List.of(Constants.ErrorMessage.MISSING_MANDATORY), ErrorMessageEnum.MISSING_OR_INVALID_VALUE);
        errorMappings.put(List.of(Constants.ErrorMessage.INVALID_UAN), ErrorMessageEnum.INVALID_UAN);
        errorMappings.put(List.of(Constants.ErrorMessage.UNEXPECTED_ERROR), ErrorMessageEnum.UNEXPECTED_ERROR);
        errorMappings.put(List.of(Constants.ErrorMessage.PREMISSES_NOT_FOUND), ErrorMessageEnum.PREMISSES_NOT_FOUND);
        errorMappings.put(List.of(Constants.ErrorMessage.REQUEST_FAILED_VALIDATION), ErrorMessageEnum.INVALID_NBI_VALUE);
        return errorMappings;
    }

    public static boolean matchesErrorMessage(List<String> keyList, String errorMessage) {
        if (keyList.size() == 1) {
            return errorMessage.contains(keyList.get(0));
        } else if (keyList.size() == 2) {
            return errorMessage.contains(keyList.get(0)) && errorMessage.contains(keyList.get(1));
        }
        return false;
    }
}
