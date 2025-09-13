package com.aeromaintenance.Config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(ExecutionTimeLoggingAspect.class);

    // This AOP pointcut covers all your methods (controller + service + repo) since you don’t have separate packages
    @Around("execution(* com.aeromaintenance..*(..)) " +
            "&& !within(javax.servlet.Filter+) " +
            "&& !within(org.springframework.web.filter.OncePerRequestFilter+)")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
    	String signature = pjp.getSignature().toShortString();
        long start = System.currentTimeMillis();

        try {
            log.debug("➡️ ENTER {}", signature);
            Object result = pjp.proceed();
            long took = System.currentTimeMillis() - start;
            log.debug("⬅️ EXIT {} -> {} (took={}ms)", signature, summarize(result), took);
            return result;
        } catch (Throwable ex) {
            String correlationId = MDC.get("correlationId");
            log.error("❌ [{}] Exception in {}: {}",
                    correlationId, signature, ex.getMessage(), ex);
            throw ex;
        }
    }

    // Prevent huge responses from flooding logs
 // Prevent huge responses or lazy-loading exceptions
    private String summarize(Object result) {
        if (result == null) return "null";

        try {
            // For collections, just show size
            if (result instanceof java.util.Collection) {
                return "Collection(size=" + ((java.util.Collection<?>) result).size() + ")";
            }

            // For arrays
            if (result.getClass().isArray()) {
                return "Array(type=" + result.getClass().getComponentType().getSimpleName()
                        + ", length=" + java.lang.reflect.Array.getLength(result) + ")";
            }

            // For entities (avoid lazy collections)
            String className = result.getClass().getSimpleName();
            return className + "@" + Integer.toHexString(System.identityHashCode(result));
        } catch (Exception e) {
            // Fallback in case something still breaks
            return result.getClass().getSimpleName() + "(unavailable)";
        }
    }

}

