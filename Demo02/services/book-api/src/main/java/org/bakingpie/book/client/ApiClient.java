package org.bakingpie.book.client;

import feign.Feign;
import feign.slf4j.Slf4jLogger;

import javax.annotation.Generated;

@Generated(value = "io.swagger.codegen.languages.JavaClientCodegen")
public class ApiClient {
    public interface Api {
    }

    private String basePath = "http://localhost:8084/number-api/api";
    private Feign.Builder feignBuilder;

    public ApiClient() {
        feignBuilder = Feign.builder()
            .logger(new Slf4jLogger());
    }

    public ApiClient(String[] authNames) {
        this();
        for (String authName : authNames) {
            throw new RuntimeException("auth name \"" + authName + "\" not found in available auth names");
        }
    }

    /**
     * Basic constructor for single auth name
     *
     * @param authName
     */
    public ApiClient(String authName) {
        this(new String[]{authName});
    }

    public String getBasePath() {
        return basePath;
    }

    public ApiClient setBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    public Feign.Builder getFeignBuilder() {
        return feignBuilder;
    }

    public ApiClient setFeignBuilder(Feign.Builder feignBuilder) {
        this.feignBuilder = feignBuilder;
        return this;
    }

    /**
     * Creates a feign client for given API interface.
     * <p>
     * Usage:
     * ApiClient apiClient = new ApiClient();
     * apiClient.setBasePath("http://localhost:8080");
     * XYZApi api = apiClient.buildClient(XYZApi.class);
     * XYZResponse response = api.someMethod(...);
     *
     * @param <T>         Type
     * @param clientClass Client class
     * @return The Client
     */
    public <T extends Api> T buildClient(Class<T> clientClass) {
        return feignBuilder.target(clientClass, basePath);
    }
}
