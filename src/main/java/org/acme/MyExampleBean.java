package org.acme;

import org.acme.gen.api.ApiException;
import org.acme.gen.api.PetStoreApi;
import org.acme.gen.model.NewPet;
import org.acme.gen.model.Pet;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class MyExampleBean {
    private final Logger logger = LoggerFactory.getLogger(MyExampleBean.class);

    @Inject
    @RestClient
    PetStoreApi petStoreApi;

    public Long createPet(NewPet pet){
        logger.info("Creating a pet {}", pet);
        try {
            Pet petResponse = petStoreApi.addPet(pet);
            return petResponse.getId();
        } catch (ApiException e) {
            logger.error("Received error response from petStoreService:", e);
            return -1L;
        }
    }

    public void logAllPets(){
        try {
            List<Pet> pets = petStoreApi.findPets(Collections.emptyList(), 10);
            logger.info("The following pets are in our pet store:");
            pets.forEach(pet -> logger.info(pet.toString()));
        } catch (ApiException e) {
            logger.error("Received error response from petStoreService:", e);
        }
    }
}
