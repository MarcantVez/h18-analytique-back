package com.squidsquads.service;

import com.squidsquads.form.payment.response.AmountDueResponse;
import com.squidsquads.form.payment.response.CreateResponse;
import com.squidsquads.model.Account;
import com.squidsquads.model.Payment;
import com.squidsquads.model.Royalty;
import com.squidsquads.model.Visit;
import com.squidsquads.repository.AccountRepository;
import com.squidsquads.repository.PaymentRepository;
import com.squidsquads.repository.RoyaltyRepository;
import com.squidsquads.repository.VisitRepository;
import com.squidsquads.utils.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private AccountRepository accountRepository;

    public CreateResponse create(String token) {

        Integer accountID = SessionManager.getInstance().getAccountIdForToken(token);
        Account account = accountRepository.findByAccountID(accountID);
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


        Boolean boolSuccess = false;
        try {
            // Première requête pour geler les fonds
            // https://stackoverflow.com/questions/38372422/how-to-post-form-data-with-spring-resttemplate
            String postUrlGel = "https://gti525passerelle.com/api/gel";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("APIKey", "30280103-7766-44df-8d96-49332f5e194c");

            MultiValueMap<String, String> customer = new LinkedMultiValueMap<String, String>();
            customer.add("clientFirstName", "Squids");
            customer.add("clientLastName", "Squads");
            customer.add("cardNumber", "9140831287207583");
            customer.add("CVV", "299");
            customer.add("amount", amount.toString());
            customer.add("recipient", account.getBankAccount());
            customer.add("expirationdate", "2018-05-25");

            HttpEntity<MultiValueMap<String, String>> requestGel = new HttpEntity<MultiValueMap<String, String>>(customer, headers);

            ResponseEntity<String> responseGel = restTemplate.postForEntity(postUrlGel, requestGel, String.class);


            // Deuxième requête pour accepter de faire la transaction
            if (responseGel.getStatusCode().value() == 200) {
                String postUrlTransaction = "https://gti525passerelle.com/api/transaction";

                MultiValueMap<String, String> transaction = new LinkedMultiValueMap<String, String>();
                transaction.add("id", responseGel.getBody());
                transaction.add("isGoingThrough", "true");

                HttpEntity<MultiValueMap<String, String>> requestTransaction = new HttpEntity<MultiValueMap<String, String>>(transaction, headers);

                ResponseEntity<String> responseTransaction = restTemplate.postForEntity(postUrlGel, requestGel, String.class);
                String test = "";
            }

            boolSuccess = true;

        } catch(Exception e) {

        }

        if (boolSuccess) {
            paymentRepository.save(payment);
            return new CreateResponse().ok();
        }

        return new CreateResponse().failed();

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
            Visit relatedVisit = visitRepository.findOne(royalty.getVisitID());
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
