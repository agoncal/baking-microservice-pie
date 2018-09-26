package org.bakingpie.book.client.api;

import org.bakingpie.book.client.ApiClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

// tag::adocSnippet[]
@ApplicationScoped
public class NumbersApiProducer {

    @Produces
    @RequestScoped
    public NumbersApi createNumbersApi() {
        return new ApiClient().buildNumberApiClient();
    }
}
// end::adocSnippet[]
