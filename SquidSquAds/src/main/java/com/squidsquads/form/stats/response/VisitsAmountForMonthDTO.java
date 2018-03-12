package com.squidsquads.form.stats.response;

import com.squidsquads.model.VisitsAmountForMonth;
import com.squidsquads.utils.DateFormatter;

public class VisitsAmountForMonthDTO {

    private Integer monthIndex;
    private String month;
    private Integer day;
    private Integer amount;

    public VisitsAmountForMonthDTO(VisitsAmountForMonth visitForMonth) {
        this.day = visitForMonth.getDayOfMonth();
        this.amount = visitForMonth.getAmount();
        this.monthIndex = visitForMonth.getMonthOfYear();
        this.month = DateFormatter.getMonthStringFromIndex(visitForMonth.getMonthOfYear());
    }

    public String getMonth() {
        return month;
    }

    public Integer getMonthIndex() {
        return monthIndex;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getAmount() {
        return amount;
    }
}
