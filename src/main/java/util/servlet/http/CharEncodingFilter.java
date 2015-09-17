package util.servlet.http;

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
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Sets character encoding for servlets that read/write characters from/to the
 * request/response body if none has been specified.
 */
public class CharEncodingFilter implements Filter {
    
    /* NB this is how the various possible cases are handled.
     * 
     *   E = char Encoding is set; !E = not set;
     *   T = content type is Text (i.e. any text/*) but no charset specified;
     *   O = content type Other than text/* (e.g. application/jason) or no 
     *       content type at all.
     *       
     * Request !E O => set default encoding (1)
     *          E O => do nothing
     *         !E T => set default encoding
     *          E T => do nothing
     * 
     * Response !E O => set default encoding (2)
     *           E O => do nothing
     *          !E T => set default encoding (3)
     *           E T => do nothing
     *                    
     * (1) setting the encoding should have no effect on getInputStream but the
     *     Reader returned by getReader should be created with the default 
     *     encoding that was set; no harm can be done.
     * (2) same argument as above but for getOutputStream and getWriter.
     * (3) even if the response is written through getOutputStream, it will have
     *     the default encoding set which fits in with our intents as the 
     *     content type is text so we assume the application is going to write
     *     in the default encoding, otherwise they shouldn't have used this
     *     filter.
     *     
     * So we only have one condition to check: char encoding not set.
     * To set the default encoding, we going to wait until the very last moment 
     * after which it's no longer possible to set the encoding. (See JavaDoc of
     * setContentType and setEncoding.)
     */

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
        if (!( request instanceof HttpServletRequest 
             && response instanceof HttpServletResponse )) return;
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        chain.doFilter(new RequestWrapper(httpRequest), 
                       new ResponseWrapper(httpResponse));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }

    
    private class RequestWrapper extends HttpServletRequestWrapper {
        
        HttpServletRequest target;
        
        RequestWrapper(HttpServletRequest target) {
            super(target);
            this.target = target;
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
        public ServletInputStream getInputStream() throws IOException {
            setEncodingIfAbsent(target);
            return super.getInputStream();
        }
        
        @Override
        public BufferedReader getReader() throws IOException {
            setEncodingIfAbsent(target);
            return super.getReader();
        }
        
    }
    
    private class ResponseWrapper extends HttpServletResponseWrapper {
        
        HttpServletResponse target;
        
        ResponseWrapper(HttpServletResponse target) {
            super(target);
            this.target = target;
        }
        
        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            setEncodingIfAbsent(target);
            return super.getOutputStream();
        }
        
        @Override
        public PrintWriter getWriter() throws IOException {
            setEncodingIfAbsent(target);
            return super.getWriter();
        }
        
    }
    
}
