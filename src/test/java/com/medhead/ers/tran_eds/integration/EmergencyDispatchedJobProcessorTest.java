package com.medhead.ers.tran_eds.integration;

import com.medhead.ers.tran_eds.application.messaging.exception.CannotProcessJobException;
import com.medhead.ers.tran_eds.application.messaging.redis.config.MessageListener;
import com.medhead.ers.tran_eds.utils.mock.api_dispatcher.AllAPIAvailableDispatcher;
import com.medhead.ers.tran_eds.utils.mock.api_dispatcher.AllAPIUnavailableDispatcher;
import com.medhead.ers.tran_eds.utils.mock.api_dispatcher.GeoMatrixAPIUnavailableHMSAPIAvailableDispatcher;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.medhead.ers.tran_eds.utils.FileReader.readFile;

@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@ExtendWith(OutputCaptureExtension.class)
class EmergencyDispatchedJobProcessorTest {

    private final static String MOCK_MESSAGE_RESOURCES_PATH = "src/test/resources/mock/message/";
    @Autowired
    private MessageListener messageListener;
    private static MockWebServer mockWebServer;
    @Value("${medhead.hms.api.hospitals}")
    private String medheadHMSAPIUrl;
    @Value("${graphhoper.api.matrix}")
    private String grapphopperMatrixUrl;


    // Mock web server configuration
    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(18080);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    static {
        GenericContainer<?> redis =
                new GenericContainer<>(DockerImageName.parse("redis"))
                        .withExposedPorts(6379).withPrivilegedMode(true);
        redis.start();
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
    }

    @Test
    void test_successDispatchEmergency(CapturedOutput output) throws IOException {
        mockWebServer.setDispatcher(new AllAPIAvailableDispatcher());
        String eventName = "EmergencyCreated";
        String jobProcessorName = eventName + "Job";
        List<String> messagesThatShouldBePublishedWhenJobSuccess = Arrays.asList("EmergencyDispatchedMessage");

        // Given
        String emergencyCreatedMessageFromFile = readFile(MOCK_MESSAGE_RESOURCES_PATH + "emergency_created.message");
        // Then
        Assertions.assertDoesNotThrow( ()-> {
            // When
            messageListener.receiveMessage(emergencyCreatedMessageFromFile);
        });
        // ... Then
        Assertions.assertTrue(output.getAll().contains("Message reçu de type : " + eventName));
        Assertions.assertTrue(output.getAll().contains("Traitement de l'événement de type : " + eventName + ". Job processor : "+jobProcessorName));
        Assertions.assertTrue(output.getAll().contains("Fin de traitement de l'événement de type : " + eventName +" - Événement traité avec succès."));
        for (String message: messagesThatShouldBePublishedWhenJobSuccess) {
            Assertions.assertTrue(output.getAll().contains("Publication du message de type : " + message));
        }
    }

    @Test
    void test_successDispatchEmergencyWhenGeoMatrixServiceIsNotAvailable(CapturedOutput output) throws IOException{
        mockWebServer.setDispatcher(new GeoMatrixAPIUnavailableHMSAPIAvailableDispatcher());
        String eventName = "EmergencyCreated";
        String jobProcessorName = eventName + "Job";
        List<String> messagesThatShouldBePublishedWhenJobSuccess = Arrays.asList("EmergencyDispatchedMessage");

        // Given
        String emergencyCreatedMessageFromFile = readFile(MOCK_MESSAGE_RESOURCES_PATH + "emergency_created.message");
        // Then
        Assertions.assertDoesNotThrow( ()-> {
            // When
            messageListener.receiveMessage(emergencyCreatedMessageFromFile);
        });
        // ... Then
        Assertions.assertTrue(output.getAll().contains("Message reçu de type : " + eventName));
        Assertions.assertTrue(output.getAll().contains("Traitement de l'événement de type : " + eventName + ". Job processor : "+jobProcessorName));
        Assertions.assertTrue(output.getAll().contains("Fin de traitement de l'événement de type : " + eventName +" - Événement traité avec succès."));
        for (String message: messagesThatShouldBePublishedWhenJobSuccess) {
            Assertions.assertTrue(output.getAll().contains("Publication du message de type : " + message));
        }
    }

    @Test
    void test_FailDispatchEmergencyWhenHospitalServiceIsNotAvailable() throws IOException {
        mockWebServer.setDispatcher(new AllAPIUnavailableDispatcher());
        String eventName = "EmergencyCreated";
        // Given
        String emergencyCreatedMessageFromFile = readFile(MOCK_MESSAGE_RESOURCES_PATH + "emergency_created.message");
        // Then
        Assertions.assertThrows(CannotProcessJobException.class, () -> {
            // When
            messageListener.receiveMessage(emergencyCreatedMessageFromFile);
        });
    }
}
