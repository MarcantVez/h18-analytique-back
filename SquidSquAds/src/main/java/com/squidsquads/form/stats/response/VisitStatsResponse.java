package com.squidsquads.form.stats.response;

import com.squidsquads.model.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class VisitStatsResponse {
    private HttpStatus status;

    private List<VisitsAmountForDayDTO> for24hList;
    private List<VisitsAmountForWeekDTO> forWeekList;
    private List<VisitsAmountForMonthDTO> forMonthList;
    private List<VisitsAmountForYearDTO> forYearList;


    public VisitStatsResponse ok(List<VisitsAmountFor24h> for24hList,
                                 List<VisitsAmountForWeek> forWeekList,
                                 List<VisitsAmountForMonth> forMonthList,
                                 List<VisitsAmountForYear> forYearList) {
        status = HttpStatus.OK;
        this.for24hList = new ArrayList<>();
        for(VisitsAmountFor24h visitsForDay : for24hList){
            this.for24hList.add(new VisitsAmountForDayDTO(visitsForDay));
        }
        this.forWeekList = new ArrayList<>();
        for(VisitsAmountForWeek visitsForWeek : forWeekList){
            this.forWeekList.add(new VisitsAmountForWeekDTO(visitsForWeek));
        }
        this.forMonthList = new ArrayList<>();
        for(VisitsAmountForMonth visitForMonth : forMonthList){
            this.forMonthList.add(new VisitsAmountForMonthDTO(visitForMonth));
        }
        this.forYearList = new ArrayList<>();
        for(VisitsAmountForYear visitsForYear : forYearList){
            this.forYearList.add(new VisitsAmountForYearDTO(visitsForYear));
        }
        return this;
    }

    public VisitStatsResponse failed() {
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<VisitsAmountForDayDTO> getFor24hList() {
        return for24hList;
    }

    public List<VisitsAmountForWeekDTO> getForWeekList() {
        return forWeekList;
    }

    public List<VisitsAmountForMonthDTO> getForMonthList() {
        return forMonthList;
    }

    public List<VisitsAmountForYearDTO> getForYearList() {
        return forYearList;
    }
}
