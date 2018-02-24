package com.squidsquads.service.visit;

import com.blueconic.browscap.*;
import com.squidsquads.form.visit.request.VisitRequest;
import com.squidsquads.form.visit.response.CookieCreationResponse;
import com.squidsquads.form.visit.response.VisitResponse;
import com.squidsquads.model.traffic.FingerPrint;
import com.squidsquads.model.traffic.TrackingInfo;
import com.squidsquads.model.traffic.UserAgent;
import com.squidsquads.repository.visit.TrackingInfoRepository;
import com.squidsquads.repository.visit.UserAgentRepository;
import com.squidsquads.utils.Serializer;
import com.squidsquads.utils.TimeSpentCalculator;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class VisitService {
    private UserAgentParser parser;

    @Autowired
    private TimeSpentCalculator calculator;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TrackingInfoRepository trackingInfoRepository;
    @Autowired
    private UserAgentRepository userAgentRepository;

    public VisitService() throws IOException, ParseException {
        parser = new UserAgentService()
            .loadParser(
                Arrays.asList(
                        BrowsCapField.BROWSER,
                        BrowsCapField.BROWSER_TYPE,
                        BrowsCapField.BROWSER_VERSION,
                        BrowsCapField.DEVICE_TYPE,
                        BrowsCapField.DEVICE_BRAND_NAME,
                        BrowsCapField.DEVICE_NAME,
                        BrowsCapField.PLATFORM,
                        BrowsCapField.PLATFORM_VERSION,
                        BrowsCapField.PLATFORM_MAKER
                )
        );
    }

    public VisitResponse processVisit(){
        // check if cookie, if not : ignore...
        String fingerprint = request.getHeader("cookie");
        if(fingerprint != null) {
            String strUserAgent = request.getHeader("User-Agent");
            String acceptLanguage = request.getHeader("accept-language");
            UserAgent targetAgent = userAgentRepository.findByUserAgentString(strUserAgent);
            TrackingInfo lastInfo = trackingInfoRepository.findFirstByFingerprintOrderByDateTimeDesc(fingerprint);

            if(targetAgent == null) {
                targetAgent = createAgent(strUserAgent);
            }

            TrackingInfo info;

            if(lastInfo != null){
                info = new TrackingInfo(
                        1L, // TODO add AdminSiteWebID ?
                        targetAgent.getId(),
                        fingerprint,
                        request.getHeader("host"),
                        lastInfo.getCurrentUrl(),
                        lastInfo.getIpv4Address(),
                        null, // TODO peut seulement avoir un ou l'autre...
                        lastInfo.getScreenSize(),
                        acceptLanguage,
                        calculator.calculateTimeFromNow(lastInfo.getDateTime())
                );
            } else {
                info = new TrackingInfo(
                        1L, // TODO add AdminSiteWebID ?
                        targetAgent.getId(),
                        fingerprint,
                        request.getHeader("host"),
                        null,
                        request.getRemoteAddr(),
                        null, // TODO peut seulement avoir un ou l'autre...
                        null,
                        acceptLanguage,
                        0
                );
            }

            trackingInfoRepository.save(info);
        }
        return new VisitResponse().ok();
    }

    public CookieCreationResponse createIdentity(VisitRequest visitRequest) {
        String strUserAgent = request.getHeader("User-Agent");
        String acceptLanguage = request.getHeader("accept-language");
        String remoteIPv4Addr = request.getRemoteAddr(); // requires option -Djava.net.preferIPv4Stack=true for IPV4, sinon pas constant

        FingerPrint fingerprint = new FingerPrint(
            visitRequest.getScreenWidth(), visitRequest.getScreenHeight(), visitRequest.getCanvasFingerprint(), visitRequest.getTimezone(), strUserAgent, acceptLanguage
        );
        String fingerPrintHash = Serializer.serialize(fingerprint);

        UserAgent agent = userAgentRepository.findByUserAgentString(strUserAgent);
        if (agent == null) {
            agent = createAgent(strUserAgent);
        }

        TrackingInfo info = new TrackingInfo(
                1L, // TODO add AdminSiteWebID ?
                agent.getId(),
                fingerPrintHash,
                request.getHeader("host"),
                null, // TODO null because first visit ?
                remoteIPv4Addr,
                null,
                visitRequest.getScreenSize(),
                acceptLanguage,
                1 // TODO cannot know time spent...
        );
        trackingInfoRepository.save(info);
        return new CookieCreationResponse().ok(fingerPrintHash);
    }

    /**
     * Utility method to create user agent object from string
     * @param strUserAgent the user agent string
     * @return a UserAgent object
     */
    private UserAgent createAgent(String strUserAgent) {
        Capabilities capabilities = parser.parse(strUserAgent);

        UserAgent agent = new UserAgent(
                strUserAgent,
                capabilities.getValue(BrowsCapField.BROWSER_VERSION),
                capabilities.getBrowser(),
                capabilities.getPlatform(),
                capabilities.getBrowserType(),
                capabilities.getDeviceType(),
                capabilities.getPlatformVersion(),
                null // TODO on a pas les infos des plugins par http...
        );

        return  userAgentRepository.save(agent);
    }
}
