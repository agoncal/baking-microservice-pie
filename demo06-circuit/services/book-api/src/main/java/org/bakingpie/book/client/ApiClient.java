package org.bakingpie.book.client;

import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.health.Service;
import com.orbitz.consul.model.health.ServiceHealth;
import feign.Logger;
import feign.hystrix.HystrixFeign;
import feign.slf4j.Slf4jLogger;
import org.bakingpie.book.client.api.NumbersApi;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.LoggerFactory;

import javax.annotation.Generated;
import java.util.Iterator;
import java.util.List;

@Generated(value = "io.swagger.codegen.languages.JavaClientCodegen")
public class ApiClient {
    public interface Api {
    }

    private final org.slf4j.Logger log = LoggerFactory.getLogger(ApiClient.class);

    private String consulHost = "http://localhost";
    private Integer consulPort = 8500;
    private String basePath = "/number-api/api";

    public NumbersApi buildNumberApiClient() {
        final Config config = ConfigProvider.getConfig();
        config.getOptionalValue("CONSUL_HOST", String.class).ifPresent(host -> consulHost = host);
        config.getOptionalValue("CONSUL_PORT", Integer.class).ifPresent(port -> consulPort = port);

        final Consul consul = Consul.builder().withUrl(consulHost + ":" + consulPort).build();
        final HealthClient healthClient = consul.healthClient();

        final List<ServiceHealth> nodes = healthClient.getHealthyServiceInstances("CONSUL_NUMBER_API").getResponse();
        final Iterator<ServiceHealth> iterator = nodes.iterator();
        final String baseHost;
        if (iterator.hasNext()) {
            final Service service = iterator.next().getService();
            baseHost = service.getAddress() + ":" + service.getPort();
        } else {
            // This most likely will fail anyway, but just to have a valid endpoint to call.
            baseHost = "http://localhost:8084";
        }

        // This instance will be invoked if there are errors of any kind.
        // tag::adocSkip[]
        NumbersApi fallback = () -> {
            return "FALLBACK ISBN";
        };

        // tag::adocSkip[]
        log.info("Feign builder on " + baseHost + basePath);
        // end::adocSkip[]
        return HystrixFeign.builder()
                .logger(new Slf4jLogger(NumbersApi.class))
                .logLevel(Logger.Level.FULL)
                .target(NumbersApi.class, baseHost + basePath, fallback);
        // end::adocSkip[]
    }
}
