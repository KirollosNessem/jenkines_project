package ie.vodafone.dxl.checkservicefeasibility.mappers;

import com.vodafone.group.schema.common.v1.BaseComponentType;
import com.vodafone.group.schema.common.v1.GeographicLocationType;
import com.vodafone.group.schema.common.v1.PostalAddressWithLocationType;
import com.vodafone.group.schema.common.v1.SpecificationType;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMRequestType;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMResponseType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityLineItemType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityLocationType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityPartsType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityVBOType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceSpecSpecificationType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceSpecType;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceAbilityRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceAbilityResponse;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.ResultStatus;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.LineItem;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.Location;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.Address;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.AddressSpecification;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.BuildingDetails;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.BuildingDetailsServiceSpecification;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.BuildingDetailsSpecification;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.EligibilityDetails;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.EligibilityDetailsServiceSpecification;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.EligibilityDetailsSpecification;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.LineItemResponse;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.LineItemServiceSpecification;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.LineItemSpecification;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.LocationBuildingDetails;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.Specification;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.serviceability.Survey;
import ie.vodafone.dxl.checkservicefeasibility.utils.Constants;
import ie.vodafone.dxl.checkservicefeasibility.utils.WSUtils;
import ie.vodafone.dxl.utils.common.CollectionUtils;
import ie.vodafone.dxl.utils.common.StringUtils;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.IDType;

import java.util.ArrayList;
import java.util.List;

public class CheckServiceAbilityMapper {

    public static CheckServiceFeasibilityVBMRequestType mapCheckServiceAbilityRequest(CheckServiceAbilityRequest request) {
        CheckServiceFeasibilityVBMRequestType serviceFeasibilityRequest = new CheckServiceFeasibilityVBMRequestType();
        ServiceFeasibilityVBOType serviceFeasibilityVBO = new ServiceFeasibilityVBOType();
        if (StringUtils.isNotBlank(request.getStatus())) {
            serviceFeasibilityVBO.setStatus(WSUtils.createCodeType(request.getStatus()));
        }
        if (CollectionUtils.isNotEmpty(request.getLineItem()) || (request.getLocation() != null && !request.getLocation().isEmpty())) {
            ServiceFeasibilityPartsType parts = new ServiceFeasibilityPartsType();
            mapLineItem(parts, request.getLineItem());
            mapLocation(parts, request.getLocation());
            serviceFeasibilityVBO.setParts(parts);
        }
        serviceFeasibilityRequest.setServiceFeasibilityVBO(serviceFeasibilityVBO);
        return serviceFeasibilityRequest;
    }

    public static CheckServiceAbilityResponse mapCheckServiceAbilityResponse(CheckServiceFeasibilityVBMResponseType serviceFeasibilityVBMResponseType, ResultStatus resultStatus) {
        CheckServiceAbilityResponse response = new CheckServiceAbilityResponse();
        if (resultStatus != null && StringUtils.isNotBlank(resultStatus.getName())) {
            response.setName(resultStatus.getName());
        }
        if (serviceFeasibilityVBMResponseType == null || serviceFeasibilityVBMResponseType.getServiceFeasibilityVBO() == null) {
            return response;
        }
        ServiceFeasibilityVBOType serviceFeasibilityVBOType = serviceFeasibilityVBMResponseType.getServiceFeasibilityVBO();
        if (serviceFeasibilityVBOType.getParts() != null) {
            ServiceFeasibilityPartsType serviceFeasibilityParts = serviceFeasibilityVBOType.getParts();
            mapResponseLineItems(response, serviceFeasibilityParts.getLineItems());
        }
        if (serviceFeasibilityVBOType.getIDs() != null) {
            mapResponseIDs(response, serviceFeasibilityVBOType.getIDs().getID());
        }
        return response;
    }

    private static void mapResponseLineItems(CheckServiceAbilityResponse response, ServiceFeasibilityPartsType.LineItems lineItems) {
        if (lineItems == null || CollectionUtils.isEmpty(lineItems.getLineItem())) {
            return;
        }
        List<LineItemResponse> lineItemResponseList = new ArrayList<>();
        lineItems.getLineItem().forEach(lineItem -> {
            LineItemResponse lineItemResponse = new LineItemResponse();
            if (Constants.LineItemName.BUILDING_DETAILS.equals(WSUtils.getValueFromTextType(lineItem.getName()))) {
                mapBuildingDetails(lineItem, lineItemResponse);
            } else if (Constants.LineItemName.ELIGIBILITY_DETAILS.equals(WSUtils.getValueFromTextType(lineItem.getName()))) {
                mapEligibilityDetails(lineItem, lineItemResponse);
            } else {
                mapLineItem(lineItem, lineItemResponse);
            }
            lineItemResponseList.add(lineItemResponse);
        });
        response.setLineItem(lineItemResponseList);
    }

    private static void mapEligibilityDetails(ServiceFeasibilityLineItemType lineItem, LineItemResponse lineItemResponse) {
        if (lineItem.getServiceSpecifications() == null) {
            return;
        }
        EligibilityDetails eligibilityDetails = new EligibilityDetails();
        mapEligibilityDetailsSpecification(eligibilityDetails, lineItem.getServiceSpecifications().getServiceSpecification());
        lineItemResponse.setEligibilityDetails(eligibilityDetails);
    }

    private static void mapLineItem(ServiceFeasibilityLineItemType lineItem, LineItemResponse lineItemResponse) {
        lineItemResponse.setStatus(WSUtils.getValueFromCodeType(lineItem.getStatus()));
        if (lineItem.getSpecification() != null) {
            mapLineItemSpecificationCharacteristics(lineItemResponse, lineItem.getSpecification().getCharacteristicsValue());
        }
        if (lineItem.getServiceSpecifications() != null) {
            mapLineItemServiceSpecificationResponse(lineItemResponse, lineItem.getServiceSpecifications().getServiceSpecification());
        }
    }

    private static void mapBuildingDetails(ServiceFeasibilityLineItemType lineItem, LineItemResponse lineItemResponse) {
        BuildingDetails buildingDetails = new BuildingDetails();
        if (lineItem.getIDs() != null) {
            mapBuildingDetailsIDs(buildingDetails, lineItem.getIDs().getID());
        }
        if (lineItem.getCategories() != null) {
            mapBuildingDetailsCategories(buildingDetails, lineItem.getCategories().getCategory());
        }
        if (lineItem.getSpecification() != null) {
            mapBuildingDetailsSpecifications(buildingDetails, lineItem.getSpecification().getCharacteristicsValue());
        }
        if (lineItem.getServiceSpecifications() != null) {
            mapBuildingDetailsServiceSpecifications(buildingDetails, lineItem.getServiceSpecifications().getServiceSpecification());
        }
        if (lineItem.getLocation() != null) {
            mapBuildingDetailsLocation(buildingDetails, lineItem.getLocation().getGeoLocation());
        }
        lineItemResponse.setBuildingDetails(buildingDetails);
    }

    private static void mapBuildingDetailsLocation(BuildingDetails buildingDetails, GeographicLocationType geoLocation) {
        if (geoLocation == null) {
            return;
        }
        LocationBuildingDetails location = new LocationBuildingDetails();
        location.setLatitude(WSUtils.getValueFromMeasureType(geoLocation.getLatitudeMeasure()));
        location.setLongitude(WSUtils.getValueFromMeasureType(geoLocation.getLongitudeMeasure()));
        buildingDetails.setLocation(location);
    }

    private static void mapEligibilityDetailsSpecification(EligibilityDetails eligibilityDetails, List<ServiceSpecType> serviceSpecification) {
        if (CollectionUtils.isEmpty(serviceSpecification)) {
            return;
        }
        List<EligibilityDetailsServiceSpecification> serviceSpecifications = new ArrayList<>();
        for (ServiceSpecType serviceSpecType : serviceSpecification) {
            EligibilityDetailsServiceSpecification eligibilityDetailsServiceSpecification = new EligibilityDetailsServiceSpecification();
            ServiceSpecSpecificationType specification = serviceSpecType.getSpecification();
            eligibilityDetailsServiceSpecification.setDescription(WSUtils.getValueFromTextType(serviceSpecType.getDesc()));
            if (specification != null) {
                mapEligibilityDetailsCharacteristics(eligibilityDetailsServiceSpecification, specification.getCharacteristicsValue());
            }
            serviceSpecifications.add(eligibilityDetailsServiceSpecification);
        }
        eligibilityDetails.setServiceSpecifications(serviceSpecifications);
    }

    private static void mapEligibilityDetailsCharacteristics(EligibilityDetailsServiceSpecification eligibilityDetailsServiceSpecification, List<SpecificationType.CharacteristicsValue> specification) {
        if (CollectionUtils.isEmpty(specification)) {
            return;
        }
        EligibilityDetailsSpecification eligibilityDetailsSpecification = new EligibilityDetailsSpecification();
        for (SpecificationType.CharacteristicsValue value : specification) {
            String characteristicName = value.getCharacteristicName();
            if (Constants.EligibilityDetails.POH.equalsIgnoreCase(characteristicName)) {
                eligibilityDetailsSpecification.setPoh(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.EligibilityDetails.TARGET_LAUNCH_PHASE.equalsIgnoreCase(characteristicName)) {
                eligibilityDetailsSpecification.setTargetLaunchPhase(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.EligibilityDetails.CERTAINTY_LEVEL.equalsIgnoreCase(characteristicName)) {
                eligibilityDetailsSpecification.setCertaintyLevel(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.EligibilityDetails.PREORDER_DATE.equalsIgnoreCase(characteristicName)) {
                eligibilityDetailsSpecification.setPreorderDate(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.EligibilityDetails.INSTALLATION_TYPE.equalsIgnoreCase(characteristicName)) {
                eligibilityDetailsSpecification.setInstallationType(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.EligibilityDetails.PRE_ORDER_FLAG.equalsIgnoreCase(characteristicName)) {
                eligibilityDetailsSpecification.setPreOrderFlag(Boolean.parseBoolean(WSUtils.getValueFromTextType(value.getValue())));
            } else if (Constants.EligibilityDetails.WAY_LEAVE_REQUIRED.equalsIgnoreCase(characteristicName)) {
                eligibilityDetailsSpecification.setWayLeaveRequired(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.EligibilityDetails.CONNECTION_STANDARD.equalsIgnoreCase(characteristicName)) {
                eligibilityDetailsSpecification.setConnectionStandard(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.EligibilityDetails.DROP_TYPE.equalsIgnoreCase(characteristicName)) {
                eligibilityDetailsSpecification.setDropType(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.EligibilityDetails.READY_FOR_SERVICE_DATE.equalsIgnoreCase(characteristicName)) {
                eligibilityDetailsSpecification.setReadyForServiceDate(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.EligibilityDetails.ELIGIBLE_PRODUCTS.equalsIgnoreCase(characteristicName)) {
                if (CollectionUtils.isEmpty(eligibilityDetailsSpecification.getEligibleProducts())) {
                    eligibilityDetailsSpecification.setEligibleProducts(new ArrayList<>());
                }
                eligibilityDetailsSpecification.getEligibleProducts().add(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.EligibilityDetails.IN_HOME_SERVICES.equalsIgnoreCase(characteristicName)) {
                if (CollectionUtils.isEmpty(eligibilityDetailsSpecification.getInHomeServices())) {
                    eligibilityDetailsSpecification.setInHomeServices(new ArrayList<>());
                }
                eligibilityDetailsSpecification.getInHomeServices().add(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.EligibilityDetails.CPE.equalsIgnoreCase(characteristicName)) {
                eligibilityDetailsSpecification.setCpe(WSUtils.getValueFromTextType(value.getValue()));
            }
        }
        eligibilityDetailsServiceSpecification.setSpecification(eligibilityDetailsSpecification);
    }

    private static void mapBuildingDetailsServiceSpecifications(BuildingDetails buildingDetails, List<ServiceSpecType> serviceSpecification) {
        if (CollectionUtils.isEmpty(serviceSpecification)) {
            return;
        }
        List<BuildingDetailsServiceSpecification> serviceSpecifications = new ArrayList<>();
        for (ServiceSpecType serviceSpecType : serviceSpecification) {
            BuildingDetailsServiceSpecification specification = new BuildingDetailsServiceSpecification();
            if (Constants.BuildingDetails.SURVEY.equals(WSUtils.getValueFromTextType(serviceSpecType.getName()))) {
                mapBuildingDetailsSurvey(serviceSpecType, specification);
            } else if (Constants.BuildingDetails.ADDRESSES.equals(WSUtils.getValueFromTextType(serviceSpecType.getName()))) {
                Address address = new Address();
                if (serviceSpecType.getIDs() != null) {
                    mapAddressIDs(address, serviceSpecType.getIDs().getID());
                }
                if (serviceSpecType.getCategories() != null) {
                    mapAddressCategories(address, serviceSpecType.getCategories().getCategory());
                }
                if (serviceSpecType.getSpecification() != null) {
                    mapAddressSpecification(address, serviceSpecType.getSpecification().getCharacteristicsValue());
                }
                address.setDescription(WSUtils.getValueFromTextType(serviceSpecType.getDesc()));
                specification.setAddress(address);
            }
            serviceSpecifications.add(specification);
        }
        buildingDetails.setServiceSpecifications(serviceSpecifications);
    }

    private static void mapAddressSpecification(Address address, List<SpecificationType.CharacteristicsValue> characteristicsValue) {
        if (CollectionUtils.isEmpty(characteristicsValue)) {
            return;
        }
        AddressSpecification addressSpecification = new AddressSpecification();
        for (SpecificationType.CharacteristicsValue value : characteristicsValue) {
            if (Constants.BuildingDetails.RESERVED.equalsIgnoreCase(value.getCharacteristicName())) {
                addressSpecification.setReserved(Boolean.parseBoolean(WSUtils.getValueFromTextType(value.getValue())));
            } else if (Constants.BuildingDetails.REASON.equalsIgnoreCase(value.getCharacteristicName())) {
                addressSpecification.setReason(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.BuildingDetails.RESERVED_DATE.equalsIgnoreCase(value.getCharacteristicName())) {
                addressSpecification.setReservedDate(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.BuildingDetails.RESERVATION_OWNER.equalsIgnoreCase(value.getCharacteristicName())) {
                addressSpecification.setReservationOwner(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.BuildingDetails.PENDING_INSTALLATION.equalsIgnoreCase(value.getCharacteristicName())) {
                addressSpecification.setPendingInstallation(Boolean.parseBoolean(WSUtils.getValueFromTextType(value.getValue())));
            } else if (Constants.BuildingDetails.PREVIOUS_INSTALLATION.equalsIgnoreCase(value.getCharacteristicName())) {
                addressSpecification.setPreviousInstallation(Boolean.parseBoolean(WSUtils.getValueFromTextType(value.getValue())));
            }
        }
        address.setSpecification(addressSpecification);
    }

    private static void mapAddressCategories(Address address, List<BaseComponentType.Categories.Category> categoryList) {
        if (CollectionUtils.isEmpty(categoryList)) {
            return;
        }
        for (BaseComponentType.Categories.Category category : categoryList) {
            if (Constants.BuildingDetails.EIR_CODE.equals(category.getListName())) {
                address.setEircode(category.getValue());
            }
        }
    }

    private static void mapAddressIDs(Address address, List<IDType> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        address.setId(WSUtils.getValueFromIDType(ids.get(0)));
    }

    private static void mapBuildingDetailsSurvey(ServiceSpecType serviceSpecType, BuildingDetailsServiceSpecification specification) {
        Survey survey = new Survey();
        if (serviceSpecType.getType() != null && Constants.BuildingDetails.INSTALLATION_TYPE.equals(serviceSpecType.getType().getListName())) {
            survey.setInstallationType(serviceSpecType.getType().getValue());
        }
        if (serviceSpecType.getStatus() != null && Constants.BuildingDetails.SURVEY_STATUS.equals(serviceSpecType.getStatus().getListName())) {
            survey.setSurveyStatus(serviceSpecType.getStatus().getValue());
        }
        if (serviceSpecType.getSpecification() != null && CollectionUtils.isNotEmpty(serviceSpecType.getSpecification().getCharacteristicsValue())) {
            for (SpecificationType.CharacteristicsValue characteristicsValue : serviceSpecType.getSpecification().getCharacteristicsValue()) {
                if (Constants.BuildingDetails.CUSTOMER_CONTRIBUTION.equals(characteristicsValue.getCharacteristicName())) {
                    survey.setCustomerContribution(WSUtils.getValueFromTextType(characteristicsValue.getValue()));
                }
            }
        }
        specification.setSurvey(survey);
    }

    private static void mapBuildingDetailsSpecifications(BuildingDetails buildingDetails, List<SpecificationType.CharacteristicsValue> characteristicsValue) {
        if (CollectionUtils.isEmpty(characteristicsValue)) {
            return;
        }
        BuildingDetailsSpecification specification = new BuildingDetailsSpecification();
        for (SpecificationType.CharacteristicsValue value : characteristicsValue) {
            if (Constants.BuildingDetails.MULTIPLE_PREMISES.equalsIgnoreCase(value.getCharacteristicName())) {
                specification.setMultiplePremises(Boolean.parseBoolean(WSUtils.getValueFromTextType(value.getValue())));
            } else if (Constants.BuildingDetails.SURVEY_REQUIRED.equalsIgnoreCase(value.getCharacteristicName())) {
                specification.setSurveyRequired(Boolean.parseBoolean(WSUtils.getValueFromTextType(value.getValue())));
            } else if (Constants.BuildingDetails.INTERVENTION_AREA.equalsIgnoreCase(value.getCharacteristicName())) {
                specification.setInterventionArea(Boolean.parseBoolean(WSUtils.getValueFromTextType(value.getValue())));
            }
        }
        buildingDetails.setSpecification(specification);
    }


    private static void mapBuildingDetailsCategories(BuildingDetails buildingDetails, List<BaseComponentType.Categories.Category> categories) {
        if (CollectionUtils.isEmpty(categories)) {
            return;
        }
        for (BaseComponentType.Categories.Category category : categories) {
            if (Constants.BuildingDetails.BUILDING_ADDRESS.equals(category.getListName())) {
                buildingDetails.setBuildingAddress(category.getValue());
            }
        }
    }

    private static void mapBuildingDetailsIDs(BuildingDetails buildingDetails, List<IDType> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        ids.forEach(id -> {
            if (Constants.CheckServiceAbilityResponse.ID.equals(id.getSchemeName())) {
                buildingDetails.setId(WSUtils.getValueFromIDType(id));
            }
        });
    }

    private static void mapResponseIDs(CheckServiceAbilityResponse response, List<IDType> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        ids.forEach(id -> {
            if (Constants.CheckServiceAbilityResponse.ORDER_ID.equals(id.getSchemeName())) {
                response.setOrderId(WSUtils.getValueFromIDType(id));
            }
        });
    }

    private static void mapLineItemServiceSpecificationResponse(LineItemResponse lineItemResponse, List<ServiceSpecType> serviceSpecificationList) {
        if (CollectionUtils.isEmpty(serviceSpecificationList)) {
            return;
        }
        List<LineItemServiceSpecification> serviceSpecificationResponseList = new ArrayList<>();
        for (ServiceSpecType serviceSpecType : serviceSpecificationList) {
            LineItemServiceSpecification serviceSpecification = new LineItemServiceSpecification();
            serviceSpecification.setName(WSUtils.getValueFromTextType(serviceSpecType.getName()));
            mapServiceSpecificationsCharacteristics(serviceSpecification, serviceSpecType.getSpecification());
            serviceSpecificationResponseList.add(serviceSpecification);
        }
        lineItemResponse.setServiceSpecification(serviceSpecificationResponseList);
    }

    private static void mapServiceSpecificationsCharacteristics(LineItemServiceSpecification serviceSpecification, ServiceSpecSpecificationType specification) {
        if (specification == null || CollectionUtils.isEmpty(specification.getCharacteristicsValue())) {
            return;
        }
        for (SpecificationType.CharacteristicsValue characteristic : specification.getCharacteristicsValue()) {
            if (Constants.LineItemServiceSpecification.MAX_UPLOAD_SPEED.equalsIgnoreCase(characteristic.getCharacteristicName())) {
                serviceSpecification.setMaxUploadSpeed(WSUtils.getValueFromTextType(characteristic.getValue()));
            } else if (Constants.LineItemServiceSpecification.MAX_DOWNLOAD_SPEED.equalsIgnoreCase(characteristic.getCharacteristicName())) {
                serviceSpecification.setMaxDownloadSpeed(WSUtils.getValueFromTextType(characteristic.getValue()));
            } else if (Constants.LineItemServiceSpecification.IS_HIGHEST_PRIORITY.equalsIgnoreCase(characteristic.getCharacteristicName())) {
                serviceSpecification.setIsHighestPriority(Boolean.parseBoolean(WSUtils.getValueFromTextType(characteristic.getValue())));
            } else if (Constants.LineItemServiceSpecification.CAN_SHOW_TVOFFERS.equalsIgnoreCase(characteristic.getCharacteristicName())) {
                serviceSpecification.setCanShowTVOffers(Boolean.parseBoolean(WSUtils.getValueFromTextType(characteristic.getValue())));
            } else if (Constants.LineItemServiceSpecification.SERVICE_CODE.equalsIgnoreCase(characteristic.getCharacteristicName())) {
                if (CollectionUtils.isEmpty(serviceSpecification.getServiceCode())) {
                    serviceSpecification.setServiceCode(new ArrayList<>());
                }
                serviceSpecification.getServiceCode().add(WSUtils.getValueFromTextType(characteristic.getValue()));
            }
        }
    }

    private static void mapLineItemSpecificationCharacteristics(LineItemResponse lineItemResponse, List<SpecificationType.CharacteristicsValue> characteristicsValue) {
        if (CollectionUtils.isEmpty(characteristicsValue)) {
            return;
        }
        LineItemSpecification specification = new LineItemSpecification();
        for (SpecificationType.CharacteristicsValue characteristic : characteristicsValue) {
            String characteristicName = characteristic.getCharacteristicName();
            if (Constants.LineItemSpecification.EIR_FTTH_APPLICABLE.equalsIgnoreCase(characteristicName)) {
                specification.setEirFithApplicable(WSUtils.getValueFromTextType(characteristic.getValue()));
            } else if (Constants.LineItemSpecification.EIRCODE_EXISTS.equalsIgnoreCase(characteristicName)) {
                specification.setEircodeExists(WSUtils.getValueFromTextType(characteristic.getValue()));
            } else if (Constants.LineItemSpecification.IS_BBSASUPPORTED.equalsIgnoreCase(characteristicName)) {
                specification.setIsBbsaSupported(Boolean.parseBoolean(WSUtils.getValueFromTextType(characteristic.getValue())));
            } else if (Constants.LineItemSpecification.PREQUALIFICATION_SPEED.equalsIgnoreCase(characteristicName)) {
                specification.setPrequalificationSpeed(WSUtils.getValueFromTextType(characteristic.getValue()));
            } else if (Constants.LineItemSpecification.PREQUALIFICATION_RESULT.equalsIgnoreCase(characteristicName)) {
                specification.setPrequalificationResult(WSUtils.getValueFromTextType(characteristic.getValue()));
            } else if (Constants.LineItemSpecification.SERVICE_PROVIDER.equalsIgnoreCase(characteristicName)) {
                specification.setServiceProvider(WSUtils.getValueFromTextType(characteristic.getValue()));
            }
        }
        lineItemResponse.setSpecification(specification);
    }


    private static void mapLineItem(ServiceFeasibilityPartsType parts, List<LineItem> lineItemRequest) {
        if (CollectionUtils.isEmpty(lineItemRequest)) {
            return;
        }
        ServiceFeasibilityPartsType.LineItems lineItems = new ServiceFeasibilityPartsType.LineItems();
        for (LineItem item : lineItemRequest) {
            ServiceFeasibilityLineItemType serviceFeasibilityLineItem = new ServiceFeasibilityLineItemType();
            serviceFeasibilityLineItem.setType(WSUtils.createCodeType(item.getType()));
            mapLineItemIDs(item, serviceFeasibilityLineItem);
            mapLineItemServiceSpecification(item, serviceFeasibilityLineItem);
            lineItems.getLineItem().add(serviceFeasibilityLineItem);
        }
        parts.setLineItems(lineItems);

    }

    private static void mapLocation(ServiceFeasibilityPartsType parts, Location location) {
        if (location == null) {
            return;
        }
        ServiceFeasibilityLocationType serviceFeasibilityLocation = new ServiceFeasibilityLocationType();
        PostalAddressWithLocationType.IDs iDs = new PostalAddressWithLocationType.IDs();
        WSUtils.addIdIfExists(iDs, location.getVmLocationId(), Constants.CheckServiceAbilityRequest.VM_LOCATION_ID);
        WSUtils.addIdIfExists(iDs, location.getArdkey(), Constants.CheckServiceFeasibilityRequest.ARD_KEY);
        WSUtils.addIdIfExists(iDs, location.getPremisesId(), Constants.CheckServiceFeasibilityRequest.PREMISES_ID);
        WSUtils.addIdIfExists(iDs, location.getNbiEircode(), Constants.CheckServiceAbilityRequest.NBI_EIRCODE);
        serviceFeasibilityLocation.setIDs(iDs);
        parts.setLocation(serviceFeasibilityLocation);
    }

    private static void mapLineItemServiceSpecification(LineItem item, ServiceFeasibilityLineItemType
            serviceFeasibilityLineItem) {
        if (CollectionUtils.isEmpty(item.getSpecification())) {
            return;
        }
        ServiceFeasibilityLineItemType.ServiceSpecifications serviceSpecifications = new ServiceFeasibilityLineItemType.ServiceSpecifications();
        for (Specification specification : item.getSpecification()) {
            ServiceSpecType serviceSpecType = new ServiceSpecType();
            ServiceSpecSpecificationType value = new ServiceSpecSpecificationType();
            value.setType(WSUtils.createCodeType(specification.getType()));
            serviceSpecType.setSpecification(value);
            serviceSpecifications.getServiceSpecification().add(serviceSpecType);
        }
        serviceFeasibilityLineItem.setServiceSpecifications(serviceSpecifications);
    }

    private static void mapLineItemIDs(LineItem item, ServiceFeasibilityLineItemType serviceFeasibilityLineItem) {
        WSUtils.addIdIfExists(serviceFeasibilityLineItem.getIDs(), item.getEircode(), Constants.CheckServiceAbilityRequest.EIRCODE);
        WSUtils.addIdIfExists(serviceFeasibilityLineItem.getIDs(), item.getCli(), Constants.CheckServiceAbilityRequest.CLI);
    }

}
