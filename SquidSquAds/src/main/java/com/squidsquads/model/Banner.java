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

    public Integer getBannerId() {
        return bannerID;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public String getOrientation() {
        return orientation;
    }
}
