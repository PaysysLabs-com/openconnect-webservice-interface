package com.paysyslabs.bootstrap.webservice.validators;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HashValidator {
    
    private static final Logger LOG = LoggerFactory.getLogger(HashValidator.class);
    
    @Value("${service.secret}")
    private String secret;

    public boolean validate(List<String> parameters, String hash) {
        List<String> toHash = new ArrayList<>(parameters);
        toHash.add(secret);
        
        String stringToHash = String.join(",", toHash);
        
        String expected = DigestUtils.sha256Hex(stringToHash);

        LOG.info("expected {} == found {}", expected, hash);
        
        return hash.equals(expected);
    }
    
}
