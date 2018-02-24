package com.squidsquads.unit.utils;

import com.squidsquads.model.traffic.FingerPrint;
import com.squidsquads.utils.Serializer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class SerializerTest {

    @Test
    public void testSerializeDuplicate() {
        FingerPrint fingerPrint = new FingerPrint();
        fingerPrint.setCanvasFingerprint("6101AE99");
        fingerPrint.setScreenHeight(1440);
        fingerPrint.setScreenWidth(2560);
        fingerPrint.setTimezone("America/Toronto");
        fingerPrint.setAcceptLanguages("en-CA,en;q=0.9,en-GB;q=0.8,en-US;q=0.7,fr;q=0.6");
        fingerPrint.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
        FingerPrint fpClone = fingerPrint;
        String hash1 = Serializer.serialize(fingerPrint);
        String hash2 = Serializer.serialize(fpClone);

        assertEquals("The element should have the same hash", hash1, hash2);
    }

    @Test
    public void testSerializeSameInfo() {
        FingerPrint fingerPrint = new FingerPrint();
        fingerPrint.setCanvasFingerprint("6101AE99");
        fingerPrint.setScreenHeight(1440);
        fingerPrint.setScreenWidth(2560);
        fingerPrint.setTimezone("America/Toronto");
        fingerPrint.setAcceptLanguages("en-CA,en;q=0.9,en-GB;q=0.8,en-US;q=0.7,fr;q=0.6");
        fingerPrint.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
        FingerPrint fingerPrint2 = new FingerPrint();
        fingerPrint2.setCanvasFingerprint("6101AE99");
        fingerPrint2.setScreenHeight(1440);
        fingerPrint2.setScreenWidth(2560);
        fingerPrint2.setTimezone("America/Toronto");
        fingerPrint2.setAcceptLanguages("en-CA,en;q=0.9,en-GB;q=0.8,en-US;q=0.7,fr;q=0.6");
        fingerPrint2.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
        String hash1 = Serializer.serialize(fingerPrint);
        String hash2 = Serializer.serialize(fingerPrint2);

        assertEquals("The element should have the same hash", hash1, hash2);
    }

    @Test
    public void testSerializeDifferentInfo() {
        FingerPrint fingerPrint = new FingerPrint();
        fingerPrint.setCanvasFingerprint("6101AE99");
        fingerPrint.setScreenHeight(1080);
        fingerPrint.setScreenWidth(1920);
        fingerPrint.setTimezone("America/Toronto");
        fingerPrint.setAcceptLanguages("en-CA,en;q=0.8,en-GB;q=0.7,en-US;q=0.6,fr;q=0.5");
        fingerPrint.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
        FingerPrint fingerPrint2 = new FingerPrint();
        fingerPrint2.setCanvasFingerprint("6101AE99");
        fingerPrint2.setScreenHeight(1440);
        fingerPrint2.setScreenWidth(2560);
        fingerPrint2.setTimezone("America/Toronto");
        fingerPrint2.setAcceptLanguages("en-CA,en;q=0.9,en-GB;q=0.8,en-US;q=0.7,fr;q=0.6");
        fingerPrint2.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
        String hash1 = Serializer.serialize(fingerPrint);
        String hash2 = Serializer.serialize(fingerPrint2);

        assertNotEquals("The element should have the same hash", hash1, hash2);
    }

}
