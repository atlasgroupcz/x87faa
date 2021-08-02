package cz.atlascon.profidata.app.cfg;

import cz.atlascon.profidata.app.TimestampResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/rest")
public class Config extends ResourceConfig {

    @PostConstruct
    public void registerEndpoints() {
        register(HealthcheckResource.class);
        register(TimestampResource.class);
    }

}