package util.servlet;

import static java.util.Objects.requireNonNull;
import static util.Exceptions.unchecked;
import static util.Exceptions.throwAsIfUnchecked;
import static util.Strings.isNullOrEmpty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Sets character encoding for servlets that read/write characters from/to the
 * request/response body if none has been specified.
 */
public class CharEncodingFilter implements Filter {

    /**
     * Creates a new filter to default character encoding to UTF-8. 
     * @return a new filter to add UTF-8 default encoding.
     */
    public static CharEncodingFilter Utf8() {
        return new CharEncodingFilter(StandardCharsets.UTF_8);
    }
    
    private final String defaultEncoding;
    
    /**
     * Creates a new instance to set the specified encoding if none is specified
     * in the request/response or by the servlet.
     * @param defaultEncoding the default encoding to use if none is present.
     */
    public CharEncodingFilter(Charset defaultEncoding) {
        requireNonNull(defaultEncoding, "defaultEncoding");
        
        this.defaultEncoding = defaultEncoding.name();
    }
    
    private void setEncodingIfAbsent(Supplier<String> getter, 
            Consumer<String> setter) throws IOException {
        String givenEncoding = getter.get();
        if (isNullOrEmpty(givenEncoding)) {
            setter.accept(defaultEncoding);
        }
    }
    
    private void setEncodingIfAbsent(ServletRequest r) {
        try {
            setEncodingIfAbsent(r::getCharacterEncoding, 
                                unchecked(r::setCharacterEncoding));
        } catch (IOException e) {
            throwAsIfUnchecked(e);
        }
    }
    
    private void setEncodingIfAbsent(ServletResponse r) throws IOException {
        setEncodingIfAbsent(r::getCharacterEncoding, 
                            r::setCharacterEncoding);
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new RequestWrapper(request), 
                       new ResponseWrapper(response));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }

    
    private class RequestWrapper extends ServletRequestWrapper {
        
        RequestWrapper(ServletRequest target) {
            super(target);
        }
        
        @Override
        public String getParameter(String name) {
            setEncodingIfAbsent(target);
            return super.getParameter(name);
        }

        @Override
        public Enumeration<String> getParameterNames() {
            setEncodingIfAbsent(target);
            return super.getParameterNames();
        }

        @Override
        public String[] getParameterValues(String name) {
            setEncodingIfAbsent(target);
            return super.getParameterValues(name);
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            setEncodingIfAbsent(target);
            return super.getParameterMap();
        }
        
        @Override
        public BufferedReader getReader() throws IOException {
            setEncodingIfAbsent(target);
            return super.getReader();
        }
        
    }
    
    private class ResponseWrapper extends ServletResponseWrapper {
        
        ResponseWrapper(ServletResponse target) {
            super(target);
        }
        
        @Override
        public PrintWriter getWriter() throws IOException {
            setEncodingIfAbsent(target);
            return super.getWriter();
        }
    }
}
