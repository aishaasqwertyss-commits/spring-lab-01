package kz.iitu.springlab.aspect;

import kz.iitu.springlab.annotation.RequiresRole;
import kz.iitu.springlab.security.SecurityContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    @Before("@annotation(requiresRole)")
    public void checkSecurity(RequiresRole requiresRole) {
        String currentRole = SecurityContext.getCurrentRole();
        if (!requiresRole.value().equals(currentRole)) {
            throw new SecurityException("Access denied! Required role: " + requiresRole.value() + ", Current role: " + currentRole);
        }
        System.out.println("[AOP SECURITY] Access granted for role: " + currentRole);
    }
}