package com.squidsquads.unit.service.payment;

import com.squidsquads.form.payment.response.AmountDueResponse;
import com.squidsquads.model.Account;
import com.squidsquads.unit.service.AbstractServiceTests;
import com.squidsquads.utils.session.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

public class PaymentServiceTest extends AbstractServiceTests {
    private PaymentServiceHelper helper = new PaymentServiceHelper();

    private Account web1, web2;
    private String token1, token2;

    @Before
    public void setup() {
        web1 = helper.getAccountWeb1();
        web2 = helper.getAccountWeb2();
        token1 = SessionManager.getInstance().addSession(web1);
        token2 = SessionManager.getInstance().addSession(web2);
    }

    @Test
    public void testGetAmountWrongToken(){
        AmountDueResponse res = getPaymentService().getAmount("bad token");
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatus());
        assertNull(res.getTotalAmount());
    }

    @Test
    public void testGetAmountWithNoRoyalties(){
        when(getRoyaltyRepository().findAllByAccountIDAndIsClaimed(web2.getAccountID(), false))
                .thenReturn(helper.getNoRoyalties());
        AmountDueResponse res = getPaymentService().getAmount(token2);
        assertEquals(HttpStatus.OK, res.getStatus());
        assertEquals((Float)0.0f, res.getFromClicks());
        assertEquals((Float)0.0f, res.getFromTargetedClicks());
        assertEquals((Float)0.0f, res.getFromTargetedViews());
        assertEquals((Float)0.0f, res.getFromViews());
        assertEquals((Float)0.0f, res.getTotalAmount());
    }

    @Test
    public void testGetAmountWithRoyalties(){
        when(getRoyaltyRepository().findAllByAccountIDAndIsClaimed(web1.getAccountID(), false))
                .thenReturn(helper.getRoyalties());
        when(getVisitRepository().findOne(1)).thenReturn(helper.getViewedVisit());
        when(getVisitRepository().findOne(2)).thenReturn(helper.getClickedVisit());
        when(getVisitRepository().findOne(3)).thenReturn(helper.getTargetedVisit());
        when(getVisitRepository().findOne(4)).thenReturn(helper.getClickedTargetedVisit());

        AmountDueResponse res = getPaymentService().getAmount(token1);

        assertEquals(HttpStatus.OK, res.getStatus());
        assertEquals((Float)10.0f, res.getFromViews());
        assertEquals((Float)11.0f, res.getFromClicks());
        assertEquals((Float)12.0f, res.getFromTargetedViews());
        assertEquals((Float)13.0f, res.getFromTargetedClicks());
        assertEquals((Float)46.0f, res.getTotalAmount());
    }

}
