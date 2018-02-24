package com.squidsquads.model.traffic;

import javax.persistence.*;

@Entity
@Table(name = "banniere")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id_banniere")
    private Long bannerId;

    @Column(name="orientation")
    private String orientation;

    public Banner() {
    }

    public Banner(String orientation) {
        this.orientation = orientation;
    }

    public Long getBannerId() {
        return bannerId;
    }

    public String getOrientation() {
        return orientation;
    }
}
