package ie.vodafone.dxl.checkservicefeasibility.utils;

public enum ErrorMessageEnum {

    MISSING_OR_INVALID_VALUE("CSF100", "Missing or Invalid Value"),
    ORDER_ALREADY_EXIST("CSF101", "Order No already present in the system"),
    INVALID_UAN("CSF102", "Customer Account specified is not a valid UAN on AP system"),
    PREMISES_ID_NOT_SPECIFIED("CSF103", "V0023_Premises Id not specified"),
    LOCATION_ID_IS_REQUIRED("CSF104", "locationID is required"),
    INVALID_PRODUCT_TYPE("CSF105", "Invalid ProductType parameter"),
    INVALID_CONTENT("CSF106", "Invalid content was found"),
    INVALID_NBI_VALUE("CSF107", "Request Body Failed Validation with NBI");

    private String errorCode;
    private String message;

    ErrorMessageEnum(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
