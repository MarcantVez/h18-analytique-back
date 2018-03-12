package com.squidsquads.form.stats.response;

import com.squidsquads.model.VisitsAmountFor24h;
import com.squidsquads.utils.DateFormatter;

import java.util.Date;

public class VisitsAmountForDayDTO {

    private String timeOfDay;
    private Integer amount;

    public VisitsAmountForDayDTO(VisitsAmountFor24h visitForDay) {
        this.timeOfDay = DateFormatter.getHourFromDate(visitForDay.getTimeOfDay());
        this.amount = visitForDay.getAmount();
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public Integer getAmount() {
        return amount;
    }
}
