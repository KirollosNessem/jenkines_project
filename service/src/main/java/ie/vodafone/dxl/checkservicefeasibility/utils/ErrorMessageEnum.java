package ie.vodafone.dxl.checkservicefeasibility.utils;

public enum ErrorMessageEnum {

    MISSING_OR_INVALID_VALUE("CSF100", "Missing or Invalid Value"),
    ORDER_ALREADY_EXIST("CSF101", "Order No already present in the system"),
    INVALID_UAN("CSF102", "Customer Account specified is not a valid UAN on AP system");

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
