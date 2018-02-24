package com.squidsquads.form.account.response;

public class ListBannerResponseItem {

    private final Long id;
    private final String orientation;

    public ListBannerResponseItem(Long id, String orientation) {
        this.id = id;
        this.orientation = orientation;
    }

    public Long getId() {
        return id;
    }

    public String getOrientation() {
        return orientation;
    }
}
