package com.squidsquads.form.campaign.response;

public class ListResponseItem {

    private final Integer id;
    private final String name;
    private final String dateCreated;

    public ListResponseItem(Integer id, String name, String dateCreated) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateCreated() {
        return dateCreated;
    }
}
