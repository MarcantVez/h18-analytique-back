package com.squidsquads.service.visit;

import com.blueconic.browscap.*;
import com.squidsquads.form.visit.response.CookieCreationResponse;
import com.squidsquads.form.visit.response.VisitResponse;
import com.squidsquads.model.traffic.BrowserType;
import com.squidsquads.model.traffic.BrowserTypeAgent;
import com.squidsquads.model.traffic.TrackingInfo;
import com.squidsquads.model.traffic.UserAgent;
import com.squidsquads.repository.visit.BrowserTypeAgentRepository;
import com.squidsquads.repository.visit.BrowserTypeRepository;
import com.squidsquads.repository.visit.TrackingInfoRepository;
import com.squidsquads.repository.visit.UserAgentRepository;
import com.squidsquads.service.campaign.CampaignService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Enumeration;

@Service
public class VisitService {
    private UserAgentParser parser;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private BrowserTypeAgentRepository browserTypeAgentRepository;
    @Autowired
    private BrowserTypeRepository browserTypeRepository;
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
            String strUserAgent = request.getHeader("User-Agent");
            String acceptLanguage = request.getHeader("accept-language");
            String remoteIPv4Addr = request.getRemoteAddr(); // requires option -Djava.net.preferIPv4Stack=true for IPV4, sinon pas constant
            Capabilities capabilities = parser.parse(strUserAgent);
            String browser = capabilities.getBrowser();
            String browserType = capabilities.getBrowserType();
            String browserVersion = capabilities.getValue(BrowsCapField.BROWSER_VERSION);
            String deviceType = capabilities.getDeviceType();
            String platform = capabilities.getPlatform();
            String platformVersion = capabilities.getPlatformVersion();

            BrowserType type = browserTypeRepository.findByName(browser);
            if (type == null) {
                type = browserTypeRepository.save(new BrowserType(browser));
            }

            TrackingInfo info = new TrackingInfo(
                    1L, // TODO add AdminSiteWebID ?
                    null, // TODO calculer l'empreinte
                    request.getHeader("host"),
                    null, // TODO add previous url in requests headers
                    remoteIPv4Addr,
                    null, // TODO peut seulement avoir un ou l'autre...
                    null, // TODO Screen size not in http
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
                        platform,
                        browserType,
                        deviceType,
                        platformVersion,
                        null // TODO on a pas les infos des plugins par http...
                );
                agent = userAgentRepository.save(agent);
            }

            BrowserTypeAgent browserTypeAgent = new BrowserTypeAgent(type.getBrowserTypeId(), agent.getId());
            browserTypeAgentRepository.save(browserTypeAgent);
        }
        return new VisitResponse().ok();
    }

    public CookieCreationResponse createIdentity() {
        String fingerPrintContent = "";
        String fingerprint = DigestUtils.md5Hex(fingerPrintContent).toUpperCase();
        return new CookieCreationResponse().ok(fingerprint);
    }
}
