package controller;

import model.WebSiteAdmin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-01-22
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
@RestController
public class ServiceRegisteringController {

    @RequestMapping(value = "/add-web-site-admin/", method = RequestMethod.POST)
    public void addWebsiteAdmin (@RequestParam WebSiteAdmin newAdmin){

    }

    @RequestMapping(value = "/remove-web-site-admin/", method = RequestMethod.POST)
    public void removeWebsiteAdmin (@RequestParam WebSiteAdmin admin){

    }

}
