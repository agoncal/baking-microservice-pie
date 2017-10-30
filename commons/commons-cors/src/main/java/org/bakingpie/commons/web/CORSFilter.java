package org.bakingpie.commons.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Antonio Goncalves
 * http://www.antoniogoncalves.org
 * --
 */
// tag::adocSnippet[]
@WebFilter(filterName = "corsFilter")
public class CORSFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, Etag, if-none-match, x-xsrf-token");
        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        resp.addHeader("Access-Control-Max-Age", "1209600");
        resp.addHeader("Access-Control-Expose-Headers", "origin, content-type, accept, authorization, ETag, if-none-match");
        chain.doFilter(request, response);
    }

    // tag::adocSkip[]
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }
    // end::adocSkip[]
}
// end::adocSnippet[]
