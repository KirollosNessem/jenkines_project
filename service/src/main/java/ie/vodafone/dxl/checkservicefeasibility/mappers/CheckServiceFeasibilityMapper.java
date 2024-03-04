package ie.vodafone.dxl.checkservicefeasibility.mappers;

import com.vodafone.group.schema.common.v1.BaseComponentType;
import com.vodafone.group.schema.common.v1.CharacteristicsType;
import com.vodafone.group.schema.common.v1.InfoComponentType;
import com.vodafone.group.schema.common.v1.PostalAddressWithLocationType;
import com.vodafone.group.schema.common.v1.SpecificationType;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMRequestType;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMResponseType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.SalesQuoteReferenceType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityLineItemSpecificationType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityLineItemType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityLocationType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityPartsType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityRequestorType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityRolesType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityVBOType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceSpecType;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceFeasibilityRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceFeasibilityResponse;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.ResultStatus;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.LineItemRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.LineItemResponse;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.ServiceSpecification;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.SpecificationRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.SpecificationResponse;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.SubSpecification;
import ie.vodafone.dxl.checkservicefeasibility.utils.Constants;
import ie.vodafone.dxl.checkservicefeasibility.utils.WSUtils;
import ie.vodafone.dxl.utils.common.CollectionUtils;
import ie.vodafone.dxl.utils.common.StringUtils;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.IDType;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.IndicatorType;

import java.util.ArrayList;
import java.util.List;

public class CheckServiceFeasibilityMapper {

    public static CheckServiceFeasibilityVBMRequestType mapCheckServiceFeasibilityRequest(CheckServiceFeasibilityRequest request) {
        CheckServiceFeasibilityVBMRequestType serviceFeasibilityRequest = new CheckServiceFeasibilityVBMRequestType();
        ServiceFeasibilityVBOType serviceFeasibilityVBO = new ServiceFeasibilityVBOType();
        ServiceFeasibilityPartsType parts = new ServiceFeasibilityPartsType();
        if (StringUtils.isNotBlank(request.getStatus())) {
            serviceFeasibilityVBO.setStatus(WSUtils.createCodeType(request.getStatus()));
        }
        mapIDs(serviceFeasibilityVBO, request);
        mapRolesIDs(serviceFeasibilityVBO, request);
        mapSalesQuoteIDs(request, parts);
        mapLocation(request, parts);
        mapLineItemRequest(request.getLineItem(), parts);
        serviceFeasibilityVBO.setParts(parts);
        serviceFeasibilityRequest.setServiceFeasibilityVBO(serviceFeasibilityVBO);
        return serviceFeasibilityRequest;
    }

    public static CheckServiceFeasibilityResponse mapCheckServiceFeasibilityResponse(CheckServiceFeasibilityVBMResponseType feasibilityVBMResponseType, ResultStatus resultStatus) {
        CheckServiceFeasibilityResponse response = new CheckServiceFeasibilityResponse();
        if (resultStatus != null && StringUtils.isNotBlank(resultStatus.getName())) {
            response.setName(resultStatus.getName());
        }
        if (feasibilityVBMResponseType == null || feasibilityVBMResponseType.getServiceFeasibilityVBO() == null) {
            return response;
        }
        ServiceFeasibilityVBOType serviceFeasibilityVBOType = feasibilityVBMResponseType.getServiceFeasibilityVBO();
        response.setDesc(WSUtils.getValueFromTextType(serviceFeasibilityVBOType.getDesc()));
        if (serviceFeasibilityVBOType.getType() != null) {
            mapResponseType(serviceFeasibilityVBOType, response);
        }
        if (serviceFeasibilityVBOType.getCategories() != null) {
            mapServiceFeasibilityCategories(response, serviceFeasibilityVBOType.getCategories().getCategory());
        }
        if (serviceFeasibilityVBOType.getDetails() != null) {
            mapCheckServiceFeasibilityDetails(response, serviceFeasibilityVBOType.getDetails().getManualIndicator());
        }
        if (serviceFeasibilityVBOType.getParts() != null) {
            mapLineItemsResponse(response, serviceFeasibilityVBOType.getParts().getLineItems());
        }
        if (serviceFeasibilityVBOType.getIDs() != null) {
            mapServiceFeasibilityResponseIDs(response, serviceFeasibilityVBOType.getIDs().getID());
        }
        return response;
    }

    private static void mapResponseType(ServiceFeasibilityVBOType serviceFeasibilityVBOType, CheckServiceFeasibilityResponse response) {
        String name = serviceFeasibilityVBOType.getType().getName();
        if (Constants.CheckServiceFeasibilityResponse.QA.equalsIgnoreCase(name)) {
            response.setQa(serviceFeasibilityVBOType.getType().getValue());
        } else if (Constants.CheckServiceFeasibilityResponse.ELIGIBILITY_CHECK.equalsIgnoreCase(name)) {
            response.setEligibilityCheck(serviceFeasibilityVBOType.getType().getValue());
        }
    }

    private static void mapServiceFeasibilityResponseIDs(CheckServiceFeasibilityResponse response, List<IDType> idTypeList) {
        if (idTypeList == null) {
            return;
        }
        for (IDType idType : idTypeList) {
            if (Constants.CheckServiceFeasibilityResponse.QAORDER_ID.equalsIgnoreCase(idType.getSchemeName())) {
                response.setQaOrderId(idType.getValue());
            } else if (Constants.CheckServiceFeasibilityResponse.LEORDER_ID.equalsIgnoreCase(idType.getSchemeName())) {
                response.setLeOrderId(idType.getValue());
            }
        }
    }

    private static void mapLineItemsResponse(CheckServiceFeasibilityResponse response, ServiceFeasibilityPartsType.LineItems lineItem) {
        if (lineItem == null || CollectionUtils.isEmpty(lineItem.getLineItem())) {
            return;
        }
        List<LineItemResponse> lineItemResponseList = new ArrayList<>();
        List<ServiceFeasibilityLineItemType> lineItemList = lineItem.getLineItem();
        for (ServiceFeasibilityLineItemType serviceFeasibilityLineItemType : lineItemList) {
            LineItemResponse lineItemResponse = new LineItemResponse();
            lineItemResponse.setStatus(WSUtils.getValueFromCodeType(serviceFeasibilityLineItemType.getStatus()));
            lineItemResponse.setToDate(WSUtils.getValidityPeriodToDate(serviceFeasibilityLineItemType.getValidityPeriod()));
            if (serviceFeasibilityLineItemType.getReason() != null) {
                lineItemResponse.setReasonCode(WSUtils.getValueFromCodeType(serviceFeasibilityLineItemType.getReason().getCode()));
                lineItemResponse.setReason(WSUtils.getValueFromTextType(serviceFeasibilityLineItemType.getReason().getText()));
            }
            if (serviceFeasibilityLineItemType.getSpecification() != null) {
                mapLineItemSpecification(lineItemResponse, serviceFeasibilityLineItemType.getSpecification());
            }
            if (serviceFeasibilityLineItemType.getIDs() != null) {
                mapLineItemResponseIDs(lineItemResponse, serviceFeasibilityLineItemType.getIDs().getID());
            }
            if (serviceFeasibilityLineItemType.getCategories() != null) {
                mapLineItemCategories(lineItemResponse, serviceFeasibilityLineItemType.getCategories().getCategory());
            }
            if (serviceFeasibilityLineItemType.getServiceSpecifications() != null) {
                mapServiceSpecifications(lineItemResponse, serviceFeasibilityLineItemType.getServiceSpecifications().getServiceSpecification());
            }
            lineItemResponseList.add(lineItemResponse);
        }
        response.setLineItem(lineItemResponseList);
    }

    private static void mapServiceSpecifications(LineItemResponse lineItemResponse, List<ServiceSpecType> specTypeList) {
        if (CollectionUtils.isEmpty(specTypeList)) {
            return;
        }
        List<ServiceSpecification> mappedServiceSpecificationList = new ArrayList<>();
        for (ServiceSpecType serviceSpecType : specTypeList) {
            ServiceSpecification mappedServiceSpecification = new ServiceSpecification();
            if (Constants.ServiceSpecificationResponse.ACTIVE.equalsIgnoreCase(WSUtils.getValueFromTextType(serviceSpecType.getName()))) {
                mappedServiceSpecification.setActive(WSUtils.getValueFromTextType(serviceSpecType.getName()));
            } else if (Constants.ServiceSpecificationResponse.AVAILABLE.equalsIgnoreCase(WSUtils.getValueFromTextType(serviceSpecType.getName()))) {
                mappedServiceSpecification.setAvailable(WSUtils.getValueFromTextType(serviceSpecType.getName()));
            } else if (Constants.ServiceSpecificationResponse.RESERVED.equalsIgnoreCase(WSUtils.getValueFromTextType(serviceSpecType.getName()))) {
                mappedServiceSpecification.setReserved(WSUtils.getValueFromTextType(serviceSpecType.getName()));
            }
            if(serviceSpecType.getDesc() != null){
                mappedServiceSpecification.setDesc(WSUtils.getValueFromTextType(serviceSpecType.getDesc()));
            }
            if (serviceSpecType.getType() != null) {
                if (Constants.ServiceSpecificationsResponse.AS_DETAILS.equalsIgnoreCase(serviceSpecType.getType().getValue())) {
                    mappedServiceSpecification.setAsDetails(serviceSpecType.getType().getValue());
                }
                if (serviceSpecType.getIDs() != null) {
                    mapServiceSpecificationIDs(mappedServiceSpecification, serviceSpecType.getIDs().getID());
                }
            }
            mapServiceSpecifications(serviceSpecType, mappedServiceSpecification);
            mappedServiceSpecificationList.add(mappedServiceSpecification);
        }
        lineItemResponse.setServiceSpecification(mappedServiceSpecificationList);
    }

    private static void mapServiceSpecifications(ServiceSpecType serviceSpecType, ServiceSpecification mappedServiceSpecification) {
        if (serviceSpecType.getSpecification() == null) {
            return;
        }
        SubSpecification specification = new SubSpecification();
        mapServiceSubSpecificationIDs(specification, serviceSpecType.getSpecification().getIDs());
        mapServiceSpecificationCategories(specification, serviceSpecType.getSpecification().getCategories());
        mapServiceSpecificationCharacteristics(specification, serviceSpecType.getSpecification().getCharacteristicsValue());
        mappedServiceSpecification.setSpecification(specification);
    }

    private static void mapServiceSpecificationIDs(ServiceSpecification mappedServiceSpecification, List<IDType> idTypeList) {
        if (CollectionUtils.isEmpty(idTypeList)) {
            return;
        }
        for (IDType idType : idTypeList) {
            if (Constants.ServiceSpecificationResponse.SERVICE.equalsIgnoreCase(idType.getSchemeName())) {
                mappedServiceSpecification.setService(idType.getValue());
            } else if (Constants.ServiceSpecificationResponse.ONT_ACTIVE.equalsIgnoreCase(idType.getSchemeName())) {
                mappedServiceSpecification.setOntActive(idType.getValue());
            } else if (Constants.ServiceSpecificationResponse.LINE_ID.equalsIgnoreCase(idType.getSchemeName())) {
                mappedServiceSpecification.setOntActive(idType.getValue());
            } else if (Constants.ServiceSpecificationResponse.INSTALLATION_TYPE.equalsIgnoreCase(idType.getSchemeName())) {
                mappedServiceSpecification.setOntActive(idType.getValue());
            }
        }
    }

    private static void mapServiceSpecificationCharacteristics(SubSpecification specification, List<SpecificationType.CharacteristicsValue> characteristicsValueList) {
        if (CollectionUtils.isEmpty(characteristicsValueList)) {
            return;
        }
        for (SpecificationType.CharacteristicsValue value : characteristicsValueList) {
            if (Constants.ServiceSpecificationResponse.SERVICE.equalsIgnoreCase(value.getCharacteristicName())) {
                specification.setService(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.ServiceSpecificationResponse.ONT_TYPE.equalsIgnoreCase(value.getCharacteristicName())) {
                specification.setOntType(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.ServiceSpecificationResponse.INSTALLATION_TYPE.equalsIgnoreCase(value.getCharacteristicName())) {
                specification.setInstallationType(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.ServiceSpecificationResponse.TAG.equalsIgnoreCase(value.getCharacteristicName())) {
                specification.setTag(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.ServiceSpecificationResponse.CSID.equalsIgnoreCase(value.getCharacteristicName())) {
                specification.setCsid(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.ServiceSpecificationResponse.LINE_ID.equalsIgnoreCase(value.getCharacteristicName())) {
                specification.setLineId(WSUtils.getValueFromTextType(value.getValue()));
            }
        }
    }

    private static void mapServiceSpecificationCategories(SubSpecification specification, BaseComponentType.Categories categories) {
        if (categories == null || CollectionUtils.isEmpty(categories.getCategory())) {
            return;
        }
        for (BaseComponentType.Categories.Category category : categories.getCategory()) {
            if (Constants.ServiceSpecificationsResponse.ACTION_FLAG.equalsIgnoreCase(category.getName())) {
                specification.setActionFlag(category.getValue());
            }
        }
    }

    private static void mapServiceSubSpecificationIDs(SubSpecification specification, InfoComponentType.IDs iDs) {
        if (iDs == null || CollectionUtils.isEmpty(iDs.getID())) {
            return;
        }
        for (IDType idType : iDs.getID()) {
            if (Constants.ServiceSpecificationsResponse.AS_CODE.equalsIgnoreCase(idType.getSchemeName())) {
                specification.setAsCode(idType.getValue());
            } else if (Constants.ServiceSpecificationsResponse.TELE_NO.equalsIgnoreCase(idType.getSchemeName())) {
                specification.setTeleNo(idType.getValue());
            }
        }
    }

    private static void mapLineItemCategories(LineItemResponse response, List<BaseComponentType.Categories.Category> categoryList) {
        if (CollectionUtils.isEmpty(categoryList)) {
            return;
        }
        for (BaseComponentType.Categories.Category category : categoryList) {
            if (Constants.CheckServiceFeasibilityResponse.ACTION_FLAG.equalsIgnoreCase(category.getName())) {
                response.setActionFlag(category.getValue());
            } else if (Constants.CheckServiceFeasibilityResponse.ELIGIBILITY_STATUS.equalsIgnoreCase(category.getName())) {
                response.setEligibilityStatus(category.getValue());
            } else if (Constants.CheckServiceFeasibilityResponse.INELIGIBILITY_DESCRIPTION.equalsIgnoreCase(category.getName())) {
                response.setIneligibilityDescription(category.getValue());
            }
        }
    }

    private static void mapLineItemResponseIDs(LineItemResponse response, List<IDType> idTypeList) {
        if (CollectionUtils.isEmpty(idTypeList)) {
            return;
        }
        for (IDType value : idTypeList) {
            if (Constants.CheckServiceFeasibilityResponse.CLI.equalsIgnoreCase(value.getSchemeName())) {
                response.setCli(value.getValue());
            } else if (Constants.CheckServiceFeasibilityResponse.EIRCODE.equalsIgnoreCase(value.getSchemeName())) {
                response.setEirCode(value.getValue());
            } else if (Constants.CheckServiceFeasibilityResponse.ADDRESS_ID.equalsIgnoreCase(value.getSchemeName())) {
                response.setAddressId(value.getValue());
            } else if (Constants.CheckServiceFeasibilityResponse.ORDER_ID.equalsIgnoreCase(value.getSchemeName())) {
                response.setOrderId(value.getValue());
            } else if (Constants.CheckServiceFeasibilityResponse.VALID.equalsIgnoreCase(value.getSchemeName())) {
                response.setValid(value.getValue());
            }
        }
    }

    private static void mapLineItemSpecification(LineItemResponse lineItemResponse, ServiceFeasibilityLineItemSpecificationType specificationType) {
        SpecificationResponse specification = new SpecificationResponse();
        specification.setType(WSUtils.getValueFromCodeType(specificationType.getType()));
        mapLineItemSpecificationCharacteristics(specification, specificationType.getCharacteristicsValue());
        lineItemResponse.setSpecification(specification);
    }

    private static void mapLineItemSpecificationCharacteristics(SpecificationResponse specification, List<SpecificationType.CharacteristicsValue> characteristicsValue) {
        if (CollectionUtils.isEmpty(characteristicsValue)) {
            return;
        }
        for (SpecificationType.CharacteristicsValue value : characteristicsValue) {
            String characteristicName = value.getCharacteristicName();
            if (Constants.LineItemResponseSpecificationCharacteristic.PORT_AVAILABLE_FLAG.equalsIgnoreCase(characteristicName)) {
                specification.setPortAvailableFlag(Boolean.parseBoolean(WSUtils.getValueFromTextType(value.getValue())));
            } else if (Constants.LineItemResponseSpecificationCharacteristic.ADDITIONAL_BUILD_INDICATOR.equalsIgnoreCase(characteristicName)) {
                specification.setAdditionalBuildIndicator(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.LineItemResponseSpecificationCharacteristic.DOES_PENDING_ORDER_EXIST.equalsIgnoreCase(characteristicName)) {
                specification.setDoesPendingOrderExist(Boolean.parseBoolean(WSUtils.getValueFromTextType(value.getValue())));
            } else if (Constants.LineItemResponseSpecificationCharacteristic.FEASIBILITY_CHECK.equalsIgnoreCase(characteristicName)) {
                specification.setFeasibilityCheck(Boolean.parseBoolean(WSUtils.getValueFromTextType(value.getValue())));
            } else if (Constants.LineItemResponseSpecificationCharacteristic.OPEN_ORDER_EXISTS.equalsIgnoreCase(characteristicName)) {
                specification.setOpenOrderExists(Boolean.parseBoolean(WSUtils.getValueFromTextType(value.getValue())));
            } else if (Constants.LineItemResponseSpecificationCharacteristic.ACTIVE_ASSET_EXISTS.equalsIgnoreCase(characteristicName)) {
                specification.setActiveAssetExists(Boolean.parseBoolean(WSUtils.getValueFromTextType(value.getValue())));
            } else if (Constants.LineItemResponseSpecificationCharacteristic.BROADBAND_SERVICE.equalsIgnoreCase(characteristicName)) {
                specification.setBroadBandService(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.LineItemResponseSpecificationCharacteristic.SERVICE_PROVIDER.equalsIgnoreCase(characteristicName)) {
                specification.setServiceProvider(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.LineItemResponseSpecificationCharacteristic.INSITU_FLAG.equalsIgnoreCase(characteristicName)) {
                specification.setInsituFlag(WSUtils.getValueFromTextType(value.getValue()));
            }
        }
    }

    private static void mapCheckServiceFeasibilityDetails(CheckServiceFeasibilityResponse response, IndicatorType manualIndicator) {
        if (manualIndicator == null || manualIndicator.isIndicator() == null) {
            return;
        }
        response.setIndicator(manualIndicator.isIndicator());
    }

    private static void mapServiceFeasibilityCategories(CheckServiceFeasibilityResponse response, List<BaseComponentType.Categories.Category> categories) {
        if (CollectionUtils.isEmpty(categories)) {
            return;
        }
        for (BaseComponentType.Categories.Category category : categories) {
            if (Constants.CheckServiceFeasibilityResponseCategories.SYSTEMS_TO_CALL.equalsIgnoreCase(category.getName())) {
                response.setSystemsToCall(category.getValue());
            } else if (Constants.CheckServiceFeasibilityResponseCategories.PENDING_ORDERS.equalsIgnoreCase(category.getName())) {
                response.setPendingOrders(category.getValue());
            } else if (Constants.CheckServiceFeasibilityResponseCategories.TOS_FLAG.equalsIgnoreCase(category.getName())) {
                response.setTosFlag(category.getValue());
            }
        }
    }

    private static void mapLineItemRequest(List<LineItemRequest> lineItem, ServiceFeasibilityPartsType parts) {
        if (CollectionUtils.isEmpty(lineItem)) {
            return;
        }
        ServiceFeasibilityPartsType.LineItems lineItems = new ServiceFeasibilityPartsType.LineItems();
        List<ServiceFeasibilityLineItemType> lineItemTypeList = lineItems.getLineItem();
        for (LineItemRequest lineItemRequest : lineItem) {
            ServiceFeasibilityLineItemType serviceFeasibilityLineItemType = new ServiceFeasibilityLineItemType();
            serviceFeasibilityLineItemType.setType(WSUtils.createCodeType(lineItemRequest.getType()));
            mapLineItemRequestSpecification(lineItemRequest.getSpecification(), serviceFeasibilityLineItemType);
            mapLineItemRequestIDs(lineItemRequest, serviceFeasibilityLineItemType);
            lineItemTypeList.add(serviceFeasibilityLineItemType);
        }
        parts.setLineItems(lineItems);

    }

    private static void mapLineItemRequestIDs(LineItemRequest lineItemRequest, ServiceFeasibilityLineItemType
            serviceFeasibilityLineItemType) {
        InfoComponentType.IDs iDs = new InfoComponentType.IDs();
        WSUtils.addIdIfExists(iDs, lineItemRequest.getAssignedProductId(), Constants.CheckServiceFeasibilityRequest.ASSIGNED_PRODUCT_ID);
        serviceFeasibilityLineItemType.setIDs(iDs);
    }

    private static void mapLineItemRequestSpecification(SpecificationRequest specificationRequest, ServiceFeasibilityLineItemType serviceFeasibilityLineItemType) {
        if (specificationRequest == null) {
            return;
        }
        ServiceFeasibilityLineItemSpecificationType specificationType = new ServiceFeasibilityLineItemSpecificationType();
        specificationType.setType(WSUtils.createCodeType(specificationRequest.getType()));
        List<SpecificationType.CharacteristicsValue> characteristicsValue = specificationType.getCharacteristicsValue();
        characteristicsValue.add(WSUtils.createSpecificationCharacteristic(specificationRequest.getNewNetworkTechnology(), Constants.CheckServiceFeasibilityRequest.NEW_NETWORK_TECHNOLOGY));
        characteristicsValue.add(WSUtils.createSpecificationCharacteristic(specificationRequest.getOldNetworkTechnology(), Constants.CheckServiceFeasibilityRequest.OLD_NETWORK_TECHNOLOGY));
        characteristicsValue.add(WSUtils.createSpecificationCharacteristic(specificationRequest.getServiceProvider(), Constants.CheckServiceFeasibilityRequest.SERVICE_PROVIDER));
        characteristicsValue.add(WSUtils.createSpecificationCharacteristic(specificationRequest.getTransferRequest(), Constants.CheckServiceFeasibilityRequest.TRANSFER_REQUEST));
        serviceFeasibilityLineItemType.setSpecification(specificationType);
    }

    private static void mapLocation(CheckServiceFeasibilityRequest request, ServiceFeasibilityPartsType parts) {
        if (request.getLocation() == null) {
            return;
        }
        ServiceFeasibilityLocationType location = new ServiceFeasibilityLocationType();
        PostalAddressWithLocationType.IDs locationIDs = new PostalAddressWithLocationType.IDs();
        WSUtils.addIdIfExists(locationIDs, request.getLocation().getPremisesId(), Constants.CheckServiceFeasibilityRequest.PREMISES_ID);
        WSUtils.addIdIfExists(locationIDs, request.getLocation().getArdkey(), Constants.CheckServiceFeasibilityRequest.ARD_KEY);
        WSUtils.addIdIfExists(locationIDs, request.getLocation().getVmLocationId(), Constants.CheckServiceFeasibilityRequest.VM_LOCATION_ID);
        WSUtils.addIdIfExists(locationIDs, request.getLocation().getNbiEircode(), Constants.CheckServiceFeasibilityRequest.NBI_EIRCODE);
        location.setIDs(locationIDs);

        if(StringUtils.isNotBlank(request.getLocation().getAddressId())) {
            CharacteristicsType characteristicsType = new CharacteristicsType();
            WSUtils.addCharacteristic(characteristicsType.getCharacteristicsValue(), request.getLocation().getAddressId(), Constants.CheckServiceFeasibilityRequest.ADDRESS_ID, null);
            location.setCharacteristics(characteristicsType);
        }

        parts.setLocation(location);
    }

    private static void mapIDs(ServiceFeasibilityVBOType serviceFeasibilityVBO, CheckServiceFeasibilityRequest request) {
        if (StringUtils.isBlank(request.getTransferReservation()) && StringUtils.isBlank(request.getLineId())) {
            return;
        }
        InfoComponentType.IDs ids = new InfoComponentType.IDs();
        WSUtils.addIdIfExists(ids, request.getTransferReservation(), Constants.CheckServiceFeasibilityRequest.TRANSFER_Reservation);
        WSUtils.addIdIfExists(ids, request.getLineId(), Constants.CheckServiceFeasibilityRequest.LINE_ID);
        serviceFeasibilityVBO.setIDs(ids);
    }

        private static void mapRolesIDs(ServiceFeasibilityVBOType serviceFeasibilityVBO, CheckServiceFeasibilityRequest request) {
        if (StringUtils.isBlank(request.getUan())) {
            return;
        }
        ServiceFeasibilityRolesType rolesType = new ServiceFeasibilityRolesType();
        ServiceFeasibilityRequestorType requestor = new ServiceFeasibilityRequestorType();
        InfoComponentType.IDs requestorIDs = new InfoComponentType.IDs();
        WSUtils.addIdIfExists(requestorIDs, request.getUan(), Constants.CheckServiceFeasibilityRequest.UAN);
        requestor.setIDs(requestorIDs);
        rolesType.setRequestor(requestor);
        serviceFeasibilityVBO.setRoles(rolesType);
    }

    private static void mapSalesQuoteIDs(CheckServiceFeasibilityRequest request, ServiceFeasibilityPartsType parts) {
        SalesQuoteReferenceType salesQuote = new SalesQuoteReferenceType();
        InfoComponentType.IDs salesQuoteIDs = new InfoComponentType.IDs();
        WSUtils.addIdIfExists(salesQuoteIDs, request.getProductOrderID(), Constants.CheckServiceFeasibilityRequest.PRODUCT_ORDER_ID);
        WSUtils.addIdIfExists(salesQuoteIDs, request.getBasketId(), Constants.CheckServiceFeasibilityRequest.BASKET_ID);
        WSUtils.addIdIfExists(salesQuoteIDs, request.getOrderNumber(), Constants.CheckServiceFeasibilityRequest.ORDER_NUMBER);
        salesQuote.setIDs(salesQuoteIDs);
        parts.setSalesQuote(salesQuote);
    }
}
