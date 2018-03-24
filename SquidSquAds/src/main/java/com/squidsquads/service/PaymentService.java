package com.squidsquads.service;

import com.squidsquads.form.payment.response.AmountDueResponse;
import com.squidsquads.form.payment.response.CreateResponse;
import com.squidsquads.model.Payment;
import com.squidsquads.model.Royalty;
import com.squidsquads.model.Visit;
import com.squidsquads.repository.PaymentRepository;
import com.squidsquads.repository.RoyaltyRepository;
import com.squidsquads.repository.VisitRepository;
import com.squidsquads.utils.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(CampaignService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RoyaltyRepository royaltyRepository;

    @Autowired
    private VisitRepository visitRepository;

    public CreateResponse create(String token) {

        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);
        BigDecimal amount = BigDecimal.valueOf(0.00);

        // Si le compte n'a pas de session ici, c'est un probleme serveur
        if (SessionManager.NO_SESSION.equals(accountID)) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new CreateResponse().failed();
        }

        // Aller chercher la totalité des redevances non-réclamées
        List<Royalty> royaltyList = royaltyRepository.findAllByAccountIDAndIsClaimed(accountID, false);

        if (royaltyList.isEmpty()) {
            return new CreateResponse().noAmount();
        }

        for (Royalty royalty: royaltyList) {
            amount = amount.add(royalty.getAmount());
            royalty.setClaimed(true);
            royaltyRepository.save(royalty);
        }

        Payment payment = new Payment(
                accountID,
                amount
        );
        // TODO use Passerelle Paiement to process transaction
        paymentRepository.save(payment);

        return new CreateResponse().ok();
    }

    public AmountDueResponse getAmount(String token) {
        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);
        // Si le compte n'a pas de session ici, c'est un probleme serveur
        if (SessionManager.NO_SESSION.equals(accountID)) {
            logger.error("Un compte basé sur un token de session est introuvable");
            return new AmountDueResponse().failed();
        }

        BigDecimal fromClicks = BigDecimal.valueOf(0.00);
        BigDecimal fromViews = BigDecimal.valueOf(0.00);
        BigDecimal fromTargetedClicks = BigDecimal.valueOf(0.00);
        BigDecimal fromTargetedViews = BigDecimal.valueOf(0.00);
        // Aller chercher la totalité des redevances non-réclamées
        List<Royalty> royaltyList = royaltyRepository.findAllByAccountIDAndIsClaimed(accountID, false);

        for (Royalty royalty: royaltyList) {
            Visit relatedVisit = visitRepository.findByVisitID(royalty.getVisitID());
            if(relatedVisit.getClicked() && relatedVisit.getTargeted())
                fromTargetedClicks = fromTargetedClicks.add(royalty.getAmount());
            if(!relatedVisit.getClicked() && !relatedVisit.getTargeted())
                fromViews = fromViews.add(royalty.getAmount());
            if(relatedVisit.getClicked() && !relatedVisit.getTargeted())
                fromClicks = fromClicks.add(royalty.getAmount());
            if(!relatedVisit.getClicked() && relatedVisit.getTargeted())
                fromTargetedViews = fromTargetedViews.add(royalty.getAmount());
        }

        return new AmountDueResponse().ok(fromViews.floatValue(), fromTargetedViews.floatValue(), fromClicks.floatValue(), fromTargetedClicks.floatValue());
    }
}
