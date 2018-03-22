package com.squidsquads.service;

import com.squidsquads.model.Royalty;
import com.squidsquads.repository.RoyaltyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoyaltyService {
    private static final Logger logger = LoggerFactory.getLogger(RoyaltyService.class);

    @Autowired
    private RoyaltyRepository royaltyRepository;

    public Royalty findByVisitID(Integer visitID)
    {
        return royaltyRepository.findByVisitID(visitID);
    }

    public Royalty save(Royalty royalty)
    {
        return royaltyRepository.save(royalty);
    }
}
