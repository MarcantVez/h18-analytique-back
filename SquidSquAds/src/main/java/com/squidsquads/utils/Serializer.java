package com.squidsquads.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Base64;

@Component
public class Serializer {
    public static String serialize(Serializable toSerialize){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(toSerialize);
            out.flush();
            String encoded = Base64.getEncoder().encodeToString(bos.toByteArray());
            return DigestUtils.md5Hex(encoded);
        } catch (IOException e) {
            return null;
        }
    }
}
