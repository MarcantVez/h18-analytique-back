package com.squidsquads.model;

import javax.persistence.*;

@Entity
@Table(name = "campagne_profilutilisateur")
public class CampaignProfile {

    @Id
    @SequenceGenerator(name = "campagne_profilutilisateur_id_campagne_profilutilisateur_seq",
            sequenceName = "campagne_profilutilisateur_id_campagne_profilutilisateur_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campagne_profilutilisateur_id_campagne_profilutilisateur_seq")
    @Column(name = "id_campagne_profilutilisateur")
    private Integer campaignProfileID;

    @Column(name = "id_profilutilisateur")
    private Integer profileID;

    @Column(name = "id_campagne")
    private Integer campaignID;

    public CampaignProfile() {
    }

    public CampaignProfile(Integer profileID, Integer campaignID) {
        this.profileID = profileID;
        this.campaignID = campaignID;
    }

    public Integer getCampaignProfileID() {
        return campaignProfileID;
    }

    public void setCampaignProfileID(Integer campaignProfileID) {
        this.campaignProfileID = campaignProfileID;
    }

    public Integer getProfileID() {
        return profileID;
    }

    public void setProfileID(Integer profileID) {
        this.profileID = profileID;
    }

    public Integer getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(Integer campaignID) {
        this.campaignID = campaignID;
    }
}
