package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    MyExampleBean exampleBean;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        exampleBean.logAllPets();
        return "Hello RESTEasy";
    }
}