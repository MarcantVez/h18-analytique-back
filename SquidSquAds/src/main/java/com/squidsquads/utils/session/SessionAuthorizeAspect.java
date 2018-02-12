package com.squidsquads.utils.session;

import com.squidsquads.model.account.AdminType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class SessionAuthorizeAspect {

    private static final Logger logger = LoggerFactory.getLogger(SessionAuthorizeAspect.class);
    private static final String FORBIDDEN_SESSION_MESSAGE = "Vous n'avez pas les droits d'accès requis pour accéder à cette ressource";

    @Around("@annotation(com.squidsquads.utils.session.SessionAuthorize) && execution(public * *(..))")
    public Object verifySession(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SessionAuthorize sessionAuthorize = method.getAnnotation(SessionAuthorize.class);

        // Obtenier la requete HTTP faites au endpoint
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();
        String token = request.getHeader("Token");

        // Si la session n'existe pas
        if (!SessionManager.getInstance().isSessionValid(token)) {
            logger.info("Un utilisateur non connecté a tenté d'accéder à " + request.getRequestURI());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(FORBIDDEN_SESSION_MESSAGE);
        }

        AdminType adminType = SessionManager.getInstance().getAdminTypeForToken(token);

        // Si le type de l'utilisateur n'est pas permis
        if (adminType == null || !Arrays.asList(sessionAuthorize.value()).contains(adminType)) {
            logger.info("Un utilisateur ne possédant pas les droits requis a tenté d'accéder à " + request.getRequestURI());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(FORBIDDEN_SESSION_MESSAGE);
        }

        // Renouvellement de la date d'expiration de la session
        SessionManager.getInstance().renewSession(token);

        // Executer la methode normalement
        Object value;
        try {
            value = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        }
        return value;
    }
}
