package ie.vodafone.dxl.checkservicefeasibility.mappers;

import com.vodafone.group.schema.common.v1.BaseComponentType;
import com.vodafone.group.schema.common.v1.InfoComponentType;
import com.vodafone.group.schema.common.v1.SpecificationType;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMRequestType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityLineItemSpecificationType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityLineItemType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityPartsType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityRequestorType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityRolesType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityVBOType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceSpecSpecificationType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceSpecType;
import ie.vodafone.dxl.checkservicefeasibility.dto.QueryAncillaryServicesRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.QueryAncillaryServicesResponse;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.ResultStatus;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices.Category;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices.LineItem;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices.LineItemSpecification;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices.LineItemSubSpecification;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices.ServiceSpecification;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.queryancillaryservices.Specification;
import ie.vodafone.dxl.checkservicefeasibility.utils.Constants;
import ie.vodafone.dxl.checkservicefeasibility.utils.WSUtils;
import ie.vodafone.dxl.utils.common.CollectionUtils;
import ie.vodafone.dxl.utils.common.StringUtils;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.IDType;

import java.util.ArrayList;
import java.util.List;

public class QueryAncillaryServicesMapper {

    public static CheckServiceFeasibilityVBMRequestType mapCheckServiceFeasibilityRequest(QueryAncillaryServicesRequest request) {
        CheckServiceFeasibilityVBMRequestType serviceFeasibilityRequest = new CheckServiceFeasibilityVBMRequestType();
        ServiceFeasibilityVBOType serviceFeasibilityVBO = new ServiceFeasibilityVBOType();
        if (StringUtils.isNotBlank(request.getStatus())) {
            serviceFeasibilityVBO.setStatus(WSUtils.createCodeType(request.getStatus()));
        }
        if (StringUtils.isNotBlank(request.getUan())) {
            mapRequesterIDs(serviceFeasibilityVBO, request.getUan());
        }
        serviceFeasibilityRequest.setServiceFeasibilityVBO(serviceFeasibilityVBO);
        return serviceFeasibilityRequest;
    }

    public static QueryAncillaryServicesResponse mapCheckServiceFeasibilityResponse(ServiceFeasibilityVBOType serviceFeasibilityVBOType, ResultStatus resultStatus) {
        QueryAncillaryServicesResponse response = new QueryAncillaryServicesResponse();
        if (resultStatus != null && StringUtils.isNotBlank(resultStatus.getName())) {
            response.setName(resultStatus.getName());
        }
        if (serviceFeasibilityVBOType == null) {
            return response;
        }
        if (serviceFeasibilityVBOType.getIDs() != null) {
            mapServiceFeasibilityVBOIDs(response, serviceFeasibilityVBOType.getIDs().getID());
        }
        if (serviceFeasibilityVBOType.getParts() != null) {
            mapServiceFeasibilityVBOParts(response, serviceFeasibilityVBOType.getParts());
        }
        return response;
    }

    private static void mapServiceFeasibilityVBOParts(QueryAncillaryServicesResponse response, ServiceFeasibilityPartsType parts) {
        if (parts.getLineItems() != null) {
            mapLineItems(response, parts.getLineItems());
        }
        if (parts.getSpecification() != null) {
            mapSpecificationCharacteristics(response, parts.getSpecification().getCharacteristicsValue());
        }
    }

    private static void mapLineItems(QueryAncillaryServicesResponse response, ServiceFeasibilityPartsType.LineItems lineItems) {
        if (CollectionUtils.isEmpty(lineItems.getLineItem())) {
            return;
        }
        List<LineItem> lineItemList = new ArrayList<>();
        for (ServiceFeasibilityLineItemType lineItemType : lineItems.getLineItem()) {
            LineItem lineItem = new LineItem();
            lineItem.setStatus(WSUtils.getValueFromCodeType(lineItemType.getStatus()));
            if (lineItemType.getReason() != null) {
                lineItem.setReason(WSUtils.getValueFromTextType(lineItemType.getReason().getText()));
            }
            if (lineItemType.getIDs() != null) {
                mapLineItemIDs(lineItem, lineItemType.getIDs().getID());
            }
            if (lineItemType.getCategories() != null) {
                mapLineItemCategories(lineItem, lineItemType.getCategories().getCategory());
            }
            if (lineItemType.getSpecification() != null) {
                mapLineItemSpecifications(lineItem, lineItemType.getSpecification());
            }
            if (lineItemType.getServiceSpecifications() != null) {
                mapLineItemServiceSpecifications(lineItem, lineItemType.getServiceSpecifications().getServiceSpecification());
            }
            lineItemList.add(lineItem);
        }
        response.setLineItem(lineItemList);
    }

    private static void mapLineItemServiceSpecifications(LineItem lineItem, List<ServiceSpecType> specTypeList) {
        if (CollectionUtils.isEmpty(specTypeList)) {
            return;
        }
        List<ServiceSpecification> serviceSpecificationList = new ArrayList<>();
        for (ServiceSpecType serviceSpecType : specTypeList) {
            ServiceSpecification serviceSpecification = new ServiceSpecification();
            serviceSpecification.setType(WSUtils.getValueFromCodeType(serviceSpecType.getType()));
            if (serviceSpecType.getSpecification() != null) {
                mapLineItemSubSpecifications(serviceSpecification, serviceSpecType.getSpecification());
            }
            serviceSpecificationList.add(serviceSpecification);
        }
        lineItem.setServiceSpecification(serviceSpecificationList);
    }

    private static void mapLineItemSubSpecifications(ServiceSpecification serviceSpecification, ServiceSpecSpecificationType specificationType) {
        LineItemSubSpecification specification = new LineItemSubSpecification();
        if (CollectionUtils.isNotEmpty(specificationType.getCharacteristicsValue())) {
            for (SpecificationType.CharacteristicsValue characteristicsValue : specificationType.getCharacteristicsValue()) {
                if (Constants.QueryAncillaryServicesRequest.ACTION_FLAG.equalsIgnoreCase(characteristicsValue.getCharacteristicName())) {
                    specification.setActionFlag(WSUtils.getValueFromTextType(characteristicsValue.getValue()));
                }
            }
        }
        if (specificationType.getIDs() != null && CollectionUtils.isNotEmpty(specificationType.getIDs().getID())) {
            for (IDType idType : specificationType.getIDs().getID()) {
                if (Constants.QueryAncillaryServicesRequest.TELE_NO.equalsIgnoreCase(idType.getSchemeName())) {
                    specification.setTeleNo(idType.getValue());
                }
            }
        }
        serviceSpecification.setSpecification(specification);
    }

    private static void mapLineItemSpecifications(LineItem lineItem, ServiceFeasibilityLineItemSpecificationType specificationType) {
        LineItemSpecification specification = new LineItemSpecification();
        specification.setType(WSUtils.getValueFromCodeType(specificationType.getType()));
        mapLineItemSpecificationCharacteristics(specification, specificationType.getCharacteristicsValue());
        lineItem.setSpecification(specification);
    }

    private static void mapLineItemSpecificationCharacteristics(LineItemSpecification specification, List<SpecificationType.CharacteristicsValue> valueList) {
        if (CollectionUtils.isEmpty(valueList)) {
            return;
        }
        for (SpecificationType.CharacteristicsValue characteristicsValue : valueList) {
            if (Constants.QueryAncillaryServicesRequest.BROADBAND_SERVICE.equalsIgnoreCase(characteristicsValue.getCharacteristicName())) {
                specification.setBroadbandService(WSUtils.getValueFromTextType(characteristicsValue.getValue()));
            } else if (Constants.QueryAncillaryServicesRequest.MULTICAST_SERVICE.equalsIgnoreCase(characteristicsValue.getCharacteristicName())) {
                specification.setMulticastService(WSUtils.getValueFromTextType(characteristicsValue.getValue()));
            } else if (Constants.QueryAncillaryServicesRequest.INSITU_FLAG.equalsIgnoreCase(characteristicsValue.getCharacteristicName())) {
                specification.setInsituFlag(WSUtils.getValueFromTextType(characteristicsValue.getValue()));
            }
        }
    }

    private static void mapLineItemCategories(LineItem lineItem, List<BaseComponentType.Categories.Category> categoryList) {
        if (CollectionUtils.isEmpty(categoryList)) {
            return;
        }
        List<Category> mappedCategoryList = new ArrayList<>();
        for (BaseComponentType.Categories.Category category : categoryList) {
            Category mappedCategory = new Category();
            if (Constants.QueryAncillaryServicesRequest.ACTION_FLAG.equalsIgnoreCase(category.getName())) {
                mappedCategory.setActionFlag(category.getValue());
            }
            mappedCategoryList.add(mappedCategory);
        }
        lineItem.setCategory(mappedCategoryList);
    }

    private static void mapLineItemIDs(LineItem lineItem, List<IDType> idTypeList) {
        if (CollectionUtils.isEmpty(idTypeList)) {
            return;
        }
        for (IDType idType : idTypeList) {
            if (Constants.QueryAncillaryServicesRequest.CLI.equalsIgnoreCase(idType.getSchemeName())) {
                lineItem.setCli(idType.getValue());
            }
        }
    }

    private static void mapSpecificationCharacteristics(QueryAncillaryServicesResponse response, List<SpecificationType.CharacteristicsValue> characteristicsValueList) {
        if (CollectionUtils.isEmpty(characteristicsValueList)) {
            return;
        }
        Specification specification = new Specification();
        for (SpecificationType.CharacteristicsValue characteristicsValue : characteristicsValueList) {
            if (Constants.QueryAncillaryServicesRequest.PENDING_ORDERS.equalsIgnoreCase(characteristicsValue.getCharacteristicName())) {
                specification.setPendingOrders(WSUtils.getValueFromTextType(characteristicsValue.getValue()));
            } else if (Constants.QueryAncillaryServicesRequest.TOS_FLAG.equalsIgnoreCase(characteristicsValue.getCharacteristicName())) {
                specification.setTosFlag(WSUtils.getValueFromTextType(characteristicsValue.getValue()));
            }
        }
        response.setSpecification(specification);
    }

    private static void mapRequesterIDs(ServiceFeasibilityVBOType serviceFeasibilityVBO, String uan) {
        ServiceFeasibilityRolesType rolesType = new ServiceFeasibilityRolesType();
        ServiceFeasibilityRequestorType requestorType = new ServiceFeasibilityRequestorType();
        InfoComponentType.IDs requesterIds = new InfoComponentType.IDs();
        WSUtils.addIdIfExists(requesterIds, uan, Constants.QueryAncillaryServicesRequest.UAN);
        requestorType.setIDs(requesterIds);
        rolesType.setRequestor(requestorType);
        serviceFeasibilityVBO.setRoles(rolesType);
    }

    private static void mapServiceFeasibilityVBOIDs(QueryAncillaryServicesResponse response, List<IDType> idTypeList) {
        if (CollectionUtils.isEmpty(idTypeList)) {
            return;
        }
        for (IDType idType : idTypeList) {
            if (Constants.QueryAncillaryServicesRequest.QAORDER_ID.equalsIgnoreCase(idType.getSchemeName())) {
                response.setQaOrderId(idType.getValue());
            }
        }
    }
}
