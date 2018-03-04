package com.squidsquads.service;

import com.blueconic.browscap.*;
import com.squidsquads.form.visit.request.VisitRequest;
import com.squidsquads.form.visit.response.CookieCreationResponse;
import com.squidsquads.form.visit.response.VisitResponse;
import com.squidsquads.model.Fingerprint;
import com.squidsquads.model.TrackingInfo;
import com.squidsquads.model.UserAgent;
import com.squidsquads.repository.TrackingInfoRepository;
import com.squidsquads.repository.UserAgentRepository;
import com.squidsquads.utils.Serializer;
import com.squidsquads.utils.TimeSpentCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@Service
public class VisitService {

    private static final Logger logger = LoggerFactory.getLogger(VisitService.class);

    public static final String SQUIDSQUADS_COOKIE = "_squidsquads";
    private static final String HEADER_USER_AGENT = "User-Agent";
    private static final String HEADER_ACCEPT_LANGUAGE = "accept-language";
    private static final String HEADER_REFERER = "referer";

    private UserAgentParser parser;

    @Autowired
    private TimeSpentCalculator calculator;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TrackingInfoRepository trackingInfoRepository;

    @Autowired
    private UserAgentRepository userAgentRepository;

    public VisitService() {

        try {
            instantiateParser();
        } catch (IOException | ParseException e) {
            logger.error(e.getMessage());
        }

    }

    private void instantiateParser() throws IOException, ParseException {
        parser = new UserAgentService().loadParser(Arrays.asList(
                BrowsCapField.BROWSER,
                BrowsCapField.BROWSER_TYPE,
                BrowsCapField.BROWSER_VERSION,
                BrowsCapField.DEVICE_TYPE,
                BrowsCapField.DEVICE_BRAND_NAME,
                BrowsCapField.DEVICE_NAME,
                BrowsCapField.PLATFORM,
                BrowsCapField.PLATFORM_VERSION,
                BrowsCapField.PLATFORM_MAKER
        ));
    }

    /**
     * Lorsqu'un visiteur se rend sur une page qui contient notre processus de tracking,
     * un GET est envoyé au serveur. Cette méthode traite ce GET en question pour faire
     * de l'historique de navigation pour l'administrateur du site Web.
     */
    public VisitResponse processVisit(Integer siteWebAdminID) {

        // Vérifier la présence du cookie de tracking
        Cookie cookie = WebUtils.getCookie(request, SQUIDSQUADS_COOKIE);

        // Si le cookie n'est pas présent, c'est que l'empreinte du visiteur n'a pas encore
        // été générée. Le traitement se fera alors dans createIdentity suite à un POST.
        if (cookie == null) {
            return new VisitResponse().ok();
        }

        // Récupérer le cookie et des headers dans la requête
        String fingerprint = cookie.getValue();
        String hdrUserAgent = request.getHeader(HEADER_USER_AGENT);
        String hdrAcceptLanguage = request.getHeader(HEADER_ACCEPT_LANGUAGE);
        String hdrReferer = request.getHeader(HEADER_REFERER);
        String hdrRemoteAddr = request.getRemoteAddr();

        // Il se peut que l'enregistrement de l'AgentUtilisateur soit déjà présent si ce
        // n'est pas la première fois qu'on track le visiteur.
        UserAgent targetAgent = userAgentRepository.findByUserAgentString(hdrUserAgent);

        // Si le fingerprint existe déjà, poursuivre le tracking de l'utilisateur
        TrackingInfo lastInfo = trackingInfoRepository.findFirstByFingerprintOrderByDateTimeDesc(fingerprint);

        if (targetAgent == null) {
            targetAgent = createAgent(hdrUserAgent);
        }

        TrackingInfo info;

        String previousUrl = null;
        String screenSize = null;
        int timeSpent = 1;

        // Lier le tracking entre les différentes pages du site
        if (lastInfo != null) {
            previousUrl = lastInfo.getCurrentUrl();
            screenSize = lastInfo.getScreenSize();
            timeSpent = calculator.calculateTimeFromNow(lastInfo.getDateTime());
        }

        info = new TrackingInfo(
                siteWebAdminID,
                targetAgent.getId(),
                fingerprint,
                hdrReferer,
                previousUrl,
                hdrRemoteAddr,
                null,
                screenSize,
                hdrAcceptLanguage,
                timeSpent
        );

        trackingInfoRepository.save(info);

        return new VisitResponse().ok();
    }

    /**
     * Lorsqu'un visiteur se rend sur une page qui contient notre processus de tracking,
     * un POST est envoyé au serveur si l'utilisateur n'a pas le cookie de tracking.
     */
    public CookieCreationResponse createIdentity(VisitRequest visitRequest) {

        // Récupérer des headers dans la requête
        String hdrUserAgent = request.getHeader(HEADER_USER_AGENT);
        String hdrAcceptLanguage = request.getHeader(HEADER_ACCEPT_LANGUAGE);
        String hdrReferer = request.getHeader(HEADER_REFERER);
        String hdrRemoteAddr = request.getRemoteAddr(); // requires option -Djava.net.preferIPv4Stack=true for IPV4, sinon pas constant

        // Bâtir le fingerprint de l'utilisateur
        Fingerprint fingerprint = new Fingerprint(
                visitRequest.getScreenWidth(),
                visitRequest.getScreenHeight(),
                visitRequest.getCanvasFingerprint(),
                visitRequest.getTimezone(),
                hdrUserAgent,
                hdrAcceptLanguage
        );

        // Sérialiser ce fingerprint pour qu'il soit inséré dans les cookies du visiteur
        String fingerPrintHash = Serializer.serialize(fingerprint);

        // Il se peut que l'enregistrement de l'AgentUtilisateur soit déjà présent si ce
        // n'est pas la première fois qu'on track le visiteur.
        UserAgent targetAgent = userAgentRepository.findByUserAgentString(hdrUserAgent);

        // Si le fingerprint existe déjà, poursuivre le tracking de l'utilisateur
        TrackingInfo lastInfo = trackingInfoRepository.findFirstByFingerprintOrderByDateTimeDesc(fingerPrintHash);

        if (targetAgent == null) {
            targetAgent = createAgent(hdrUserAgent);
        }

        String previousUrl = null;
        int timeSpent = 1;

        // Lier le tracking entre les différentes pages du site
        if (lastInfo != null) {
            previousUrl = lastInfo.getCurrentUrl();
            timeSpent = calculator.calculateTimeFromNow(lastInfo.getDateTime());
        }

        TrackingInfo info = new TrackingInfo(
                visitRequest.getUserId(),
                targetAgent.getId(),
                fingerPrintHash,
                hdrReferer,
                previousUrl,
                hdrRemoteAddr,
                null,
                visitRequest.getScreenSize(),
                hdrAcceptLanguage,
                timeSpent
        );

        trackingInfoRepository.save(info);

        return new CookieCreationResponse().ok(fingerPrintHash);
    }

    /**
     * Utility method to create user agent object from string
     *
     * @param hdrUserAgent the user agent string
     * @return a UserAgent object
     */
    private UserAgent createAgent(String hdrUserAgent) {

        Capabilities capabilities = parser.parse(hdrUserAgent);

        UserAgent agent = new UserAgent(
                hdrUserAgent,
                capabilities.getValue(BrowsCapField.BROWSER_VERSION),
                capabilities.getBrowser(),
                capabilities.getPlatform(),
                capabilities.getBrowserType(),
                capabilities.getDeviceType(),
                capabilities.getPlatformVersion(),
                null // TODO on a pas les infos des plugins par http...
        );

        return userAgentRepository.save(agent);
    }
}
