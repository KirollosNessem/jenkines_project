package ie.vodafone.dxl.checkservicefeasibility.utils;

import com.vodafone.group.schema.common.v1.BaseComponentType;
import com.vodafone.group.schema.common.v1.InfoComponentType;
import com.vodafone.group.schema.common.v1.PostalAddressWithLocationType;
import com.vodafone.group.schema.common.v1.SpecificationType;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMRequestType;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMResponseType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.SalesQuoteReferenceType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityDetailsType;
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
import ie.vodafone.dxl.checkservicefeasibility.dto.QueryAncillaryServicesResponse;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.IDType;
import un.unece.uncefact.documentation.standard.corecomponenttype._2.IndicatorType;

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

    public static ServiceFeasibilityVBOType getQueryAncillaryServicesOSBResponse() {
        ServiceFeasibilityVBOType vboType = new ServiceFeasibilityVBOType();
        vboType.setIDs(new InfoComponentType.IDs());
        WSUtils.addIdIfExists(vboType.getIDs(), "343327", "QAOrderID");
        vboType.setParts(new ServiceFeasibilityPartsType());
        vboType.getParts().setSpecification(new ServiceFeasibilitySpecificationType());
        List<SpecificationType.CharacteristicsValue> characteristicsValue = vboType.getParts().getSpecification().getCharacteristicsValue();
        WSUtils.addSpecificationCharacteristicValueIfExists(characteristicsValue, "N", "TOS_FLAG");
        vboType.getParts().setLineItems(new ServiceFeasibilityPartsType.LineItems());
        List<ServiceFeasibilityLineItemType> lineItemTypeList = vboType.getParts().getLineItems().getLineItem();
        ServiceFeasibilityLineItemType lineItemType = new ServiceFeasibilityLineItemType();
        lineItemType.setIDs(new InfoComponentType.IDs());
        WSUtils.addIdIfExists(lineItemType.getIDs(), "35314534540", "CLI");
        lineItemType.setType(WSUtils.createCodeType("CLI"));
        lineItemType.setCategories(new BaseComponentType.Categories());
        List<BaseComponentType.Categories.Category> category = lineItemType.getCategories().getCategory();
        category.add(WSUtils.createCategory("L", "ACTION_FLAG", "name"));
        lineItemType.setStatus(WSUtils.createCodeType("C"));
        lineItemType.setSpecification(new ServiceFeasibilityLineItemSpecificationType());
        lineItemType.getSpecification().setType(WSUtils.createCodeType("FTC"));
        WSUtils.addSpecificationCharacteristicValueIfExists(lineItemType.getSpecification().getCharacteristicsValue(), "FTC", "BROADBAND_SERVICE");
        lineItemTypeList.add(lineItemType);
        return vboType;
    }

    public static CheckServiceFeasibilityVBMRequestType getCheckServiceFeasibilityRequest() {
        CheckServiceFeasibilityVBMRequestType requestType = new CheckServiceFeasibilityVBMRequestType();
        ServiceFeasibilityVBOType vboType = new ServiceFeasibilityVBOType();
        vboType.setStatus(WSUtils.createCodeType("CheckFeasibility"));
        vboType.setParts(new ServiceFeasibilityPartsType());
        vboType.getParts().setSalesQuote(new SalesQuoteReferenceType());
        InfoComponentType.IDs salesQuoteIds = new InfoComponentType.IDs();
        WSUtils.addIdIfExists(salesQuoteIds, "UFE_00046478", "BasketID");
        WSUtils.addIdIfExists(salesQuoteIds, "UFE_00046478", "OrderNumber");
        vboType.getParts().getSalesQuote().setIDs(salesQuoteIds);
        vboType.getParts().setLocation(new ServiceFeasibilityLocationType());
        vboType.getParts().getLocation().setIDs(new PostalAddressWithLocationType.IDs());
        PostalAddressWithLocationType.IDs iDs = vboType.getParts().getLocation().getIDs();
        WSUtils.addIdIfExists(iDs, "P100000079", "PremisesId");
        WSUtils.addIdIfExists(iDs, "P100000079", "ARD_KEY");
        requestType.setServiceFeasibilityVBO(vboType);
        return requestType;
    }

    public static CheckServiceFeasibilityVBMResponseType getCheckServiceFeasibilityResponse() {
        CheckServiceFeasibilityVBMResponseType responseType = new CheckServiceFeasibilityVBMResponseType();
        ServiceFeasibilityVBOType vboType = new ServiceFeasibilityVBOType();
        vboType.setType(WSUtils.createCodeType("SIRO"));
        vboType.getType().setName("EligibilityCheck");
        vboType.setCategories(new BaseComponentType.Categories());
        WSUtils.addCategoryIfExists(vboType.getCategories(), "EligibilityCheck", "SystemsToCall", "name");
        vboType.setDetails(new ServiceFeasibilityDetailsType());
        vboType.getDetails().setManualIndicator(new IndicatorType());
        vboType.getDetails().getManualIndicator().setIndicator(true);
        vboType.setParts(new ServiceFeasibilityPartsType());
        vboType.getParts().setLineItems(new ServiceFeasibilityPartsType.LineItems());
        List<ServiceFeasibilityLineItemType> lineItemList = vboType.getParts().getLineItems().getLineItem();
        ServiceFeasibilityLineItemType lineItemType = new ServiceFeasibilityLineItemType();
        lineItemType.setCategories(new BaseComponentType.Categories());
        WSUtils.addCategoryIfExists(lineItemType.getCategories(), "Passed", "EligibilityStatus", "name");
        lineItemType.setStatus(WSUtils.createCodeType("OK"));
        lineItemList.add(lineItemType);
        responseType.setServiceFeasibilityVBO(vboType);
        return responseType;

    }
}
