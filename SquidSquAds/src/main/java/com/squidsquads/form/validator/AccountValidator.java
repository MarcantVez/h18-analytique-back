package com.squidsquads.form.validator;

import com.squidsquads.form.account.request.CreateRequest;
import com.squidsquads.model.AdminType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountValidator {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public static boolean isCreateRequestComplete(CreateRequest req) {

        // Champs requis pour les deux types d'administrateur
        return CommonValidator.notEmpty(req.getAdminType())
                && CommonValidator.notEmpty(req.getEmail())
                && CommonValidator.notEmpty(req.getBank())
                && CommonValidator.notEmpty(req.getPassword())
                && CommonValidator.notEmpty(req.getConfirmPassword())
                // Domain requis pour l'administrateur Web
                && (!AdminType.WEB.name().equals(req.getAdminType()) || CommonValidator.notEmpty(req.getDomain()));
    }

    public static boolean isBankAccountValid(String bankAccount) {
        return Pattern.matches("^[0-9]{8}$", bankAccount);
    }

    public static boolean isEmailValid(String email) {
        if(email.length() <= 100){
            return validateEmail(email);
        } else {
            return false;
        }

    }

    public static boolean isDomainLengthValid(String domain) {
        return domain.length() <= 200;
    }

    public static boolean isPasswordLengthValid(String password) {
        return password.length() <= 60;
    }
}
