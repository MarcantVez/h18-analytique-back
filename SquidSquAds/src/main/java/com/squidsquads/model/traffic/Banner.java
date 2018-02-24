package com.squidsquads.model.traffic;

import javax.persistence.*;

@Entity
@Table(name = "banniere")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_banniere")
    private Long bannerId;

    @Column(name = "orientation")
    private String orientation;

    @Column(name = "id_compte")
    private long accountID;

    public Banner() {
    }

    public Banner(Long accountID, String orientation) {
        this.accountID = accountID;
        this.orientation = orientation;
    }

    public Long getBannerId() {
        return bannerId;
    }

    public Long getAccountID() {
        return accountID;
    }

    public String getOrientation() {
        return orientation;
    }
}
