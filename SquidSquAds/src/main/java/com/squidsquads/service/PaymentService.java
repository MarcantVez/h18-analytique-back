package com.squidsquads.service;

import com.squidsquads.form.payment.response.CreateResponse;
import com.squidsquads.model.Campaign;
import com.squidsquads.model.Payment;
import com.squidsquads.model.Royalty;
import com.squidsquads.repository.PaymentRepository;
import com.squidsquads.repository.RoyaltyRepository;
import com.squidsquads.utils.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(CampaignService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RoyaltyRepository royaltyRepository;

    public CreateResponse create(String token) {

        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);
        BigDecimal amount = BigDecimal.valueOf(0.00);

        // Si le compte n'a pas de session ici, c'est un probleme serveur
        if (SessionManager.NO_SESSION.equals(accountID)) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new com.squidsquads.form.payment.response.CreateResponse().failed();
        }

        // Aller chercher la totalité des redevances non-réclamées
        List<Royalty> royaltyList = royaltyRepository.findAllByAccountIDAndClaimed(accountID, false);

        if (royaltyList.isEmpty()) {
            return new CreateResponse().failed();
        }

        for (Royalty royalty: royaltyList) {
            amount.add(royalty.getAmount());
            royalty.setClaimed(true);
        }

        Payment payment = new Payment(
                accountID,
                amount
        );

        Payment created = paymentRepository.save(payment);

        return new com.squidsquads.form.payment.response.CreateResponse().ok();
    }
}
