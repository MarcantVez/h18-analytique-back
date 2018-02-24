package com.squidsquads.model.traffic;

import java.io.Serializable;

public class FingerPrint implements Serializable{
    private int screenWidth;
    private int screenHeight;
    private String canvasFingerprint;
    private String timezone;
    private String userAgentString;
    private String acceptLanguages;

    public FingerPrint(int screenWidth, int screenHeight, String canvasFingerprint, String timezone, String userAgentString, String acceptLanguages) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.canvasFingerprint = canvasFingerprint;
        this.timezone = timezone;
        this.userAgentString = userAgentString;
        this.acceptLanguages = acceptLanguages;
    }

    public FingerPrint() {
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public String getCanvasFingerprint() {
        return canvasFingerprint;
    }

    public void setCanvasFingerprint(String canvasFingerprint) {
        this.canvasFingerprint = canvasFingerprint;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getUserAgentString() {
        return userAgentString;
    }

    public void setUserAgentString(String userAgentString) {
        this.userAgentString = userAgentString;
    }

    public String getAcceptLanguages() {
        return acceptLanguages;
    }

    public void setAcceptLanguages(String acceptLanguages) {
        this.acceptLanguages = acceptLanguages;
    }
}
