package com.squidsquads.form.validator;

import com.squidsquads.form.account.request.CreateRequest;
import com.squidsquads.model.account.AdminType;

import java.util.regex.Pattern;

public class AccountValidator {

    public static boolean isCreateRequestComplete(CreateRequest req) {

        // Champs requis pour les deux types d'administrateur
        return CommonValidator.notEmpty(req.getAdminType())
                && CommonValidator.notEmpty(req.getEmail())
                && CommonValidator.notEmpty(req.getBank())
                && CommonValidator.notEmpty(req.getPassword())
                && CommonValidator.notEmpty(req.getConfirmPassword())
                // Domain requis pour l'administrateur Web
                && (AdminType.valueOf(req.getAdminType()) != AdminType.WEB || CommonValidator.notEmpty(req.getDomain()));
    }

    public static boolean isBankAccountValid(String bankAccount) {
        return Pattern.matches("^[0-9]{3}-[0-9]{5}$", bankAccount);
    }
}
