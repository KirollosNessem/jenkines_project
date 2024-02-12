package ie.vodafone.dxl.checkservicefeasibility.utils;

public class Constants {

    public static final String PROP_JAXB_VALIDATION_EVENT_HANDLER = "set-jaxb-validation-event-handler";

    public static class Soap {
        public static final String FAILURE = "Failure";
        public static final String HEADERS_LIST = "org.apache.cxf.headers.Header.list";
        public static final String RESULT_STATUS = "ResultStatus";
        public static final String PATH_VALUE_TEXT = "PathValueText";
        public static final String PATH_NAME = "PathName";
        public static final String DATA_REF = "Dataref";
    }

    public static class ErrorMessages {
        public static final String MISSING_OR_INVALID_VALUE = "Missing or Invalid Value";
        public static final String INVALID_REQUEST = "request is an invalid usage of the operation";
        public static final String INVALID_PRODUCT_TYPE= "Invalid ProductType parameter.";
        public static final String INVALID_CONTENT= "Invalid content";
        public static final String LOCATION_ID_IS_REQUIRED = "locationID is required";
        public static final String ORDER_ALREADY_EXIST = "Order No already present in the system";
        public static final String PREMISES_ID_NOT_SPECIFIED = "Premises Id not specified";
        public static final String MISSING_MANDATORY = "missing mandatory";
        public static final String INVALID_UAN = "not a valid UAN";
        public static final String REQUEST_FAILED_VALIDATION = "Request Body Failed Validation";
        public static final String NBI = "NBI";
        public static final String UNEXPECTED_ERROR = "Unexpected error at 'ProductGetAvailable_v2'";
        public static final String PREMISSES_NOT_FOUND = "Premises not found";
        public static final String ENUMERATION_ERROR = "not facet-valid with respect to enumeration";
        public static final String SERVICE_PROVIDER_NOT_FOUND = "serviceProvider}' is expected";

    }

    public static class IndexKeys {
        public static final String BASKET_ID = "basketId";
        public static final String ORDER_NUMBER = "orderNumber";
        public static final String LINEITEM_TYPE = "lineItem.type";
        public static final String UAN = "uan";
    }

    public static class CheckServiceFeasibilityRequest {
        public static final String PREMISES_ID = "PremisesId";
        public static final String ARD_KEY = "ARD_KEY";
        public static final String NBI_EIRCODE = "NBI_EIRCODE";
        public static final String ADDRESS_ID = "addressId";
        public static final String UAN = "UAN";
        public static final String PRODUCT_ORDER_ID = "ProductOrderID";
        public static final String BASKET_ID = "BasketID";
        public static final String ORDER_NUMBER = "OrderNumber";
        public static final String NEW_NETWORK_TECHNOLOGY = "NewNetworkTechnology";
        public static final String OLD_NETWORK_TECHNOLOGY = "oldNetworkTechnology";
        public static final String SERVICE_PROVIDER = "ServiceProvider";
        public static final String ASSIGNED_PRODUCT_ID = "assignedProductID";
        public static final String VM_LOCATION_ID = "VM_locationID";
        public static final String TRANSFER_REQUEST = "transferRequest";
    }

    public static class QueryAncillaryServicesRequest {
        public static final String UAN = "UAN";
        public static final String PENDING_ORDERS = "PENDING_ORDERS";
        public static final String TOS_FLAG = "TOS_FLAG";
        public static final String MULTICAST_SERVICE = "MULTICAST_SERVICE";
        public static final String BROADBAND_SERVICE = "BROADBAND_SERVICE";
        public static final String ACTION_FLAG = "ACTION_FLAG";
        public static final String CLI = "CLI";
        public static final String QAORDER_ID = "QAOrderID";
        public static final String INSITU_FLAG = "INSITU_FLAG";
        public static final String TELE_NO = "TELE_NO";

    }

    public static class CheckServiceFeasibilityResponse {
        public static final String QAORDER_ID = "QAOrderId";
        public static final String QA = "QA";
        public static final String ELIGIBILITY_CHECK = "EligibilityCheck";
        public static final String CLI = "CLI";
        public static final String ACTION_FLAG = "ACTION_FLAG";
        public static final String LEORDER_ID = "LEOrderID";
        public static final String EIRCODE = "eircode";
        public static final String ADDRESS_ID = "addressId";
        public static final String ORDER_ID = "orderId";
        public static final String VALID = "valid";
        public static final String ELIGIBILITY_STATUS = "EligibilityStatus";
        public static final String INELIGIBILITY_DESCRIPTION = "IneligibilityDescription";
    }

    public static class ServiceSpecificationResponse {
        public static final String ACTIVE = "active";
        public static final String AVAILABLE = "available";
        public static final String SERVICE = "service";
        public static final String ONT_TYPE = "ontType";
        public static final String CSID = "csid";
        public static final String ONT_ACTIVE = "ontActive";
        public static final String RESERVED = "reserved";
        public static final String INSTALLATION_TYPE = "installationType";
        public static final String TAG = "tag";
        public static final String LINE_ID = "lineId";
    }

    public static class CheckServiceFeasibilityResponseCategories {
        public static final String SYSTEMS_TO_CALL = "SystemsToCall";
        public static final String PENDING_ORDERS = "PENDING_ORDERS";
        public static final String TOS_FLAG = "TOS_FLAG";

    }

    public static class ServiceSpecificationsResponse {
        public static final String AS_DETAILS = "AS_DETAILS";
        public static final String ACTION_FLAG = "ACTION_FLAG";
        public static final String AS_CODE = "AS_CODE";
        public static final String TELE_NO = "TELE_NO";
    }

    public static class LineItemResponseSpecificationCharacteristic {
        public static final String PORT_AVAILABLE_FLAG = "PortAvailableFlag";
        public static final String ADDITIONAL_BUILD_INDICATOR = "AdditionalBuildIndicator";
        public static final String DOES_PENDING_ORDER_EXIST = "DoesPendingOrderExist";
        public static final String BROADBAND_SERVICE = "BROADBAND_SERVICE";
        public static final String SERVICE_PROVIDER = "ServiceProvider";
        public static final String INSITU_FLAG = "INSITU_FLAG";
        public static final String FEASIBILITY_CHECK = "feasibilityCheck";
        public static final String OPEN_ORDER_EXISTS = "openOrderExists";
        public static final String ACTIVE_ASSET_EXISTS = "activeAssetExists";

    }

    public static class Category {
        public static final String CATEGORY_NAME = "name";
        public static final String CATEGORY_LISTNAME = "listname";
        public static final String CATEGORY_LISTAGENCYNAME = "listAgencyName";
    }

    public static class CheckServiceAbilityRequest {
        public static final String EIRCODE = "EIRCODE";
        public static final String CLI = "CLI";
        public static final String NBI_EIRCODE = "NBI_EIRCODE";
        public static final String VM_LOCATION_ID = "VM_locationID";
    }

    public static class CheckServiceAbilityResponse {
        public static final String ORDER_ID = "orderId";
        public static final String ID = "id";
    }

    public static class LineItemName {
        public static final String BUILDING_DETAILS = "buildingDetails";
        public static final String ELIGIBILITY_DETAILS = "eligibilityDetails";
    }

    public static class LineItemSpecification {
        public static final String EIR_FTTH_APPLICABLE = "Eir_FTTH_Applicable";
        public static final String EIRCODE_EXISTS = "Eircode_Exists";
        public static final String IS_BBSASUPPORTED = "isBBSASupported";
        public static final String PREQUALIFICATION_SPEED = "prequalificationSpeed";
        public static final String PREQUALIFICATION_RESULT = "prequalificationResult";
        public static final String SERVICE_PROVIDER = "ServiceProvider";
    }

    public static class LineItemServiceSpecification {
        public static final String MAX_UPLOAD_SPEED = "maxUploadSpeed";
        public static final String MAX_DOWNLOAD_SPEED = "maxDownloadSpeed";
        public static final String IS_HIGHEST_PRIORITY = "IsHighestPriority";
        public static final String CAN_SHOW_TVOFFERS = "canShowTVOffers";
        public static final String SERVICE_CODE = "ServiceCode";
    }

    public static class BuildingDetails {
        public static final String BUILDING_ADDRESS = "buildingAddress";
        public static final String MULTIPLE_PREMISES = "multiplePremises";
        public static final String SURVEY_REQUIRED = "SurveyRequired";
        public static final String INTERVENTION_AREA = "interventionArea";
        public static final String INSTALLATION_TYPE = "installationType";
        public static final String SURVEY = "survey";
        public static final String SURVEY_STATUS = "surveyStatus";
        public static final String CUSTOMER_CONTRIBUTION = "customerContribution";
        public static final String ADDRESSES = "addresses";
        public static final String EIR_CODE = "eircode";
        public static final String RESERVED = "reserved";
        public static final String REASON = "reason";
        public static final String RESERVED_DATE = "reservedDate";
        public static final String RESERVATION_OWNER = "reservationOwner";
        public static final String PENDING_INSTALLATION = "pendingInstallation";
        public static final String PREVIOUS_INSTALLATION = "previousInstallation";
    }

    public static class EligibilityDetails {
        public static final String POH = "poh";
        public static final String TARGET_LAUNCH_PHASE = "targetLaunchPhase";
        public static final String CERTAINTY_LEVEL = "certaintyLevel";
        public static final String PREORDER_DATE = "preorderDate";
        public static final String INSTALLATION_TYPE = "installationType";
        public static final String PRE_ORDER_FLAG = "preOrderFlag";
        public static final String WAY_LEAVE_REQUIRED = "wayLeaveRequired";
        public static final String CONNECTION_STANDARD = "connectionStandard";
        public static final String DROP_TYPE = "dropType";
        public static final String READY_FOR_SERVICE_DATE = "readyForServiceDate";
        public static final String ELIGIBLE_PRODUCTS = "eligibleProducts";
        public static final String IN_HOME_SERVICES = "inHomeServices";
        public static final String CPE = "CPE";
    }
}
