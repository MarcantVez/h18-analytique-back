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
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@Service
public class VisitService {
    private UserAgentParser parser;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TrackingInfoRepository trackingInfoRepository;
    @Autowired
    private UserAgentRepository userAgentRepository;

    public VisitService() throws IOException, ParseException {
        parser = new UserAgentService().loadParser(
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
        String cookie = request.getHeader("cookie");
        if(cookie != null) {
            processRequest();
        }
        return new VisitResponse().ok();
    }

    public CookieCreationResponse createIdentity(VisitRequest visitRequest) {
        String strUserAgent = request.getHeader("User-Agent");
        String acceptLanguage = request.getHeader("accept-language");
        FingerPrint fingerprint = new FingerPrint(
            visitRequest.getScreenWidth(), visitRequest.getScreenHeight(), visitRequest.getCanvasFingerprint(), visitRequest.getTimezone(), strUserAgent, acceptLanguage
        );
        String fingerPrintHash = Serializer.serialize(fingerprint);

        String remoteIPv4Addr = request.getRemoteAddr(); // requires option -Djava.net.preferIPv4Stack=true for IPV4, sinon pas constant
        Capabilities capabilities = parser.parse(strUserAgent);
        String browser = capabilities.getBrowser();
        String browserType = capabilities.getBrowserType();
        String browserVersion = capabilities.getValue(BrowsCapField.BROWSER_VERSION);
        String deviceType = capabilities.getDeviceType();
        String platform = capabilities.getPlatform();
        String platformVersion = capabilities.getPlatformVersion();

        TrackingInfo info = new TrackingInfo(
                1L, // TODO add AdminSiteWebID ?
                fingerPrintHash,
                request.getHeader("host"),
                null, // TODO find last url from that fingerprint
                remoteIPv4Addr,
                null, // TODO peut seulement avoir un ou l'autre...
                visitRequest.getScreenSize(),
                acceptLanguage,
                1 // TODO cannot know time spent...
        );
        info = trackingInfoRepository.save(info);

        UserAgent agent = userAgentRepository.findByUserAgentString(strUserAgent);
        if (agent == null) {
            agent = new UserAgent(
                    info.getTrackingInfoId(),
                    strUserAgent,
                    browserVersion,
                    browser,
                    platform,
                    browserType,
                    deviceType,
                    platformVersion,
                    null // TODO on a pas les infos des plugins par http...
            );
            agent = userAgentRepository.save(agent);
        }
        return new CookieCreationResponse().ok(fingerPrintHash);
    }


    private void processRequest(){

    }
}
