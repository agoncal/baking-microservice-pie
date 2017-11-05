package org.bakingpie.book.client;

import feign.Feign;
import feign.Logger;
import feign.slf4j.Slf4jLogger;
import org.bakingpie.book.client.api.NumbersApi;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.annotation.Generated;

@Generated(value = "io.swagger.codegen.languages.JavaClientCodegen")
// tag::adocSnippet[]
public class ApiClient {
    public interface Api {
    }

    private String baseHost = "http://localhost:8084";
    private String basePath = "/number-api/api";

    public NumbersApi buildNumberApiClient() {
        final Config config = ConfigProvider.getConfig();
        config.getOptionalValue("NUMBER_API_HOST", String.class)
              .ifPresent(host -> baseHost = host);

        return Feign.builder()
            .logger(new Slf4jLogger(NumbersApi.class))
            .logLevel(Logger.Level.FULL)
            .target(NumbersApi.class, baseHost + basePath);
    }
}
// end::adocSnippet[]
