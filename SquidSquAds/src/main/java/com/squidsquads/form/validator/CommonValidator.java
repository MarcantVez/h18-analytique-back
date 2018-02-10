package com.squidsquads.form.validator;

public class CommonValidator {

    public static boolean notEmpty(final String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean isPositive(final float f) {
        return f > 0;
    }
}
