package form.validator;

public class CommonValidator {

    public static boolean notEmpty(final String s) {
        return s != null && !s.trim().isEmpty();
    }
}
