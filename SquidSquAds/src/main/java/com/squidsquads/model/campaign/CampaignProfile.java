package com.squidsquads.model.campaign;

import javax.persistence.*;

@Entity
@Table(name = "campagne_profilutilisateur")
public class CampaignProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Id_Campagne_ProfilUtilisateur")
    private Long campaignProfileID;

    @Column(name = "id_profilutilisateur")
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
