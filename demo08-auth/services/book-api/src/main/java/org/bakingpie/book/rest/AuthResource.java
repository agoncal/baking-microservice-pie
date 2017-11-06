package org.bakingpie.book.rest;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

@ApplicationScoped
@Path("auth")
public class AuthResource {
    @Inject
    @ConfigProperty(name = "OAUTH2_TOKEN_ENDPOINT", defaultValue = "http://localhost:9000/oauth2/token")
    private String tokenEndpoint;

    @POST
    @Consumes(APPLICATION_FORM_URLENCODED)
    public Response token(@FormParam("username") final String username,
                          @FormParam("password") final String password) {

        final Response response =
                ClientBuilder.newClient()
                             .target(tokenEndpoint)
                             .request()
                             .header("Content-Type", "application/x-www-form-urlencoded")
                             .post(Entity.form(new Form().param("username", username)
                                                         .param("password", password)
                                                         .param("grant_type", "password")));

        if (response.getStatus() == 200) {
            final Token token = response.readEntity(Token.class);
            return Response.ok()
                           .header("Authorization", "Bearer " + token.access_token)
                           .entity(token.access_token).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @XmlRootElement
    public static class Token {
        private String access_token;
    }
}
