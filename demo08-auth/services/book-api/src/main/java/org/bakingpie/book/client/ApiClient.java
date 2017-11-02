package org.bakingpie.book.client;

import feign.Logger;
import feign.hystrix.HystrixFeign;
import feign.ribbon.LoadBalancingTarget;
import feign.slf4j.Slf4jLogger;
import org.bakingpie.book.client.api.NumbersApi;
import org.slf4j.LoggerFactory;

import javax.annotation.Generated;

@Generated(value = "io.swagger.codegen.languages.JavaClientCodegen")
public class ApiClient {
    public interface Api {
    }

    private String basePath = "/number-api/api";

    public NumbersApi buildNumberApiClient() {
        // This instance will be invoked if there are errors of any kind.
        NumbersApi fallback = () -> {
            return "FALLBACK ISBN";
        };

        return HystrixFeign.builder()
                .logger(new Logger.JavaLogger())
                .logLevel(Logger.Level.FULL)
                .target(LoadBalancingTarget.create(NumbersApi.class, "http://number-api" + basePath), fallback);
    }
}
