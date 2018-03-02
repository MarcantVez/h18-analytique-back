package com.squidsquads.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "visite")
public class Visit {

    @Id
    @SequenceGenerator(name = "visite_id_visite_seq", sequenceName = "visite_id_visite_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visite_id_visite_seq")
    @Column(name = "id_visite")
    private Integer visitId;

    @Column(name = "est_Cliquee")
    private Boolean estCliquee;

    @Column(name = "est_Ciblee")
    private Boolean estCiblee;

    @Column(name = "date_heure")
    private Timestamp dateTime;

    public Visit() {
    }

    public Visit(Boolean estCliquee, Boolean estCiblee) {
        this.estCliquee = estCliquee;
        this.estCiblee = estCiblee;
        this.dateTime = Timestamp.from(Instant.now());
    }

    public Integer getVisitId() {
        return visitId;
    }

    public Boolean getEstCiblee() {
        return estCiblee;
    }

    public Boolean getEstCliquee() {
        return estCliquee;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }
}
