package com.squidsquads.utils;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class Serializer {
    public String serializeToString(Serializable object){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String encoded = new String(Base64.encode(byteArrayOutputStream.toByteArray()));
            String hash = "";
            return hash;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
