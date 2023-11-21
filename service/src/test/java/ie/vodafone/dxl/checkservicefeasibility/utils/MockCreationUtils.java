package ie.vodafone.dxl.checkservicefeasibility.utils;

import com.vodafone.group.schema.common.v1.InfoComponentType;
import com.vodafone.group.schema.common.v1.PostalAddressWithLocationType;
import com.vodafone.group.schema.common.v1.SpecificationType;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMRequestType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityLineItemSpecificationType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityLineItemType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityLocationType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityPartsType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityRequestorType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityRolesType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilitySpecificationType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityVBOType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceSpecSpecificationType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceSpecType;

import java.util.List;

public class MockCreationUtils {

    public static CheckServiceFeasibilityVBMRequestType getCheckServiceAbilityRequest() {
        CheckServiceFeasibilityVBMRequestType requestType = new CheckServiceFeasibilityVBMRequestType();
        requestType.setServiceFeasibilityVBO(new ServiceFeasibilityVBOType());
        requestType.getServiceFeasibilityVBO().setStatus(WSUtils.createCodeType("CheckServiceability"));
        requestType.getServiceFeasibilityVBO().setParts(new ServiceFeasibilityPartsType());
        requestType.getServiceFeasibilityVBO().getParts().setLocation(new ServiceFeasibilityLocationType());
        PostalAddressWithLocationType.IDs locationIds = new PostalAddressWithLocationType.IDs();
        WSUtils.addIdIfExists(locationIds, "P100000035", "PremisesId");
        requestType.getServiceFeasibilityVBO().getParts().getLocation().setIDs(locationIds);
        requestType.getServiceFeasibilityVBO().getParts().setLineItems(new ServiceFeasibilityPartsType.LineItems());
        List<ServiceFeasibilityLineItemType> itemTypeList = requestType.getServiceFeasibilityVBO().getParts().getLineItems().getLineItem();
        ServiceFeasibilityLineItemType serviceFeasibilityLineItemType = new ServiceFeasibilityLineItemType();
        itemTypeList.add(serviceFeasibilityLineItemType);
        serviceFeasibilityLineItemType.setType(WSUtils.createCodeType("Fixed"));
        serviceFeasibilityLineItemType.setServiceSpecifications(new ServiceFeasibilityLineItemType.ServiceSpecifications());
        List<ServiceSpecType> serviceSpecification = serviceFeasibilityLineItemType.getServiceSpecifications().getServiceSpecification();
        serviceSpecification.add(new ServiceSpecType());
        serviceSpecification.get(0).setSpecification(new ServiceSpecSpecificationType());
        serviceSpecification.get(0).getSpecification().setType(WSUtils.createCodeType("ALL"));
        return requestType;
    }

    public static ServiceFeasibilityVBOType getCheckServiceAbilityResponse() {
        ServiceFeasibilityVBOType vboType = new ServiceFeasibilityVBOType();
        vboType.setParts(new ServiceFeasibilityPartsType());
        vboType.getParts().setLineItems(new ServiceFeasibilityPartsType.LineItems());
        List<ServiceFeasibilityLineItemType> lineItemTypeList = vboType.getParts().getLineItems().getLineItem();
        ServiceFeasibilityLineItemType lineItemType = new ServiceFeasibilityLineItemType();
        lineItemType.setStatus(WSUtils.createCodeType("Success"));
        lineItemType.setSpecification(new ServiceFeasibilityLineItemSpecificationType());
        lineItemType.getSpecification().getCharacteristicsValue().add(WSUtils.createSpecificationCharacteristic("SIRO", "ServiceProvider"));
        lineItemType.setServiceSpecifications(new ServiceFeasibilityLineItemType.ServiceSpecifications());
        List<ServiceSpecType> serviceSpecification = lineItemType.getServiceSpecifications().getServiceSpecification();
        serviceSpecification.add(new ServiceSpecType());
        ServiceSpecType serviceSpecType = serviceSpecification.get(0);
        serviceSpecType.setName(WSUtils.createTextType("FTTH"));
        serviceSpecType.setSpecification(new ServiceSpecSpecificationType());
        List<SpecificationType.CharacteristicsValue> characteristicsValueList = serviceSpecType.getSpecification().getCharacteristicsValue();
        characteristicsValueList.add(WSUtils.createSpecificationCharacteristic("100", "maxUploadSpeed"));
        characteristicsValueList.add(WSUtils.createSpecificationCharacteristic("1000", "maxDownloadSpeed"));
        characteristicsValueList.add(WSUtils.createSpecificationCharacteristic("true", "IsHighestPriority"));
        characteristicsValueList.add(WSUtils.createSpecificationCharacteristic("true", "canShowTVOffers"));
        characteristicsValueList.add(WSUtils.createSpecificationCharacteristic("LightPulse 500 Plus", "ServiceCode"));
        characteristicsValueList.add(WSUtils.createSpecificationCharacteristic("LightStream 150", "ServiceCode"));
        characteristicsValueList.add(WSUtils.createSpecificationCharacteristic("LightStream 150 Plus", "ServiceCode"));
        characteristicsValueList.add(WSUtils.createSpecificationCharacteristic("LightStream 600 Plus TV", "ServiceCode"));
        lineItemTypeList.add(lineItemType);
        return vboType;
    }

    public static CheckServiceFeasibilityVBMRequestType getQueryAncillaryServicesRequest() {
        CheckServiceFeasibilityVBMRequestType requestType = new CheckServiceFeasibilityVBMRequestType();
        requestType.setServiceFeasibilityVBO(new ServiceFeasibilityVBOType());
        requestType.getServiceFeasibilityVBO().setStatus(WSUtils.createCodeType("QueryAncilliaryServices"));
        requestType.getServiceFeasibilityVBO().setRoles(new ServiceFeasibilityRolesType());
        requestType.getServiceFeasibilityVBO().getRoles().setRequestor(new ServiceFeasibilityRequestorType());
        InfoComponentType.IDs iDs = new InfoComponentType.IDs();
        WSUtils.addIdIfExists(iDs, "12345", "UAN");
        requestType.getServiceFeasibilityVBO().getRoles().getRequestor().setIDs(iDs);
        return requestType;
    }

//    public static ServiceFeasibilityVBOType getQueryAncillaryServicesResponse() {
//        ServiceFeasibilityVBOType vboType = new ServiceFeasibilityVBOType();
//        vboType.setParts(new ServiceFeasibilityPartsType());
//        vboType.getParts().setLineItems(new ServiceFeasibilityPartsType.LineItems());
//        List<ServiceFeasibilityLineItemType> lineItemTypeList = vboType.getParts().getLineItems().getLineItem();
//        ServiceFeasibilityLineItemType lineItemType = new ServiceFeasibilityLineItemType();
//        lineItemType.setStatus(WSUtils.createCodeType("Status"));
//        lineItemTypeList.add(lineItemType);
//    }
}
