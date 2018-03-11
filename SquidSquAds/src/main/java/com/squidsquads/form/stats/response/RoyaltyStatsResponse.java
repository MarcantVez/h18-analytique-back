package com.squidsquads.form.stats.response;

import com.squidsquads.model.*;
import org.springframework.http.HttpStatus;
import java.util.List;

public class RoyaltyStatsResponse {

    private HttpStatus status;

    private List<RoyaltyAmount> royaltyStats;


    public RoyaltyStatsResponse ok(List<RoyaltyAmount> royaltyStats) {
        status = HttpStatus.OK;
        this.royaltyStats = royaltyStats;
        return this;
    }

    public RoyaltyStatsResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<RoyaltyAmount> royaltyStats() {
        return royaltyStats;
    }

}
