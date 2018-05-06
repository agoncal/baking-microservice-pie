package org.bakingpie.book.client.api;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;

@Alternative
@ApplicationScoped
public class NumbersApiAlternative {
    @Produces
    public NumbersApi createNumbersApi() {
        return () -> "BK-Mock";
    }
}
