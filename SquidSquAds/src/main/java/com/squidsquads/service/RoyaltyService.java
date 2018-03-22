package com.squidsquads.service;

import com.squidsquads.model.Campaign;
import com.squidsquads.model.Royalty;
import com.squidsquads.model.Visit;
import com.squidsquads.repository.RoyaltyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RoyaltyService {

    private static final BigDecimal ROYALTY_FEE_VIEWED = BigDecimal.valueOf(0.01);
    private static final BigDecimal ROYALTY_FEE_TARGETED_VIEWED = BigDecimal.valueOf(0.03);
    private static final BigDecimal ROYALTY_FEE_CLICKED = BigDecimal.valueOf(0.05);
    private static final BigDecimal ROYALTY_FEE_TARGETED_CLICKED = BigDecimal.valueOf(0.10);

    @Autowired
    private RoyaltyRepository royaltyRepository;

    public Royalty findByVisitID(Integer visitID) {
        return royaltyRepository.findByVisitID(visitID);
    }

    public Royalty create(Campaign campaign, Visit visit, boolean isTargeted, boolean isClicked) {
        return royaltyRepository.save(new Royalty(campaign.getAccountID(), visit.getVisitID(), getFee(isTargeted, isClicked), false));
    }

    public Royalty update(Royalty royalty) {
        return royaltyRepository.save(royalty);
    }

    public BigDecimal getFee(boolean isTargeted, boolean isClicked) {

        if (isTargeted) {
            if (isClicked)
                return ROYALTY_FEE_TARGETED_CLICKED;
            else
                return ROYALTY_FEE_TARGETED_VIEWED;
        } else {
            if (isClicked)
                return ROYALTY_FEE_CLICKED;
            else
                return ROYALTY_FEE_VIEWED;
        }
    }
}
