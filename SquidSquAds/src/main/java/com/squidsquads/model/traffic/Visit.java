package com.squidsquads.model.traffic;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visite")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="numero_visite")
    private Long visitId;

    @Column(name="numero_banniere")
    private Long bannerId;

    @Column(name="date_heure")
    private LocalDateTime dateTime;

    public Visit() {
    }

    public Visit(Long bannerId) {
        this.bannerId = bannerId;
        this.dateTime = LocalDateTime.now();
    }

    public Long getVisitId() {
        return visitId;
    }

    public Long getBannerId() {
        return bannerId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
