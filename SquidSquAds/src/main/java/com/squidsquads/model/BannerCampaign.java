package com.squidsquads.model;

import javax.persistence.*;

@Entity
@Table(name = "banniere_campagne")
public class BannerCampaign {

    @Id
    @SequenceGenerator(name = "banniere_campagne_id_banniere_campagne_seq", sequenceName = "banniere_campagne_id_banniere_campagne_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "banniere_campagne_id_banniere_campagne_seq")
    @Column(name = "id_banniere_campagne")
    private Integer bannerCampaignId;

    @Column(name = "id_banniere")
    private Integer bannerID;

    @Column(name = "id_campagne")
    private Integer campaignID;

    public BannerCampaign() {
    }

    public BannerCampaign(Integer bannerID, Integer campaignID) {
        this.bannerID = bannerID;
        this.campaignID = campaignID;
    }

    public Integer getBannerCampaignId() {
        return bannerCampaignId;
    }

    public Integer getBannerID() {
        return bannerID;
    }

    public Integer getCampaignID() {
        return campaignID;
    }
}
