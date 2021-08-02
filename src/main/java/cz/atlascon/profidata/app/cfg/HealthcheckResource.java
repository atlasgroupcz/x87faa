package cz.atlascon.profidata.app.cfg;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/status")
@Resource
public class HealthcheckResource {

    @GET
    @Path("/health")
    public Response health() {
        return Response.ok().build();
    }

}
