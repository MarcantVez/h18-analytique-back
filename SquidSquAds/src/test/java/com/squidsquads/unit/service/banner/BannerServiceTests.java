package com.squidsquads.unit.service.banner;

import com.squidsquads.form.account.response.*;
import com.squidsquads.model.Account;
import com.squidsquads.unit.service.AbstractServiceTests;
import com.squidsquads.utils.session.SessionManager;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

public class BannerServiceTests extends AbstractServiceTests {

    private BannerTestHelper helper = new BannerTestHelper();

    /////////////
    // Get All //
    /////////////

    @Test
    public void getAllFailsWhenSessionNotFound() {

        BannerListResponse res = getBannerService().getAll("invalid token");

        assertNotNull(res);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatus());
    }

    @Test
    public void getAllSucceeds() {

        when(getWebSiteAdminRepository().findByAccountID(anyInt())).thenReturn(helper.getWebSiteAdmin());
        when(getBannerRepository().findByAccountID(anyInt())).thenReturn(helper.getListOfBanners());

        Account web = helper.getAccountWeb();
        String token = SessionManager.getInstance().addSession(web);

        BannerListResponse res = getBannerService().getAll(token);

        assertNotNull(res);
        assertEquals(2, res.getWebSiteAdminID().intValue());
        assertEquals(31, res.getHorID().intValue());
        assertEquals(32, res.getVerID().intValue());
        assertEquals(33, res.getMobID().intValue());
    }

    ///////////////////
    // Get Publicity //
    ///////////////////
}
