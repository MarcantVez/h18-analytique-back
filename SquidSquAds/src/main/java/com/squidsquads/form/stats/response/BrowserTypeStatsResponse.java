package com.squidsquads.form.stats.response;

import com.squidsquads.model.stats.BrowserTypesItem;
import org.springframework.http.HttpStatus;

import java.util.List;

public class BrowserTypeStatsResponse {
    private HttpStatus status;
    private List<BrowserTypesItem> browserStats;

    public BrowserTypeStatsResponse(){}

    public BrowserTypeStatsResponse ok(List<BrowserTypesItem> browserTypeList) {
        status = HttpStatus.OK;
        browserStats = browserTypeList;
        return this;
    }

    public BrowserTypeStatsResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<BrowserTypesItem> getBrowserStats() {
        return browserStats;
    }

    public void setBrowserStats(List<BrowserTypesItem> browserStats) {
        this.browserStats = browserStats;
    }
}

