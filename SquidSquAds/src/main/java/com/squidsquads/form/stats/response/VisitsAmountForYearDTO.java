package com.squidsquads.form.stats.response;

import com.squidsquads.model.VisitsAmountForYear;
import com.squidsquads.utils.DateFormatter;

public class VisitsAmountForYearDTO {

    private Integer monthIndex;
    private String month;
    private Integer year;
    private Integer amount;

    public VisitsAmountForYearDTO(VisitsAmountForYear visitForYear) {
        this.monthIndex = visitForYear.getMonthOfYear();
        this.amount = visitForYear.getAmount();
        this.year = visitForYear.getYear();
        this.month = DateFormatter.getMonthStringFromIndex(visitForYear.getMonthOfYear());
    }

    public Integer getMonthIndex() {
        return monthIndex;
    }

    public String getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getAmount() {
        return amount;
    }
}
