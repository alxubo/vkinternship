 package com.internship.olegchistov.configuration;
 
 import org.springframework.data.domain.AuditorAware;
 import java.util.Optional;
 
 public class AuditorAwareImpl implements AuditorAware<String> {
     @Override
     public Optional<String> getCurrentAuditor() {
         return Optional.of("StaticAuditor"); // As an example
     }
 }