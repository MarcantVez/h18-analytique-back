package com.squidsquads.form.visit.request;

public class VisitRequest {
    private boolean doNotTrack;
    private int screenWidth;
    private int screenHeight;
    private boolean cookieEnabled;
    private String canvasFingerprint;
    private String timezone;

    public boolean isDoNotTrack() {
        return doNotTrack;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public boolean isCookieEnabled() {
        return cookieEnabled;
    }

    public String getCanvasFingerprint() {
        return canvasFingerprint;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setDoNotTrack(boolean doNotTrack) {
        this.doNotTrack = doNotTrack;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public void setCookieEnabled(boolean cookieEnabled) {
        this.cookieEnabled = cookieEnabled;
    }

    public void setCanvasFingerprint(String canvasFingerprint) {
        this.canvasFingerprint = canvasFingerprint;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getScreenSize(){
        return this.screenWidth + "x" + this.screenHeight;
    }
}

