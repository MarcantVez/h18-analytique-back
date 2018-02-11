package com.squidsquads.form.validator;

import java.math.BigDecimal;

public class CommonValidator {

    public static boolean notEmpty(final String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean isPositive(final long f) {
        return f > 0;
    }

    public static boolean isPositive(final BigDecimal f) {
        return f.doubleValue() > 0;
    }
}
