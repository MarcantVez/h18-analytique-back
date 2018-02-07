package controller;

import model.WebSiteAdmin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-01-16
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
@RestController
public class SystemController {

    @RequestMapping(value = "/add-web-site-admin/", method = RequestMethod.POST)
    public void addWebSiteAdmin(@RequestParam(required = true) WebSiteAdmin newAdmin){

    }

}
