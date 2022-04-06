package org.acme;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.acme.gen.model.NewPet;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(PetStoreWiremockTestResource.class)
class MyExampleBeanTest {

    @Inject
    MyExampleBean myExampleBean;

    WireMockServer petStoreServer;

    @Test
    public void creatingCatShouldReturnItsId(){
        Long id = myExampleBean.createPet(new NewPet().name("Loki").tag("Cat"));

        assertEquals(3L, id);

        petStoreServer.verify(
                postRequestedFor(urlPathEqualTo("/pets"))
                        .withRequestBody(equalToJson("{\"name\":\"Loki\",\"tag\":\"Cat\"}"))
        );
    }
}