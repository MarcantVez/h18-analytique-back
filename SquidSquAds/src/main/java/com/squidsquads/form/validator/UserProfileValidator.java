package com.squidsquads.form.validator;

import com.squidsquads.form.userProfile.request.CreateModifyRequest;

public class UserProfileValidator {
    private static final int NAME_MAX_LEN = 100;
    private static final int DESC_MAX_LEN = 200;
    private static final int URLS_MAX_LEN = 100;


    public static boolean isUserProfileRequestComplete(CreateModifyRequest req) {

        return CommonValidator.notEmpty(req.getDescription())
                && CommonValidator.notEmpty(req.getName())
                && (req.getUrls() != null)
                && (req.getUrls().length > 0);
    }

    public static boolean isURLValid(String url) {

        // Valider un URL est très difficile. Le regex ci-dessous ne valide pas les URLS qui contiennent des query
        // string ou des '#'. Voir avec les PO si cette vérification est utile et requise.
        return CommonValidator.notEmpty(url);
        /* Regex found at: https://mathiasbynens.be/demo/url-regex
        return Pattern.matches("_^(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})" +
                "(?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})(?!192\\.168(?:\\.\\d{1,3}){2})" +
                "(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])" +
                "(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|" +
                "(?:(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)(?:\\." +
                "(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)*(?:\\." +
                "(?:[a-z\\x{00a1}-\\x{ffff}]{2,})))(?::\\d{2,5})?(?:/[^\\s]*)?$_iuS", url);
                */
    }

    public static boolean isNameFormatValid(String name) {
        return name.length() <= NAME_MAX_LEN;
    }

    public static boolean isDescFormatValid(String desc) {
        return desc.length() <= DESC_MAX_LEN;
    }

    public static boolean isUrlFormatValid(String[] urls) {
        for (String url : urls){
            if (url.length() > URLS_MAX_LEN)
                return false;
        }
        return true;
    }
}
