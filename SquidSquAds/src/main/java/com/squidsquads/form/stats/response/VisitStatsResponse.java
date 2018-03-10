package com.squidsquads.form.stats.response;

import com.squidsquads.model.*;
import org.springframework.http.HttpStatus;

import java.util.List;

public class VisitStatsResponse {
    private HttpStatus status;

    private List<VisitsAmountFor24h> for24hList;
    private List<VisitsAmountForWeek> forWeekList;
    private List<VisitsAmountForMonth> forMonthList;
    private List<VisitsAmountForYear> forYearList;


    public VisitStatsResponse ok(List<VisitsAmountFor24h> for24hList,
                                 List<VisitsAmountForWeek> forWeekList,
                                 List<VisitsAmountForMonth> forMonthList,
                                 List<VisitsAmountForYear> forYearList) {
        status = HttpStatus.OK;
        this.for24hList = for24hList;
        this.forWeekList = forWeekList;
        this.forMonthList = forMonthList;
        this.forYearList = forYearList;
        return this;
    }

    public VisitStatsResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<VisitsAmountFor24h> getFor24hList() {
        return for24hList;
    }

    public List<VisitsAmountForWeek> getForWeekList() {
        return forWeekList;
    }

    public List<VisitsAmountForMonth> getForMonthList() {
        return forMonthList;
    }

    public List<VisitsAmountForYear> getForYearList() {
        return forYearList;
    }
}
