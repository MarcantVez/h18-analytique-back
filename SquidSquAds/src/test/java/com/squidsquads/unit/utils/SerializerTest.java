package com.squidsquads.unit.utils;

import com.squidsquads.model.Fingerprint;
import com.squidsquads.utils.Serializer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class SerializerTest {

    @Test
    public void testSerializeDuplicate() {
        Fingerprint fingerprint = new Fingerprint();
        fingerprint.setCanvasFingerprint("6101AE99");
        fingerprint.setScreenHeight(1440);
        fingerprint.setScreenWidth(2560);
        fingerprint.setTimezone("America/Toronto");
        fingerprint.setAcceptLanguages("en-CA,en;q=0.9,en-GB;q=0.8,en-US;q=0.7,fr;q=0.6");
        fingerprint.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
        Fingerprint fpClone = fingerprint;
        String hash1 = Serializer.serialize(fingerprint);
        String hash2 = Serializer.serialize(fpClone);

        assertEquals("The element should have the same hash", hash1, hash2);
    }

    @Test
    public void testSerializeSameInfo() {
        Fingerprint fingerprint = new Fingerprint();
        fingerprint.setCanvasFingerprint("6101AE99");
        fingerprint.setScreenHeight(1440);
        fingerprint.setScreenWidth(2560);
        fingerprint.setTimezone("America/Toronto");
        fingerprint.setAcceptLanguages("en-CA,en;q=0.9,en-GB;q=0.8,en-US;q=0.7,fr;q=0.6");
        fingerprint.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
        Fingerprint fingerprint2 = new Fingerprint();
        fingerprint2.setCanvasFingerprint("6101AE99");
        fingerprint2.setScreenHeight(1440);
        fingerprint2.setScreenWidth(2560);
        fingerprint2.setTimezone("America/Toronto");
        fingerprint2.setAcceptLanguages("en-CA,en;q=0.9,en-GB;q=0.8,en-US;q=0.7,fr;q=0.6");
        fingerprint2.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
        String hash1 = Serializer.serialize(fingerprint);
        String hash2 = Serializer.serialize(fingerprint2);

        assertEquals("The element should have the same hash", hash1, hash2);
    }

    @Test
    public void testSerializeDifferentInfo() {
        Fingerprint fingerprint = new Fingerprint();
        fingerprint.setCanvasFingerprint("6101AE99");
        fingerprint.setScreenHeight(1080);
        fingerprint.setScreenWidth(1920);
        fingerprint.setTimezone("America/Toronto");
        fingerprint.setAcceptLanguages("en-CA,en;q=0.8,en-GB;q=0.7,en-US;q=0.6,fr;q=0.5");
        fingerprint.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
        Fingerprint fingerprint2 = new Fingerprint();
        fingerprint2.setCanvasFingerprint("6101AE99");
        fingerprint2.setScreenHeight(1440);
        fingerprint2.setScreenWidth(2560);
        fingerprint2.setTimezone("America/Toronto");
        fingerprint2.setAcceptLanguages("en-CA,en;q=0.9,en-GB;q=0.8,en-US;q=0.7,fr;q=0.6");
        fingerprint2.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
        String hash1 = Serializer.serialize(fingerprint);
        String hash2 = Serializer.serialize(fingerprint2);

        assertNotEquals("The element should have the same hash", hash1, hash2);
    }

}
