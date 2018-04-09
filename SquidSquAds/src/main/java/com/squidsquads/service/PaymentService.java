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

    private static final String API_KEY = "30280103-7766-44df-8d96-49332f5e194c";
    private static final String POST_URL_GEL = "https://gti525passerelle.com/api/gel";
    private static final String POST_URL_TRANSACTION = "https://gti525passerelle.com/api/transaction";
    private static final String FIRST_NAME = "Squids";
    private static final String LAST_NAME = "Squads";
    private static final String CARD_NUMBER = "9140831287207583";
    private static final String CVV = "299";
    private static final String EXPIRATION_DATE = "2018-05-25";

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

        for (Royalty royalty : royaltyList) {
            amount = amount.add(royalty.getAmount());
        }

        Boolean boolSuccess = false;

        try {
            // Première requête pour geler les fonds
            // https://stackoverflow.com/questions/38372422/how-to-post-form-data-with-spring-resttemplate

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("APIKey", API_KEY);

            MultiValueMap<String, String> customer = new LinkedMultiValueMap<String, String>();
            customer.add("clientFirstName", FIRST_NAME);
            customer.add("clientLastName", LAST_NAME);
            customer.add("cardNumber", CARD_NUMBER);
            customer.add("CVV", CVV);
            customer.add("amount", amount.toString());
            customer.add("recipient", account.getBankAccount());
            customer.add("expirationdate", EXPIRATION_DATE);

            HttpEntity<MultiValueMap<String, String>> requestGel = new HttpEntity<MultiValueMap<String, String>>(customer, headers);

            ResponseEntity<String> responseGel = restTemplate.postForEntity(POST_URL_GEL, requestGel, String.class);

            // Deuxième requête pour accepter de faire la transaction
            if (responseGel.getStatusCode().value() == 200) {

                String id = responseGel.getBody().replace("\"", "");

                MultiValueMap<String, String> transaction = new LinkedMultiValueMap<String, String>();
                transaction.add("id", id);
                transaction.add("isGoingThrough", "true");

                HttpEntity<MultiValueMap<String, String>> requestTransaction = new HttpEntity<MultiValueMap<String, String>>(transaction, headers);

                ResponseEntity<String> responseTransaction = restTemplate.postForEntity(POST_URL_TRANSACTION, requestTransaction, String.class);

                if (responseTransaction.getStatusCode().value() == 200 || responseTransaction.getStatusCode().value() == 202) {
                    boolSuccess = true;
                }
            }

        } catch (Exception e) {
            boolSuccess = false;
            logger.error("Le paiement avec passerelle n'a pas fonctionné. Erreur : " + e.getMessage());
        }

        if (boolSuccess) {

            for (Royalty royalty : royaltyList) {
                royalty.setClaimed(true);
                royaltyRepository.save(royalty);
            }

            paymentRepository.save(new Payment(accountID, amount));
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

        for (Royalty royalty : royaltyList) {
            Visit relatedVisit = visitRepository.findOne(royalty.getVisitID());
            if (relatedVisit.getClicked() && relatedVisit.getTargeted())
                fromTargetedClicks = fromTargetedClicks.add(royalty.getAmount());
            if (!relatedVisit.getClicked() && !relatedVisit.getTargeted())
                fromViews = fromViews.add(royalty.getAmount());
            if (relatedVisit.getClicked() && !relatedVisit.getTargeted())
                fromClicks = fromClicks.add(royalty.getAmount());
            if (!relatedVisit.getClicked() && relatedVisit.getTargeted())
                fromTargetedViews = fromTargetedViews.add(royalty.getAmount());
        }

        return new AmountDueResponse().ok(fromViews.floatValue(), fromTargetedViews.floatValue(), fromClicks.floatValue(), fromTargetedClicks.floatValue());
    }
}
