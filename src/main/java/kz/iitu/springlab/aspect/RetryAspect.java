package kz.iitu.springlab.aspect;

import kz.iitu.springlab.audit.RetryOnFailure;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0) // Выполняется до всех остальных аспектов
public class RetryAspect {

    private static final Logger log = LoggerFactory.getLogger(RetryAspect.class);

    @Around("@annotation(retry)")
    public Object retryOnFailure(ProceedingJoinPoint pjp, RetryOnFailure retry) throws Throwable {
        int maxAttempts = retry.attempts();
        long delay = retry.delayMs();
        Throwable lastException = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                if (attempt > 1) {
                    log.warn("[RETRY] Попытка {} из {} для метода {}",
                            attempt, maxAttempts, pjp.getSignature().toShortString());
                }
                return pjp.proceed();
            } catch (Throwable ex) {
                lastException = ex;
                log.error("[RETRY] Ошибка на попытке {}: {}", attempt, ex.getMessage());
                if (attempt < maxAttempts && delay > 0) {
                    Thread.sleep(delay);
                }
            }
        }
        throw lastException;
    }
}