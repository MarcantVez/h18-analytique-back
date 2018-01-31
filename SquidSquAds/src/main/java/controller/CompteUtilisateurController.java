package controller;

import model.CompteUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.CompteUtilisateurRepository;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-01-22
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
@RestController("CompteUtilisateurController")
public class CompteUtilisateurController {

    @Autowired
    CompteUtilisateurRepository compteUtilisateurRepository;

    @RequestMapping("/save")
    public String process(){
        // save a single Customer
        compteUtilisateurRepository.save(CompteUtilisateur.creerAdminWeb("test@test.com", "test", "0000 1111 2222 3333"));

        return "Done";
    }

    @RequestMapping("/findAll")
    public String findAll(){
        String result = "";

        for(CompteUtilisateur compteUtilisateur : compteUtilisateurRepository.findAll()){
            result += compteUtilisateur.toString() + "<br>";
        }

        return result;
    }
}
