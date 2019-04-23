package com.base.backendcore.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class CustomAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Optional<String> loggedName = Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
        return loggedName;
    }
}
