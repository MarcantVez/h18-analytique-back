package com.squidsquads.unit.utils;

import com.squidsquads.utils.TimeSpentCalculator;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TimeSpentCalculatorTest {

    private TimeSpentCalculator calculator = new TimeSpentCalculator();

    @Test
    public void testCalculateTimeFromNow() {
        try {
            Instant instant1 = Instant.now();
            Timestamp timestamp1 = Timestamp.from(instant1);
            TimeUnit.SECONDS.sleep(3);
            assertEquals(3, calculator.calculateTimeFromNow(timestamp1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
