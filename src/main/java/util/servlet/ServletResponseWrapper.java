package util.servlet;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

/**
 * Convenience wrapper around a target {@link ServletResponse} to just forward
 * calls to the target so that subclasses can override just the methods they
 * need.
 */
public abstract class ServletResponseWrapper implements ServletResponse {
    
    protected final ServletResponse target;
    
    /**
     * Creates a new instance to forward calls to the {@code target}.
     * @param target the instance to forward calls to.
     * @throws NullPointerException if the argument is {@code null}.
     */
    protected ServletResponseWrapper(ServletResponse target) {
        requireNonNull(target);
        this.target = target;
    }
    
    @Override
    public PrintWriter getWriter() throws IOException {
        return target.getWriter();
    }
    
    // all the remaining methods just forward the call to the target.
    
    @Override
    public String getCharacterEncoding() {
        return target.getCharacterEncoding();
    }

    @Override
    public String getContentType() { 
        return target.getContentType(); 
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return target.getOutputStream();
    }

    @Override
    public void setCharacterEncoding(String charset) {
        target.setCharacterEncoding(charset);
    }

    @Override
    public void setContentLength(int len) {
        target.setContentLength(len);
    }

    @Override
    public void setContentLengthLong(long len) {
        target.setContentLengthLong(len);
    }

    @Override
    public void setContentType(String type) {
        target.setContentType(type);
    }

    @Override
    public void setBufferSize(int size) {
        target.setBufferSize(size);
    }

    @Override
    public int getBufferSize() {
        return target.getBufferSize();
    }

    @Override
    public void flushBuffer() throws IOException {
        target.flushBuffer();
    }

    @Override
    public void resetBuffer() {
        target.resetBuffer();
    }

    @Override
    public boolean isCommitted() {
        return target.isCommitted();
    }

    @Override
    public void reset() {
        target.reset();
    }

    @Override
    public void setLocale(Locale loc) {
        target.setLocale(loc);
    }

    @Override
    public Locale getLocale() {
        return target.getLocale();
    }
    
}
