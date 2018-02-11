package com.squidsquads.form.validator;

import com.squidsquads.form.userProfile.request.CreateModifyRequest;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-02-06
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
public class UserProfileValidator {

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

}