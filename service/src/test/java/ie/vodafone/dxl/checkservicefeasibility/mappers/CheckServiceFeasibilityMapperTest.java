package ie.vodafone.dxl.checkservicefeasibility.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMRequestType;
import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMResponseType;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceFeasibilityRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceFeasibilityResponse;
import ie.vodafone.dxl.checkservicefeasibility.dto.QueryAncillaryServicesRequest;
import ie.vodafone.dxl.checkservicefeasibility.dto.parts.ResultStatus;
import ie.vodafone.dxl.checkservicefeasibility.utils.MockCreationUtils;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CheckServiceFeasibilityMapperTest {

    private CheckServiceFeasibilityRequest request;
    private CheckServiceFeasibilityResponse response;

    @BeforeAll
    void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        InputStream inputStream = new FileInputStream("src/test/resources/check-service-feasibility-request.json");
        request = objectMapper.readValue(inputStream, new TypeReference<>() {
        });
        inputStream = new FileInputStream("src/test/resources/check-service-feasibility-response.json");
        response = objectMapper.readValue(inputStream, new TypeReference<>() {
        });
    }

    @Test
    void mapCheckServiceFeasibilityRequest() {
        CheckServiceFeasibilityVBMRequestType expectedRequest = MockCreationUtils.getCheckServiceFeasibilityRequest();
        CheckServiceFeasibilityVBMRequestType actualRequest = CheckServiceFeasibilityMapper.mapCheckServiceFeasibilityRequest(request);
        ObjectMapper objectMapper = new ObjectMapper();
        assertAll("Should return CheckServiceFeasibilityVBMRequestType",
                () -> assertNotNull(actualRequest, "Null Request"),
                () -> assertEquals(objectMapper.writeValueAsString(expectedRequest), objectMapper.writeValueAsString(actualRequest)));
    }

    @Test
    void mapCheckServiceFeasibilityResponse() {
        CheckServiceFeasibilityVBMResponseType osbResponse = MockCreationUtils.getCheckServiceFeasibilityResponse();
        ResultStatus resultStatus = new ResultStatus();
        resultStatus.setName("OK");
        CheckServiceFeasibilityResponse actualResponse = CheckServiceFeasibilityMapper.mapCheckServiceFeasibilityResponse(osbResponse, resultStatus);
        ObjectMapper objectMapper = new ObjectMapper();
        assertAll("Should return CheckServiceFeasibility Response",
                () -> assertNotNull(actualResponse, "Null Request"),
                () -> assertEquals(objectMapper.writeValueAsString(response), objectMapper.writeValueAsString(actualResponse)));
    }
}
