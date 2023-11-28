package ie.vodafone.dxl.checkservicefeasibility.mappers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMRequestType;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMResponseType;
import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityVBOType;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceAbilityRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceAbilityResponse;
import ie.vodafone.dxl.checkservicefeasibility.soapclient.CheckServiceFeasibilityOsbResponse;
import ie.vodafone.dxl.checkservicefeasibility.utils.MockCreationUtils;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CheckServiceAbilityMapperTest {

    private CheckServiceAbilityRequest serviceAbilityRequest;
    private CheckServiceAbilityResponse serviceAbilityResponse;

    @BeforeAll
    void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        InputStream inputStream = new FileInputStream("src/test/resources/check-service-ability-request.json");
        serviceAbilityRequest = objectMapper.readValue(inputStream, new TypeReference<>() {
        });
        inputStream = new FileInputStream("src/test/resources/check-service-ability-response.json");
        serviceAbilityResponse = objectMapper.readValue(inputStream, new TypeReference<>() {
        });
    }

    @Test
    void mapCheckServiceAbilityRequest() {
        CheckServiceFeasibilityVBMRequestType requestType = MockCreationUtils.getCheckServiceAbilityRequest();
        CheckServiceFeasibilityVBMRequestType actualRequest = CheckServiceAbilityMapper.mapCheckServiceAbilityRequest(serviceAbilityRequest);
        ObjectMapper objectMapper = new ObjectMapper();
        assertAll("Should return CheckServiceFeasibilityVBMRequestType",
                () -> assertNotNull(actualRequest, "Null Request"),
                () -> assertEquals(objectMapper.writeValueAsString(requestType), objectMapper.writeValueAsString(actualRequest)));
    }

    @Test
    void mapCheckServiceAbilityResponse() {
        ServiceFeasibilityVBOType serviceFeasibilityVBOType = MockCreationUtils.getCheckServiceAbilityResponse();
        CheckServiceFeasibilityOsbResponse osbResponse = new CheckServiceFeasibilityOsbResponse();
        CheckServiceFeasibilityVBMResponseType vbmResponseType = new CheckServiceFeasibilityVBMResponseType();
        osbResponse.setOsbResponse(vbmResponseType);
        vbmResponseType.setServiceFeasibilityVBO(serviceFeasibilityVBOType);
        CheckServiceAbilityResponse actualResponse = CheckServiceAbilityMapper.mapCheckServiceAbilityResponse(vbmResponseType, null);
        ObjectMapper objectMapper = new ObjectMapper();
        assertAll("Should return serviceFeasibilityVBOType",
                () -> assertNotNull(actualResponse, "Null Response"),
                () -> assertEquals(objectMapper.writeValueAsString(serviceAbilityResponse), objectMapper.writeValueAsString(actualResponse)));
    }
}