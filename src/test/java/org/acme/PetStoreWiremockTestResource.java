package org.acme;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.http.ContentTypeHeader;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


public class PetStoreWiremockTestResource implements QuarkusTestResourceLifecycleManager {

    private WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer(8888);
        wireMockServer.start();

        wireMockServer.stubFor(post(urlEqualTo("/pets"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(ContentTypeHeader.KEY, MediaType.APPLICATION_JSON)
                        .withBody("{\"name\":\"Loki\", \"tag\":\"Cat\", \"id\": 3}")
                ));

        return Collections.singletonMap(
                "quarkus.rest-client.\"org.acme.gen.api.PetStoreApi\".url",
                wireMockServer.baseUrl()
        );
    }

    @Override
    public void inject(TestInjector testInjector) {
        testInjector.injectIntoFields(wireMockServer, f -> f.getName().equals("petStoreServer"));
    }

    @Override
    public void stop() {
        if (null != wireMockServer) {
            wireMockServer.stop();
        }
    }
}
