package com.squidsquads.unit.service.payment;

import com.squidsquads.model.Account;
import com.squidsquads.model.AdminType;
import com.squidsquads.model.Royalty;
import com.squidsquads.model.Visit;
import com.squidsquads.unit.service.ServiceTestHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PaymentServiceHelper extends ServiceTestHelper {

    public Account getAccountWeb1() {
        Account web = new Account(AdminType.WEB.name(), EMAIL_WEB, HASHED_PASSWORD, BANK_ACCOUNT);
        web.setAccountID(ACCOUNT_ID);
        return web;
    }

    public Account getAccountWeb2() {
        Account web = new Account(AdminType.WEB.name(), EMAIL_WEB, HASHED_PASSWORD, BANK_ACCOUNT);
        web.setAccountID(2);
        return web;
    }

    public List<Royalty> getRoyalties(){
        List<Royalty> royalties = new ArrayList<>();

        royalties.add(new Royalty(1, 1, BigDecimal.valueOf(10), false));
        royalties.add(new Royalty(1, 2, BigDecimal.valueOf(11), false));
        royalties.add(new Royalty(1, 3, BigDecimal.valueOf(12), false));
        royalties.add(new Royalty(1, 4, BigDecimal.valueOf(13), false));

        return royalties;
    }

    public List<Royalty> getNoRoyalties(){
        return new ArrayList<Royalty>();
    }

    public Visit getViewedVisit(){
        Visit v =  new Visit(1, false, false);
        v.setVisitID(1);
        return v;
    }

    public Visit getClickedVisit(){
        Visit v = new Visit(1, true, false);
        v.setVisitID(2);
        return v;
    }

    public Visit getTargetedVisit(){
        Visit v = new Visit(1, false, true);
        v.setVisitID(3);
        return v;
    }

    public Visit getClickedTargetedVisit(){
        Visit v = new Visit(1, true, true);
        v.setVisitID(4);
        return v;
    }

}
