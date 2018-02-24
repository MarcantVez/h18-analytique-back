package com.squidsquads.model.traffic;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visite")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id_visite")
    private Long visitId;

    @Column(name="est_Cliquee")
    private boolean estCliquee;

    @Column(name="est_Ciblee")
    private boolean estCiblee;

    @Column(name="date_heure")
    private LocalDateTime dateTime;

    public Visit() {
    }

    public Visit(boolean estCliquee, boolean estCiblee) {
        this.estCliquee = estCliquee;
        this.estCiblee = estCiblee;
        this.dateTime = LocalDateTime.now();
    }

    public Long getVisitId() {
        return visitId;
    }

    public boolean getEstCiblee() {
        return estCiblee;
    }

    public boolean getEstCliquee() {
        return estCliquee;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
