package com.dwalldorf.timetrack.annotation;

import com.dwalldorf.timetrack.exception.AdminRequiredException;
import com.dwalldorf.timetrack.exception.LoginRequiredException;
import com.dwalldorf.timetrack.service.UserService;
import javax.inject.Inject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequireRoleInvocationHandler {

    private final UserService userService;

    @Inject
    public RequireRoleInvocationHandler(UserService userService) {
        this.userService = userService;
    }

    @Before("execution(* *(..)) && @annotation(RequireLogin)")
    public void checkLoginBefore(JoinPoint joinPoint) throws Exception {
        if (userService.getCurrentUser() == null) {
            String signature = joinPoint.getSignature().toShortString();
            throw new LoginRequiredException(signature + " called without login");
        }
    }

    @Before("execution(* *(..)) && @annotation(RequireAdmin)")
    public void checkAdminBefore(JoinPoint joinPoint) throws Exception {
        String signature = joinPoint.getSignature().toShortString();

        if (userService.getCurrentUser() == null) {
            throw new LoginRequiredException(signature + " called without login");
        }
        if (!userService.getCurrentUser().getUserProperties().getUserSettings().isAdmin()) {
            throw new AdminRequiredException(signature + " called without admin rights");
        }
    }
}