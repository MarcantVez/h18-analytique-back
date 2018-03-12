package com.squidsquads.form.stats.response;

import com.squidsquads.model.VisitsAmountForWeek;
import com.squidsquads.utils.DateFormatter;

public class VisitsAmountForWeekDTO {
    private String dayOfweek;
    private Integer amount;

    public VisitsAmountForWeekDTO(VisitsAmountForWeek visitsAmountForWeek) {
        this.dayOfweek = DateFormatter.getDayOfWeekStringFromIndex(visitsAmountForWeek.getDayOfweek());
        this.amount = visitsAmountForWeek.getAmount();
    }

    public String getDayOfweek() {
        return dayOfweek;
    }

    public Integer getAmount() {
        return amount;
    }
}
