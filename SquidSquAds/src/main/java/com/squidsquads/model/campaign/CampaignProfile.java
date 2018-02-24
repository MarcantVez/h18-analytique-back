package com.squidsquads.model.campaign;

import javax.persistence.*;

@Entity
@Table(name = "campagne_profildutilisateur")
public class CampaignProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id_Campagne_ProfilDUtilisateur")
    private Long campaignProfileID;

    @Column(name = "id_profildutilisateur")
    private Long profileID;

    @Column(name = "id_campagne")
    private Long campaignID;

    public CampaignProfile() {
    }

    public CampaignProfile(Long profileID, Long campaignID) {
        this.profileID = profileID;
        this.campaignID = campaignID;
    }

    public Long getCampaignProfileID() {
        return campaignProfileID;
    }

    public void setCampaignProfileID(Long campaignProfileID) {
        this.campaignProfileID = campaignProfileID;
    }

    public Long getProfileID() {
        return profileID;
    }

    public void setProfileID(Long profileID) {
        this.profileID = profileID;
    }

    public Long getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(Long campaignID) {
        this.campaignID = campaignID;
    }
}
