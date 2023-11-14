package ie.vodafone.dxl.checkservicefeasibility.mappers;

import com.vodafone.group.schema.common.v1.BaseComponentType;
import com.vodafone.group.schema.common.v1.InfoComponentType;
import com.vodafone.group.schema.common.v1.PostalAddressWithLocationType;
import com.vodafone.group.schema.common.v1.SpecificationType;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMRequestType;
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
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.Category;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.LineItemCategory;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.LineItemRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.LineItemResponse;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.ServiceSpecification;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.SpecificationRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.SpecificationResponse;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.SubSpecification;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.servicefeasibility.SubSpecificationCategory;
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
        mapRolesIDs(serviceFeasibilityVBO, request);
        mapSalesQuoteIDs(request, parts);
        mapLocationIDs(request, parts);
        mapLineItemRequest(request.getLineItem(), parts);
        serviceFeasibilityVBO.setParts(parts);
        serviceFeasibilityRequest.setServiceFeasibilityVBO(serviceFeasibilityVBO);
        return serviceFeasibilityRequest;
    }

    public static CheckServiceFeasibilityResponse mapCheckServiceFeasibilityResponse(ServiceFeasibilityVBOType serviceFeasibilityVBOType) {
        //TODO How will map failures
        CheckServiceFeasibilityResponse response = new CheckServiceFeasibilityResponse();
        if (serviceFeasibilityVBOType == null) {
            return response;
        }
        if (serviceFeasibilityVBOType.getType() != null && Constants.CheckServiceFeasibilityResponse.QA.equalsIgnoreCase(serviceFeasibilityVBOType.getType().getName())) {
            response.setQa(serviceFeasibilityVBOType.getType().getValue());
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
            //TODO check what that mean
//            /ServiceFeasibilityVBO/Parts/LineItems/LineItem/ServiceSpecifications/ServiceSpecification[Name='available']
            if (Constants.ServiceSpecificationResponse.ACTIVE.equalsIgnoreCase(WSUtils.getValueFromTextType(serviceSpecType.getName()))) {
                mappedServiceSpecification.setActive(WSUtils.getValueFromTextType(serviceSpecType.getName()));
            } else if (Constants.ServiceSpecificationResponse.AVAILABLE.equalsIgnoreCase(WSUtils.getValueFromTextType(serviceSpecType.getName()))) {
                mappedServiceSpecification.setAvailable(WSUtils.getValueFromTextType(serviceSpecType.getName()));
            } else if (Constants.ServiceSpecificationResponse.RESERVED.equalsIgnoreCase(WSUtils.getValueFromTextType(serviceSpecType.getName()))) {
                mappedServiceSpecification.setReserved(WSUtils.getValueFromTextType(serviceSpecType.getName()));
            }
            if (serviceSpecType.getType() != null) {
                //TODO doublecheck that
                if (Constants.ServiceSpecificationsResponse.AS_DETAILS.equalsIgnoreCase(serviceSpecType.getType().getValue())) {
                    mappedServiceSpecification.setAsDetails(serviceSpecType.getType().getValue());
                }
                if (serviceSpecType.getSpecification() != null) {
                    SubSpecification specification = new SubSpecification();
                    mapServiceSubSpecificationIDs(specification, serviceSpecType.getSpecification().getIDs());
                    mapServiceSpecificationCategories(specification, serviceSpecType.getSpecification().getCategories());
                    mapServiceSpecificationCharacteristics(specification, serviceSpecType.getSpecification().getCharacteristicsValue());
                    mappedServiceSpecification.setSpecification(specification);

                }
                if (serviceSpecType.getIDs() != null) {
                    mapServiceSpecificationIDs(mappedServiceSpecification, serviceSpecType.getIDs().getID());
                }
            }
        }
        lineItemResponse.setServiceSpecification(mappedServiceSpecificationList);
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
            }
        }
    }

    private static void mapServiceSpecificationCategories(SubSpecification specification, BaseComponentType.Categories categories) {
        if (categories == null || CollectionUtils.isEmpty(categories.getCategory())) {
            return;
        }
        List<SubSpecificationCategory> categoryList = new ArrayList<>();

        for (BaseComponentType.Categories.Category category : categories.getCategory()) {
            SubSpecificationCategory mappedCategory = new SubSpecificationCategory();
            if (Constants.ServiceSpecificationsResponse.ACTION_FLAG.equalsIgnoreCase(category.getName())) {
                mappedCategory.setActionFlag(category.getValue());
            }
            categoryList.add(mappedCategory);
        }
        specification.setCategory(categoryList);
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
        List<LineItemCategory> mappedCategories = new ArrayList<>();
        for (BaseComponentType.Categories.Category category : categoryList) {
            LineItemCategory mappedCategory = new LineItemCategory();
            if (Constants.CheckServiceFeasibilityResponse.ACTION_FLAG.equalsIgnoreCase(category.getName())) {
                mappedCategory.setActionFlag(category.getValue());
            }
        }
        response.setCategory(mappedCategories);
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
                response.setEirCode(value.getValue());
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
            } else if (Constants.LineItemResponseSpecificationCharacteristic.BROADBAND_SERVICE.equalsIgnoreCase(characteristicName)) {
                specification.setBroadBandService(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.LineItemResponseSpecificationCharacteristic.SERVICE_PROVIDER.equalsIgnoreCase(characteristicName)) {
                specification.setServiceProvider(WSUtils.getValueFromTextType(value.getValue()));
            } else if (Constants.LineItemResponseSpecificationCharacteristic.INSITU_FLAG.equalsIgnoreCase(characteristicName)) {
                specification.setInsituFlag(WSUtils.getValueFromTextType(value.getValue()));
            }
        }
    }

    private static void mapCheckServiceFeasibilityDetails(CheckServiceFeasibilityResponse response, IndicatorType
            manualIndicator) {
        if (manualIndicator == null || manualIndicator.getIndicatorString() == null) {
            return;
        }
        response.setIndicator(Boolean.parseBoolean(manualIndicator.getIndicatorString().getValue()));
    }

    private static void mapServiceFeasibilityCategories(CheckServiceFeasibilityResponse
                                                                response, List<BaseComponentType.Categories.Category> categories) {
        if (CollectionUtils.isEmpty(categories)) {
            return;
        }
        List<Category> mappedCategories = new ArrayList<>();
        for (BaseComponentType.Categories.Category category : categories) {
            Category mappedCategory = new Category();
            if (Constants.CheckServiceFeasibilityResponseCategories.SYSTEMS_TO_CALL.equalsIgnoreCase(category.getName())) {
                mappedCategory.setSystemsToCall(category.getValue());
            } else if (Constants.CheckServiceFeasibilityResponseCategories.PENDING_ORDERS.equalsIgnoreCase(category.getName())) {
                mappedCategory.setPendingOrders(category.getValue());
            } else if (Constants.CheckServiceFeasibilityResponseCategories.TOS_FLAG.equalsIgnoreCase(category.getName())) {
                mappedCategory.setTosFlag(category.getValue());
            }
            mappedCategories.add(mappedCategory);
        }
        response.setCategory(mappedCategories);
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
        List<SpecificationType.CharacteristicsValue> characteristicsValue = specificationType.getCharacteristicsValue();
        characteristicsValue.add(WSUtils.createSpecificationCharacteristic(specificationRequest.getNewNetworkTechnology(), Constants.CheckServiceFeasibilityRequest.NEW_NETWORK_TECHNOLOGY));
        characteristicsValue.add(WSUtils.createSpecificationCharacteristic(specificationRequest.getNewNetworkTechnology(), Constants.CheckServiceFeasibilityRequest.OLD_NETWORK_TECHNOLOGY));
        characteristicsValue.add(WSUtils.createSpecificationCharacteristic(specificationRequest.getNewNetworkTechnology(), Constants.CheckServiceFeasibilityRequest.SERVICE_PROVIDER));
        serviceFeasibilityLineItemType.setSpecification(specificationType);


    }

    private static void mapLocationIDs(CheckServiceFeasibilityRequest request, ServiceFeasibilityPartsType parts) {
        ServiceFeasibilityLocationType location = new ServiceFeasibilityLocationType();
        PostalAddressWithLocationType.IDs locationIDs = new PostalAddressWithLocationType.IDs();
        WSUtils.addIdIfExists(locationIDs, request.getLocation().getPremisseId(), Constants.CheckServiceFeasibilityRequest.PREMISES_ID);
        WSUtils.addIdIfExists(locationIDs, request.getLocation().getArdkey(), Constants.CheckServiceFeasibilityRequest.ARD_KEY);
        location.setIDs(locationIDs);
        parts.setLocation(location);
    }

    private static void mapRolesIDs(ServiceFeasibilityVBOType serviceFeasibilityVBO, CheckServiceFeasibilityRequest
            request) {
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
