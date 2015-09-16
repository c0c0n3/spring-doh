package util.servlet;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Convenience wrapper around a target {@link ServletRequest} to just forward
 * calls to the target so that subclasses can override just the methods they
 * need.
 */
public abstract class ServletRequestWrapper implements ServletRequest {

    protected final ServletRequest target;
    
    /**
     * Creates a new instance to forward calls to the {@code target}.
     * @param target the instance to forward calls to.
     * @throws NullPointerException if the argument is {@code null}.
     */
    protected ServletRequestWrapper(ServletRequest target) {
        requireNonNull(target);
        this.target = target;
    }

    @Override
    public Object getAttribute(String name) {
        return target.getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return target.getAttributeNames();
    }

    @Override
    public String getCharacterEncoding() {
        return target.getCharacterEncoding();
    }

    @Override
    public void setCharacterEncoding(String env)
            throws UnsupportedEncodingException {
        target.setCharacterEncoding(env);
    }

    @Override
    public int getContentLength() {
        return target.getContentLength();
    }

    @Override
    public long getContentLengthLong() {
        return target.getContentLengthLong();
    }

    @Override
    public String getContentType() {
        return target.getContentType();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return target.getInputStream();
    }

    @Override
    public String getParameter(String name) {
        return target.getParameter(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return target.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        return target.getParameterValues(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return target.getParameterMap();
    }

    @Override
    public String getProtocol() {
        return target.getProtocol();
    }

    @Override
    public String getScheme() {
        return target.getScheme();
    }

    @Override
    public String getServerName() {
        return target.getServerName();
    }

    @Override
    public int getServerPort() {
        return target.getServerPort();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return target.getReader();
    }

    @Override
    public String getRemoteAddr() {
        return target.getRemoteAddr();
    }

    @Override
    public String getRemoteHost() {
        return target.getRemoteHost();
    }

    @Override
    public void setAttribute(String name, Object o) {
        target.setAttribute(name, o);
    }

    @Override
    public void removeAttribute(String name) {
        target.removeAttribute(name);
    }

    @Override
    public Locale getLocale() {
        return target.getLocale();
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return target.getLocales();
    }

    @Override
    public boolean isSecure() {
        return target.isSecure();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return target.getRequestDispatcher(path);
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getRealPath(String path) {
        return target.getRealPath(path);
    }

    @Override
    public int getRemotePort() {
        return target.getRemotePort();
    }

    @Override
    public String getLocalName() {
        return target.getLocalName();
    }

    @Override
    public String getLocalAddr() {
        return target.getLocalAddr();
    }

    @Override
    public int getLocalPort() {
        return target.getLocalPort();
    }

    @Override
    public ServletContext getServletContext() {
        return target.getServletContext();
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return target.startAsync();
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest,
            ServletResponse servletResponse) throws IllegalStateException {
        return target.startAsync(servletRequest, servletResponse);
    }

    @Override
    public boolean isAsyncStarted() {
        return target.isAsyncStarted();
    }

    @Override
    public boolean isAsyncSupported() {
        return target.isAsyncSupported();
    }

    @Override
    public AsyncContext getAsyncContext() {
        return target.getAsyncContext();
    }

    @Override
    public DispatcherType getDispatcherType() {
        return target.getDispatcherType();
    }
    
}
