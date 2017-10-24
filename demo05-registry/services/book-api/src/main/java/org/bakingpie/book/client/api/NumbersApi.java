package org.bakingpie.book.client.api;

import feign.Headers;
import feign.RequestLine;
import org.bakingpie.book.client.ApiClient;

import javax.annotation.Generated;

// tag::adocSnippet[]
@Generated(value = "io.swagger.codegen.languages.JavaClientCodegen")
public interface NumbersApi extends ApiClient.Api {

    @RequestLine("GET http://localhost:8084/number-api/api/numbers/book")
    @Headers({"Content-Type: text/plain", "Accept: text/plain"})
    String generateBookNumber();
}
// end::adocSnippet[]
