//package ie.vodafone.dxl.checkservicefeasibility.mappers;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.vodafone.group.schema.vbm.service.service_feasibility.v1.CheckServiceFeasibilityVBMRequestType;
//import com.vodafone.group.schema.vbo.service.service_feasibility.v1.ServiceFeasibilityVBOType;
//import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceAbilityRequest;
//import ie.vodafone.dxl.checkservicefeasibility.dto.CheckServiceAbilityResponse;
//import ie.vodafone.dxl.checkservicefeasibility.dto.QueryAncillaryServicesRequest;
//import ie.vodafone.dxl.checkservicefeasibility.dto.QueryAncillaryServicesResponse;
//import ie.vodafone.dxl.checkservicefeasibility.utils.MockCreationUtils;
//import io.quarkus.test.junit.QuarkusTest;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@QuarkusTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class QueryAncillaryServicesMapperTest {
//
//    private QueryAncillaryServicesRequest request;
//    private QueryAncillaryServicesResponse response;
//
//    @BeforeAll
//    void setUp() throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        request = new QueryAncillaryServicesRequest();
//        request.setStatus("QueryAncilliaryServices");
//        request.setUan("12345");
//        InputStream inputStream = new FileInputStream("src/test/resources/query-ancillary-services-response.json");
//        response = objectMapper.readValue(inputStream, new TypeReference<>() {
//        });
//    }
//
//    @Test
//    void mapCheckServiceFeasibilityRequest() {
//        CheckServiceFeasibilityVBMRequestType requestType = MockCreationUtils.getQueryAncillaryServicesRequest();
//        CheckServiceFeasibilityVBMRequestType actualRequest = QueryAncillaryServicesMapper.mapCheckServiceFeasibilityRequest(request);
//        ObjectMapper objectMapper = new ObjectMapper();
//        assertAll("Should return CheckServiceFeasibilityVBMRequestType",
//                () -> assertNotNull(actualRequest, "Null Request"),
//                () -> assertEquals(objectMapper.writeValueAsString(requestType), objectMapper.writeValueAsString(actualRequest)));
//    }
//
////    @Test
////    void mapCheckServiceFeasibilityResponse() {
////        ServiceFeasibilityVBOType serviceFeasibilityVBOType = MockCreationUtils.getQueryAncillaryServicesResponse();
////        CheckServiceAbilityResponse actualResponse = QueryAncillaryServicesMapper.mapCheckServiceFeasibilityResponse(serviceFeasibilityVBOType);
////        ObjectMapper objectMapper = new ObjectMapper();
////        assertAll("Should return serviceFeasibilityVBOType",
////                () -> assertNotNull(actualResponse, "Null Response"),
////                () -> assertEquals(objectMapper.writeValueAsString(serviceAbilityResponse), objectMapper.writeValueAsString(actualResponse)));
////    }
//}
