package com.squidsquads.form.userProfile.response;

public class ListResponseItem {

    private final Long id;
    private final String name;
    private final String dateCreated;

    public ListResponseItem(Long id, String name, String dateCreated) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateCreated() {
        return dateCreated;
    }
}
