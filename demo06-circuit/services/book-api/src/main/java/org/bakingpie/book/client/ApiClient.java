package org.bakingpie.book.client;

import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.health.ServiceHealth;
import feign.Logger;
import feign.hystrix.HystrixFeign;
import feign.slf4j.Slf4jLogger;
import org.bakingpie.book.client.api.NumbersApi;
import org.slf4j.LoggerFactory;

import javax.annotation.Generated;
import java.util.List;

@Generated(value = "io.swagger.codegen.languages.JavaClientCodegen")
public class ApiClient {
    public interface Api {
    }

    private final org.slf4j.Logger log = LoggerFactory.getLogger(ApiClient.class);

    private String basePath = "/number-api/api";

    public NumbersApi buildNumberApiClient() {

        Consul consul = Consul.builder().build();
        HealthClient healthClient = consul.healthClient();

        List<ServiceHealth> nodes = healthClient.getHealthyServiceInstances("CONSUL_NUMBER_API").getResponse();
        String baseHost = "http://" + nodes.iterator().next().getNode().getAddress() + ":" + nodes.iterator().next().getService().getPort();

        // This instance will be invoked if there are errors of any kind.
        NumbersApi fallback = () -> {
            return "FALLBACK ISBN";
        };

        log.info("Feign builder on " + baseHost + basePath);
        return HystrixFeign.builder()
                .logger(new Slf4jLogger(NumbersApi.class))
                .logLevel(Logger.Level.FULL)
                .target(NumbersApi.class, baseHost + basePath, fallback);
    }
}
