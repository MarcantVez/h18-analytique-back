package com.squidsquads.model;

import javax.persistence.*;

@Entity
@Table(name = "banniere")
public class Banner {

    @Id
    @SequenceGenerator(name = "banniere_id_banniere_seq", sequenceName = "banniere_id_banniere_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banniere_id_banniere_seq")
    @Column(name = "id_banniere")
    private Integer bannerID;

    @Column(name = "orientation")
    private String orientation;

    @Column(name = "id_compte")
    private Integer accountID;

    public Banner() {
    }

    public Banner(Integer accountID, String orientation) {
        this.accountID = accountID;
        this.orientation = orientation;
    }

    public Integer getBannerID() {
        return bannerID;
    }

    public void setBannerID(Integer bannerID) {
        this.bannerID = bannerID;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }
}
