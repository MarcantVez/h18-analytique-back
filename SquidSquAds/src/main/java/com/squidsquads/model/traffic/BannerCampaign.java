package com.squidsquads.model.traffic;

import javax.persistence.*;

@Entity
@Table(name = "banniere_campagne")
public class BannerCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_banniere_campagne")
    private Long bannerCampaignId;

    @Column(name = "id_banniere")
    private Long bannerID;

    @Column(name = "id_campagne")
    private Long campaignID;

    public BannerCampaign() {
    }

    public BannerCampaign(Long bannerID, Long campaignID) {
        this.bannerID = bannerID;
        this.campaignID = campaignID;
    }

    public Long getBannerCampaignId() {
        return bannerCampaignId;
    }

    public Long getBannerID() {
        return bannerID;
    }

    public Long getCampaignID() {
        return campaignID;
    }
}
