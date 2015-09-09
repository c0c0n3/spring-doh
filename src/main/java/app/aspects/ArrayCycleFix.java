package app.aspects;

import org.springframework.stereotype.Component;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect to fix exceptions thrown by ArrayCycle when corner cases are hit.
 * Note that ArrayCycle is not a bean so it's not possible to advice it 
 * directly through Spring. (Quoting the Spring manual "Spring AOP only 
 * supports method execution join points for Spring beans".)
 * But this advice applies to any bean implementing cycle; we only have one, 
 * the cycle tracker bean which we're going to instantiate with an array cycle.
 */
@Component // cater for auto-detection thru classpath scanning
@Aspect
public class ArrayCycleFix { // see note at bottom of file

    @Pointcut("execution(* app.core.cyclic.Cycle.next(..))")
    public void next() {}
    
    @Around("next()")
    public Object convertExceptionToNull(ProceedingJoinPoint jp) 
            throws Throwable {
        try {
            return jp.proceed();
        } catch (NullPointerException | ArithmeticException e) { 
            return null;
        }
    }
    
}
/* NB it would be quite silly to do this in real life; first price would be to
 *  *fix* ArrayCycle. 
 *  But this arrangement gives us a cheap way to play with aspects.
 */