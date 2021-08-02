package cz.atlascon.profidata.app;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/")
@Resource
@Singleton
public class TimestampResource {

    private final CryptoService cryptoService;
    private final NotifyService notifyService;

    @Inject
    public TimestampResource(CryptoService cryptoService,
                             NotifyService notifyService) {
        this.cryptoService = cryptoService;
        this.notifyService = notifyService;
    }

    @GET
    @Path("/create")
    public Response create(@QueryParam("msg") final String msg) throws Exception {
        return Response.ok(cryptoService.create(msg)).build();
    }

    @GET
    @Path("/pickup")
    public Response verify(@QueryParam("msg") final String msg) throws Exception {
        final String decoded = cryptoService.decode(msg);
        notifyService.notifyPickup(msg, decoded);
        return Response.ok(decoded).build();
    }


}
