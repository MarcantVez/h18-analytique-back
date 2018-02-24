package com.squidsquads.form.visit.request;

public class VisitRequest {
    private boolean doNotTrack;
    private int width;
    private int height;
    private boolean cookieEnabled;
    private String timezone;
    private String canvasFingerprint;

    public String getCanvasFingerprint() {
        return canvasFingerprint;
    }

    public boolean isDoNotTrack() {
        return doNotTrack;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isCookieEnabled() {
        return cookieEnabled;
    }

    public String getTimezone() {
        return timezone;
    }
}

