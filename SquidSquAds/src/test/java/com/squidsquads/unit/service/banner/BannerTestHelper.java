package com.squidsquads.unit.service.banner;

import com.squidsquads.model.Banner;
import com.squidsquads.model.Orientation;
import com.squidsquads.unit.service.ServiceTestHelper;

import java.util.Arrays;
import java.util.List;

public class BannerTestHelper extends ServiceTestHelper {

    //////////////////////
    // Banner : Get All //
    //////////////////////

    public List<Banner> getListOfBanners() {
        Banner hor = new Banner(ACCOUNT_ID, Orientation.HOR.name());
        Banner ver = new Banner(ACCOUNT_ID, Orientation.VER.name());
        Banner mob = new Banner(ACCOUNT_ID, Orientation.MOB.name());
        hor.setBannerID(31);
        ver.setBannerID(32);
        mob.setBannerID(33);
        return Arrays.asList(hor, ver, mob);
    }
}
