package integrations.mocks;

import com.google.gson.JsonParser;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.mockserver.model.Parameter;
import org.mockserver.netty.MockServer;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.newBufferedReader;

public class Mocks {
    private static final MockServer MOCK_SERVER = new MockServer(8080);
    private static final MockServerClient MOCK_SERVER_CLIENT = new MockServerClient("localhost", MOCK_SERVER.getLocalPort());

    public static void startMocks() {
        // to turn off mock server logging - add run param -Dmockserver.logLevel=OFF
        try {
            mockResultResponse();
            mockSendResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopMocks() {
        if (MOCK_SERVER.isRunning()) {
            MOCK_SERVER.stop();
        }
        if (!MOCK_SERVER_CLIENT.hasStopped()) {
            MOCK_SERVER_CLIENT.stop();
        }
    }

    private static void mockSendResponse() throws IOException {
        HttpRequest request = HttpRequest.request("/api/send");
        String body = JsonParser.parseReader(newBufferedReader(Path.of("src/test/java/suites/testelium/utils/sendMockResponse.json")))
                .getAsJsonObject()
                .toString();
        HttpResponse response = HttpResponse.response(body)
                .withContentType(MediaType.APPLICATION_JSON)
                .withStatusCode(200);
        MOCK_SERVER_CLIENT.when(request).respond(response);
    }

    private static void mockResultResponse() throws IOException {
        HttpRequest request = HttpRequest.request("/api/result/{uuid}").withPathParameter(Parameter.param("uuid", "fee052ac-1c51-49f0-89f5-a7b888f21d38"));
        String body = JsonParser.parseReader(newBufferedReader(Path.of("src/test/java/suites/testelium/utils/resultMockResponse.json")))
                .getAsJsonObject()
                .toString();
        HttpResponse response = HttpResponse.response(body)
                .withContentType(MediaType.APPLICATION_JSON)
                .withStatusCode(200);
        MOCK_SERVER_CLIENT.when(request).respond(response);
    }
}
