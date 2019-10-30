package com.xiaoma.sofa.boot.service;

import javax.ws.rs.*;

@Path("/sample")
@Consumes("application/json;charset=UTF-8")
@Produces("application/json;charset=UTF-8")
public interface PersonServiceZookeeper {
    /**
     * @param string
     * @return
     */
    @GET
    @Path("/sayName/{string}")
    String sayName(@PathParam("string")String string);
}
