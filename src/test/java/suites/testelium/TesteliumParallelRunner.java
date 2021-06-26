package suites.testelium;

import com.google.gson.JsonParser;
import com.intuit.karate.Results;
import org.junit.jupiter.api.*;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.mockserver.model.Parameter;
import org.mockserver.netty.MockServer;

import java.io.IOException;
import java.nio.file.Path;

import static com.intuit.karate.Runner.Builder;
import static java.nio.file.Files.newBufferedReader;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TesteliumParallelRunner {
    private MockServer server;
    private MockServerClient client;

    @BeforeAll
    public void setUp() {
        // to turn off logging - add run param -Dmockserver.logLevel=OFF
        server = new MockServer(8080);
        client = new MockServerClient("localhost", server.getLocalPort());
    }

    @AfterAll
    public void tearDown() {
        if (server.isRunning()) {
            server.stop();
        }
        if (!client.hasStopped()) {
            client.stop();
        }
    }

    @Test
    public void test() {
        try {
            mockSendResponse();
            mockResultResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Builder builder = new Builder();
        builder.path("src/test/java/suites/testelium/testelium.feature");
        Results results = builder.parallel(1);

        Assertions.assertEquals(0, results.getFailCount());
    }

    private void mockSendResponse() throws IOException {
        HttpRequest request = HttpRequest.request("/api/send");
        String body = JsonParser.parseReader(newBufferedReader(Path.of("src/test/java/suites/testelium/utils/sendMockResponse.json")))
                .getAsJsonObject()
                .toString();
        HttpResponse response = HttpResponse.response(body)
                .withContentType(MediaType.APPLICATION_JSON)
                .withStatusCode(200);
        client.when(request).respond(response);
    }

    private void mockResultResponse() throws IOException {
        HttpRequest request = HttpRequest.request("/api/result/{uuid}").withPathParameter(Parameter.param("uuid", "fee052ac-1c51-49f0-89f5-a7b888f21d38"));
        String body = JsonParser.parseReader(newBufferedReader(Path.of("src/test/java/suites/testelium/utils/resultMockResponse.json")))
                .getAsJsonObject()
                .toString();
        HttpResponse response = HttpResponse.response(body)
                .withContentType(MediaType.APPLICATION_JSON)
                .withStatusCode(200);
        client.when(request).respond(response);
    }
}
