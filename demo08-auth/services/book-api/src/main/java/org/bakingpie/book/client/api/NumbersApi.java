package org.bakingpie.book.client.api;

import feign.Headers;
import feign.RequestLine;
import org.bakingpie.book.client.ApiClient;

import javax.annotation.Generated;

@Generated(value = "io.swagger.codegen.languages.JavaClientCodegen")
public interface NumbersApi extends ApiClient.Api {

    @RequestLine("GET /numbers/book")
    @Headers({"Content-Type: text/plain", "Accept: text/plain"})
    String generateBookNumber();
}
