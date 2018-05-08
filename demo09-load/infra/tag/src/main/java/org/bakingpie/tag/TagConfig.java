package org.bakingpie.tag;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxrs.client.WebClient;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Startup
@Singleton
public class TagConfig {
    private WebClient webClient;

    @PostConstruct
    private void config() throws Exception {
        System.out.println("Setting TAG Config");
        final Config config = ConfigProvider.getConfig();
        final String tagHost = config.getOptionalValue("TAG_HOST", String.class).orElse("http://localhost:8080");

        final LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        webClient = WebClient.create(tagHost + "/tag/api", emptyList(), singletonList(loggingFeature), null);

        waitForTag(0);
        clean();
        oauth2();
        account("agoncal");
        account("radcortez");
        account("goku");
        account("vegeta");
        api();
        loadBalancer();
        route();
    }

    private void waitForTag(final int count) throws Exception {
        try {
            final Response response =
                    webClient.reset()
                             .path("environment")
                             .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                             .get();

            if (response.getStatus() != 200) {
                if (count + count > 600) {
                    System.exit(0);
                }
                TimeUnit.SECONDS.sleep(10);
                waitForTag(count + 10);
            }
        } catch (Exception e) {
            if (count + count > 600) {
                System.exit(0);
            }
            TimeUnit.SECONDS.sleep(10);
            waitForTag(count + 10);
        }
    }

    private void clean() {
        // Default Stuff
        webClient.reset()
                 .path("/security/signature/http-signatures-auth-profile")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .delete();

        webClient.reset()
                 .path("/profile/oauth2/oauth2-auth-profile")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .delete();

        webClient.reset()
                 .path("/route/demo-no-auth-route")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .delete();

        webClient.reset()
                 .path("/route/demo-basic-route")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .delete();

        webClient.reset()
                 .path("/route/demo-oauth2-route")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .delete();

        webClient.reset()
                 .path("/route/demo-http-signatures-route")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .delete();

        webClient.reset()
                 .path("/route/register-host-to-load-balancer")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .delete();

        // Book Stuff
        webClient.reset()
                 .path("/profile/oauth2/books")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .delete();

        webClient.reset()
                 .path("/account/agoncal")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .delete();

        webClient.reset()
                 .path("/account/radcortez")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .delete();

        webClient.reset()
                 .path("/account/goku")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .delete();

        webClient.reset()
                 .path("/account/vegeta")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .delete();

        webClient.reset()
                 .path("/route/books")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .delete();

        webClient.reset()
                 .path("/http/books")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .delete();
    }

    private void oauth2() throws Exception {
        final URL resource = TagConfig.class.getClassLoader().getResource("book-oauth2.json");
        final JsonReader reader = Json.createReader(resource.openStream());
        final JsonObject object = reader.readObject();

        webClient.reset()
                 .path("/profile/oauth2/")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .type(MediaType.APPLICATION_JSON_TYPE)
                 .accept(MediaType.APPLICATION_JSON_TYPE)
                 .post(object);
    }

    private void account(final String account) throws Exception {
        final URL resource = TagConfig.class.getClassLoader().getResource("book-account-" + account + ".json");
        final JsonReader reader = Json.createReader(resource.openStream());
        final JsonObject object = reader.readObject();

        webClient.reset()
                 .path("/account/")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .type(MediaType.APPLICATION_JSON_TYPE)
                 .accept(MediaType.APPLICATION_JSON_TYPE)
                 .post(object);
    }

    private void api() throws Exception {
        final URL resource = TagConfig.class.getClassLoader().getResource("book-api.json");
        final JsonReader reader = Json.createReader(resource.openStream());
        final JsonObject object = reader.readObject();

        webClient.reset()
                 .path("/http/")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .type(MediaType.APPLICATION_JSON_TYPE)
                 .accept(MediaType.APPLICATION_JSON_TYPE)
                 .post(object);
    }

    private void loadBalancer() throws Exception {
        final URL resource = TagConfig.class.getClassLoader().getResource("book-api-lb.json");
        final JsonReader reader = Json.createReader(resource.openStream());
        final JsonObject object = reader.readObject();

        webClient.reset()
                 .path("/http/books")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .type(MediaType.APPLICATION_JSON_TYPE)
                 .accept(MediaType.APPLICATION_JSON_TYPE)
                 .post(object);
    }

    private void route() throws Exception {
        final URL resource = TagConfig.class.getClassLoader().getResource("book-route.json");
        final JsonReader reader = Json.createReader(resource.openStream());
        final JsonObject object = reader.readObject();

        webClient.reset()
                 .path("/route/")
                 .header(HttpHeaders.AUTHORIZATION, generateBasicAuth())
                 .type(MediaType.APPLICATION_JSON_TYPE)
                 .accept(MediaType.APPLICATION_JSON_TYPE)
                 .post(object);
    }

    private String generateBasicAuth() {
        return "Basic " + new String(Base64.getEncoder().encode(("admin:admin").getBytes()));
    }
}
