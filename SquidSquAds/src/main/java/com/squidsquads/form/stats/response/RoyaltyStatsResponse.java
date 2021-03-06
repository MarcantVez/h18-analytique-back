package com.squidsquads.form.stats.response;

import com.squidsquads.model.RoyaltyAmount;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class RoyaltyStatsResponse {

    private HttpStatus status;
    private List<RoyaltyAmountDTO> royaltyStats;
    
    public RoyaltyStatsResponse ok(List<RoyaltyAmount> royaltyStats) {
        status = HttpStatus.OK;
        this.royaltyStats = new ArrayList<>();
        for(RoyaltyAmount amount : royaltyStats){
            this.royaltyStats.add(new RoyaltyAmountDTO(amount));
        }
        return this;
    }

    public RoyaltyStatsResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }
    public List<RoyaltyAmountDTO> getRoyaltyStats() {return royaltyStats;}
}
